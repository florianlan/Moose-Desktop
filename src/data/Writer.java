package data;

import tools.TrialInfo.INFO;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
            writer = new FileWriter(INFO.CSV_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareCSV() throws IOException {
        String str = "ParticipantID,TrialNr,TaskID,BlockNr,NrRows,NrCols,IconType,LinesVisible," +
                "SelectedRow,SelectedCol,ExpectedRow,ExpectedCol,Success,DownX,DownY,UpX,UpY," +
                "ManhattanDistance,ManhattanOffRow,ManhattanOffCol,PixOffRow,PixOffCol,EuqlidDistance;" +
                "\n";
        writer.write(str);
        writer.flush();
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
