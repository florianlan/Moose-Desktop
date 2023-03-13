package data;

import tools.TrialInfo.INFO;

import java.io.FileWriter;
import java.io.IOException;

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
        String str = "ParticipantID,TrialNr,TaskID,BlockNr,NrRows,NrCols,SymbolsVisible,LinesVisible," +
                "SelectedRow,SelectedCol,ExpectedRow,ExpectedCol,Success,Duration,DownX,DownY,UpX,UpY," +
                "ManhattanDistance,ManhattanOffRow,ManhattanOffCol,PixOffY,PixOffX,EuclidDistance,SizeXTotal,SizeYTotal,SizeXField,SizeYField;" +
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
