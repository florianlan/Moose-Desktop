package data;

import tools.TrialInfo.INFO;

import java.io.IOException;

public class Data {
    private int rows;
    private int cols;
    private int sizeX;
    private int sizeY;
    private int downX;
    private int downY;
    private int upX;
    private int upY;

    //for success check
    private int expectedX;
    private int expectedY;
    private int actualX;
    private int actualY;

    private static Data INSTANCE;

    /**
     * Singleton instantiation
     */
    public static Data getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Data();
        }
        return INSTANCE;
    }

    /**
     * Constructor
     */
    private Data() {

    }

    public boolean checkMooseClick() {
        if (expectedX == 0 || expectedY == 0) {
            System.out.println("no valid expect");
            return false;
        }

        double fieldX = (sizeX + 0.0) / cols;
        double fieldY = (sizeY + 0.0) / rows;

        actualX = (int) (upX / fieldX) + 1;
        actualY = (int) (upY / fieldY) + 1;

        // Writing result into CSV file
        if (actualX == expectedX && actualY == expectedY) {
            try {
                String str = INFO.PARTICIPANT_ID + "," + INFO.TRIAL_NR + "," + INFO.TASK_ID + "," + INFO.BLOCK_NR + "," +
                        rows + "," + cols + "," + "2" + "," + "false" + "," + actualY + "," + actualX + "," + expectedY + "," +
                        expectedX + "," + "true" + "," + downX + "," + downY + "," + upX + "," + upY + "," +
                        "0,0,0,0,0,0;\n";
                Writer.getInstance().write(str);
                Writer.getInstance().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("click success");
            return true;
        }

        try {
            String str = INFO.PARTICIPANT_ID + "," + INFO.TRIAL_NR + "," + INFO.TASK_ID + "," + INFO.BLOCK_NR + "," +
                    rows + "," + cols + "," + "2" + "," + "false" + "," + actualY + "," + actualX + "," + expectedY + "," +
                    expectedX + "," + "false" + "," + downX + "," + downY + "," + upX + "," + upY + "," +
                    "0,0,0,0,0,0;\n";
            Writer.getInstance().write(str);
            Writer.getInstance().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("no success");
        return false;
    }


    /**
     * Setter
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setDownX(int downX) {
        this.downX = downX;
    }

    public void setDownY(int downY) {
        this.downY = downY;
    }

    public void setUpX(int upX) {
        this.upX = upX;
    }

    public void setUpY(int upY) {
        this.upY = upY;
    }

    public void setExpectedX(int expectedX) {
        this.expectedX = expectedX;
    }

    public void setExpectedY(int expectedY) {
        this.expectedY = expectedY;
    }

    /**
     * Getter
     */
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getDownX() {
        return downX;
    }

    public int getDownY() {
        return downY;
    }

    public int getUpX() {
        return upX;
    }

    public int getUpY() {
        return upY;
    }

}
