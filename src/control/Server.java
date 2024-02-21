//Server Code from original Moose Project (Mahmoud Sadeghi)
//https://github.com/mahci/Proto-Moose

package control;

import data.Data;
import gui.Controller;
import javafx.application.Platform;
import tools.Consts.STRINGS;
import tools.Logs;
import tools.Memo;
import tools.TrialInfo.INFO;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private final static String NAME = "Server/";

    private static Server instance; // Singelton

    private final int PORT = 8000; // always the same
    private final int CONNECTION_TIMEOUT = 60 * 1000; // 1 min

    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter outPW;
    private BufferedReader inBR;

    private final ExecutorService executor;

    //----------------------------------------------------------------------------------------

    //-- Runnable for waiting for incoming connections
    private class ConnWaitRunnable implements Runnable {
        String TAG = NAME + "ConnWaitRunnable";

        @Override
        public void run() {
            try {
                Logs.d(TAG, "Waiting for connections...");
                if (serverSocket == null) serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();

                // When reached here, Moose is connected
                Logs.d(TAG, "Moose connected!");

                // Create streams
                inBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outPW = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                // Start receiving
                executor.execute(new InRunnable());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-- Runnable for sending messages to Moose
    private class OutRunnable implements Runnable {
        String TAG = NAME + "OutRunnable";
        Memo message;

        public OutRunnable(Memo mssg) {
            message = mssg;
        }

        @Override
        public void run() {
            if (message != null && outPW != null) {
                outPW.println(message);
                outPW.flush();
            }
        }
    }

    //-- Runnable for receiving messages from Moose
    private class InRunnable implements Runnable {
        String TAG = NAME + "InRunnable";

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && inBR != null) {
                try {
                    Logs.d(TAG, "Reading messages...");
                    String message = inBR.readLine();
                    Logs.d(TAG, "Message: " + message);
                    if (message != null) {
                        Memo memo = Memo.valueOf(message);

                        // Check the action...
                        if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.INTRO)) {
                            //first INTRO message after connecting

                        } else if (memo.getAction().equals(STRINGS.EVENT) && memo.getMode().equals(STRINGS.TAP)) {
                            Data.getInstance().setEndMilliSec(System.currentTimeMillis()); //for time measurement

                            if (Controller.getInstance().isHovered()) {
                                System.out.println("click and hovered");
                                // New click on GRID
                                String[] click = memo.getStrValue(1).split(",");
                                Data.getInstance().setDownX(Integer.parseInt(click[0]));
                                Data.getInstance().setDownY(Integer.parseInt(click[1]));
                                Data.getInstance().setUpX(Integer.parseInt(click[2]));
                                Data.getInstance().setUpY(Integer.parseInt(click[3]));
                                Data.getInstance().setClickDuration(memo.getIntValue(2));

                                //ignore rare case that a negative value is passed
                                if (Data.getInstance().getUpX() <= 0 || Data.getInstance().getUpY() <= 0) break;

                                // If moose click on square
                                Controller ctr = Controller.getInstance();

                                int success = Data.getInstance().checkMooseClick();
                                Platform.runLater(() -> ctr.onCheck(success));
                            }

                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.GRID)) {
                            // Init Grid size
                            Data.getInstance().setRows(memo.getIntValue(1));
                            Data.getInstance().setCols(memo.getIntValue(2));
                            INFO.NR_ROWS = memo.getIntValue(1);
                            INFO.NR_COLS = memo.getIntValue(2);
                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.SIZE)) {
                            // Init pixel size of Grid
                            Data.getInstance().setSizeX(memo.getIntValue(1));
                            Data.getInstance().setSizeY(memo.getIntValue(2));
                            INFO.SIZE_X_TOTAL = memo.getIntValue(1);
                            INFO.SIZE_Y_TOTAL = memo.getIntValue(2);
                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.SYMBOLS)) {
                            // Init symbols and lines visible?
                            INFO.SHOW_SYMBOLS = memo.getIntValue(1) != 0;
                            INFO.SHOW_LINES = memo.getIntValue(2) != 0;
                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.FAILS)) {
                            // Init show fails?
                            INFO.SHOW_FAILS = memo.getIntValue(1) != 0;
                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.DOTSIZEPX)) {
                            // Init dot size in mm
                            INFO.DOT_SIZE_PX = memo.getIntValue(1);
                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.TEST_ID)) {
                            // Init Test_id (active rows)
                            INFO.TEST_ID = String.valueOf(memo.getIntValue(1));
                        } else if (memo.getAction().equals(STRINGS.INTRO) && memo.getMode().equals(STRINGS.ROWS_ACTIVE)) {
                            // Init set active rows
                            INFO.ACTIVE_ROW_1 = memo.getIntValue(1);
                            INFO.ACTIVE_ROW_2 = memo.getIntValue(2);
                        }
                        System.out.println(memo);

                    } else {
                        Logs.d(TAG, "Moose disconnected.");
                        start();
                        return;
                    }
                } catch (IOException e) {
                    System.out.println("Error in reading from Moose");
                    start();
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------

    /**
     * Get the instance
     *
     * @return single instance
     */
    public static Server get() {
        if (instance == null) instance = new Server();
        return instance;
    }

    /**
     * Constructor
     */
    public Server() {
        String TAG = NAME;

        // Init executerService for running threads
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Start the server
     */
    public void start() {
        String TAG = NAME + "start";

        executor.execute(new ConnWaitRunnable());
    }

    /**
     * Send a Memo to the Moose
     * Called from outside
     *
     * @param mssg Memo message
     */
    public void send(Memo mssg) {
        executor.execute(new OutRunnable(mssg));
    }

}
