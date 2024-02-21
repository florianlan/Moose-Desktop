package gui;

import data.Data;
import data.Writer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import tools.TrialInfo.INFO;
import trials.Trial;
import trials.TrialRun;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private boolean hovered;
    private Timer timer;
    private int breakTimeLeft;

    private static Controller INSTANCE;

    /**
     * Singleton instantiation
     */
    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public Controller() {
        INSTANCE = this;
    }

    @FXML
    private Pane click_field;

    @FXML
    private Button click_start;

    @FXML
    private Button click_continue;

    @FXML
    private Line hori_line;

    @FXML
    private Label lbl_info;

    @FXML
    private AnchorPane test_area;

    @FXML
    private Line vert_line;

    @FXML
    void btnClicked(MouseEvent event) {

    }

    @FXML
    void btnHover(MouseEvent event) {
        if (!hori_line.isVisible() && !vert_line.isVisible()) Data.getInstance().setStartMilliSec(System.currentTimeMillis());

        hori_line.setVisible(true);
        vert_line.setVisible(true);
        hovered = true;

    }

    @FXML
    void btnHoverExit(MouseEvent event) {
    }


    @FXML
    void btnStartClick(MouseEvent event) {
        if (Data.getInstance().getRows() > 0) { //da verbindung hergestellt zu smartphone
            try {
                Writer.writeConfigCSV();
            } catch (IOException e) {
                e.printStackTrace();
            }
            click_start.setVisible(false);
            click_field.setVisible(true);
            click_field.setDisable(false);

            hori_line.setVisible(false);
            vert_line.setVisible(false);
            hovered = false;

            //setting up Trial Run
            TrialRun.getInstance();

            //show Trial text field
            lbl_info.setVisible(true);
            updateTextField();

            //setup first exercise
            Trial trial = TrialRun.getInstance().getRandomTrial();
            setExercise(trial.getRow(), trial.getCol());

        } else {
            throw new RuntimeException("device not connected");
        }
    }


    @FXML
    void btnContinueClick(MouseEvent event) {
        click_continue.setVisible(false);
        click_field.setVisible(true);
        click_field.setDisable(false);

        hori_line.setVisible(false);
        vert_line.setVisible(false);
        hovered = false;

        INFO.TRIAL_NR = 0;
        INFO.BLOCK_NR++;
        updateTextField();
        TrialRun.getInstance().startNewBlock();

        //setup next exercise
        Trial trial = TrialRun.getInstance().getRandomTrial();
        setExercise(trial.getRow(), trial.getCol());

    }

    /**
     * after checking success wrap up
     * @param success (0-3)
     */
    public void onCheck(int success) {
        showSuccess(success);
        Trial trial = TrialRun.getInstance().getActualTrial();
        TrialRun trialRun = TrialRun.getInstance();

        if (trial != null) {
            int trialsLeft = trialRun.getActualTrial().decTrialsLeft(1);
            if (trialsLeft == 0) {
                trialRun.removeTrial(trialRun.getActualTrial());
            }
        }
        startNewTrial();
    }

    /**
     * start a new trial
     */
    public void startNewTrial() {
        Trial newTrial = TrialRun.getInstance().getRandomTrial();
        if (newTrial != null) {//when null take a break and start new Block
            setExercise(newTrial.getRow(), newTrial.getCol());
        } else {
            this.takeABreak();
        }
    }

    /**
     * Take a brake and start new Block if needed
     */
    private void takeABreak() {
        breakTimeLeft = INFO.BREAK_TIME;
        click_field.setDisable(true);
        click_field.setVisible(false);

        if (INFO.BLOCK_NR >= INFO.BLOCK_AMOUNT) { //finish test
            lbl_info.setText("finished HCI study! Thanks for your participation");

        } else { //brake and next block
            click_continue.setVisible(true);
            click_continue.setDisable(true);
            click_continue.setText("Continue (in " + breakTimeLeft + " seconds)");

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (breakTimeLeft > 0) {
                        Platform.runLater(() -> click_continue.setText("Continue (in " + breakTimeLeft-- + " seconds)"));
                    } else {
                        //enable continue button
                        Platform.runLater(() -> {
                            click_continue.setText("Continue");
                            click_continue.setDisable(false);
                        });
                        timer.cancel();
                    }
                }
            }, 1000, 1000);

        }

    }

    /**
     * show if success
     * @param success (0 = closer wrong row; 1 = closer right row; 2 = right row; 3 = in (x)mm circle)
     */
    private void showSuccess(int success) {
        if (!INFO.SHOW_FAILS) return;
        switch (success) {
            case 0 -> this.click_field.setStyle("-fx-background-color: #e55d5d; -fx-border-color: #000000");
            case 1 -> this.click_field.setStyle("-fx-background-color: #eaa050; -fx-border-color: #000000");
            case 2 -> this.click_field.setStyle("-fx-background-color: #eae750; -fx-border-color: #000000");
            case 3 -> this.click_field.setStyle("-fx-background-color: #91ea50; -fx-border-color: #000000");
        }

        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    click_field.setStyle("-fx-background-color: #cccccc; -fx-border-color: #000000");
                }
            }, 500
        );

    }

    /**
     * Getter
     */
    public boolean isHovered() {
        return hovered;
    }

    public void setExercise(int row, int col) {
        Random random = new Random();
        int lineLength = 60;
        int offsetX = lineLength / (Data.getInstance().getCols() - 1);
        int offsetY = lineLength / (Data.getInstance().getRows() - 1);

        //new place of square
        if (random.nextFloat() > 0.7f) {
            click_field.setLayoutX(random.nextInt((int) test_area.getWidth() - 100));
            click_field.setLayoutY(random.nextInt((int) test_area.getHeight() - 100));
            //set lines invisible
            hori_line.setVisible(false);
            vert_line.setVisible(false);
            hovered = false;
        }

        //new symbol
        if (row == INFO.ACTIVE_ROW_1) {
            hori_line.setStartY(0);
            hori_line.setEndY(0);
        } else if (row == INFO.ACTIVE_ROW_2) {
            hori_line.setStartY(lineLength);
            hori_line.setEndY(lineLength);
        }

        vert_line.setStartX((col - 1) * offsetX);
        vert_line.setEndX((col - 1) * offsetX);

        // Set expected symbol
        Data.getInstance().setExpectedX(col);
        Data.getInstance().setExpectedY(row);

        //update Trial Label
        INFO.TRIAL_NR++;
        INFO.TRIAL_ID = row + "" + col;
        updateTextField();

        Data.getInstance().setStartMilliSec(System.currentTimeMillis());

    }

    private void updateTextField() {
        int trails = Data.getInstance().getRows() * Data.getInstance().getCols() * INFO.TRIAL_AMOUNT_PER_BLOCK;
        lbl_info.setText("Block: " + INFO.BLOCK_NR + "/" + INFO.BLOCK_AMOUNT + "; Trail: " + INFO.TRIAL_NR + "/" + trails);

    }

}
