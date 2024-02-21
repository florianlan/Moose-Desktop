package data;

import tools.TrialInfo.INFO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Writer {
    private FileWriter writer;

    private static Writer INSTANCE;

    public static Writer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Writer();
        }
        return INSTANCE;
    }

    private Writer() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd'T'HH_mm_ss");
            String timestamp = LocalDateTime.now().format(dtf);
            writer = new FileWriter(INFO.CSV_FILE_NAME + "_" + timestamp + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareCSV() throws IOException {
        String str = "ParticipantID,TrialNr,TrialID,BlockNr,SelectedRow,SelectedCol,ExpectedRow,ExpectedCol," +
                "SuccessType,TrialActiveTimeMs,StartTimeMs,EndTimeMs,TrialTime,ClickDuration,DownX,DownY,UpX,UpY,ManhattanDistance,ManhattanOffRow,ManhattanOffCol," +
                "PixOffY,PixOffX,EuclidDistance;\n";
        writer.write(str);
        writer.flush();
    }

    public static void writeConfigCSV() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd'T'HH_mm_ss");
        String timestamp = LocalDateTime.now().format(dtf);
        String path = "csv/config_" + INFO.PARTICIPANT_ID + "_" + INFO.PARTICIPANT_NAME + "_" + timestamp + ".csv";
        File file = new File(path);
        FileWriter configWriter = new FileWriter(path);

        //TODO: check how to do it with existing file or not
//        if (!file.exists()) {
            String str = "ParticipantID,TestID,BlockAmount,TrialAmountPerBlock,ActiveRow1,ActiveRow2," +
                    "DotSizePx,BreakTime,ShowSymbols,ShowLines,ShowFails,NrRows,NrCols,SizeXTotal,SizeYTotal," +
                    "SizeXField,SizeYField;\n";
            configWriter.write(str);
            configWriter.flush();

//            System.out.println("File created successfully.");
//        }

        str = INFO.PARTICIPANT_ID + "," + INFO.TEST_ID + "," + INFO.BLOCK_AMOUNT + "," +
                INFO.TRIAL_AMOUNT_PER_BLOCK + "," + INFO.ACTIVE_ROW_1 + "," + INFO.ACTIVE_ROW_2 + "," +
                INFO.DOT_SIZE_PX + "," + INFO.BREAK_TIME + "," + INFO.SHOW_SYMBOLS + "," + INFO.SHOW_LINES + "," +
                INFO.SHOW_FAILS + "," + INFO.NR_ROWS + "," + INFO.NR_COLS + "," + INFO.SIZE_X_TOTAL + "," +
                INFO.SIZE_Y_TOTAL + "," + (INFO.SIZE_X_TOTAL / INFO.NR_ROWS) + "," +
                (INFO.SIZE_Y_TOTAL / INFO.NR_COLS) + "\n";
        configWriter.write(str);
        configWriter.flush();
    }

    public void write(String str) throws IOException {
        writer.write(str);
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }

}
