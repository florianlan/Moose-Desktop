package data;

import tools.TrialInfo;
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

    //for time measurement
    private long trailActiveMilliSec;
    private long startMilliSec;
    private long endMilliSec;
    private long clickDuration;

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

    public int checkMooseClick() {
        if (expectedX == 0 || expectedY == 0) {
            System.out.println("no valid expect");
            return 0;
        }

        //field size per grid element
        double fieldX = (sizeX + 0.0) / cols;
        double fieldY = (sizeY + 0.0) / rows;

        //coordinates of grid starting from 1
        int actualX = (int) (upX / fieldX) + 1;
        int actualY = (int) (upY / fieldY) + 1;

        //pixel offset and euclid
        int expectedXPixel = (int) (expectedX * fieldX - fieldX/2);
        int expectedYPixel = (int) (expectedY * fieldY - fieldY/2);
        int pixOffX = upX - expectedXPixel;
        int pixOffY = upY - expectedYPixel;
        double euclidDistance = Math.sqrt(Math.pow(pixOffY, 2) + Math.pow(pixOffX, 2));

        //trialTime from hover to click
        int trialTime = (int) (endMilliSec - startMilliSec);

        //manhattan calculations
        int manhattanOffRow = Math.abs(actualY - expectedY);
        int manhattanOffCol = Math.abs(actualX - expectedX);
        int manhattanDistance = manhattanOffCol + manhattanOffRow;

        // 3 => in DOT_SIZE_PX circle
        // 2 => outside circle, but closer then to other circle
        // 1 => outside row, but closer to right row
        // 0 => outside row and closer to wrong row
        int successType = -1;

        if (actualX == expectedX && actualY == expectedY) {
            successType = euclidDistance < INFO.DOT_SIZE_PX ? 3 : 2;
        } else {
            int wrongYPixel;
            if (expectedY == INFO.ACTIVE_ROW_1) {
                wrongYPixel = (int) (INFO.ACTIVE_ROW_2 * fieldY - fieldY/2);
            } else {
                wrongYPixel = (int) (INFO.ACTIVE_ROW_1 * fieldY - fieldY/2);
            }
            int halfDistanceActiveRows = Math.abs(wrongYPixel - expectedYPixel) / 2;
            System.out.println(wrongYPixel + "," + expectedYPixel + "," + halfDistanceActiveRows);
            successType = Math.abs(pixOffY) < halfDistanceActiveRows ? 1 : 0;
        }

        // Writing result into CSV file
        try {
            String str = INFO.PARTICIPANT_ID + "," + INFO.TRIAL_NR + "," + INFO.TRIAL_ID + "," + INFO.BLOCK_NR + "," +
                    actualY + "," + actualX + "," + expectedY + "," + expectedX + "," + successType + "," +
                    trailActiveMilliSec + "," + startMilliSec + "," + endMilliSec + "," + trialTime + "," + clickDuration +
                    "," + downX + "," + downY + "," + upX + "," + upY + "," + manhattanDistance + "," +
                    manhattanOffRow + "," + manhattanOffCol + ',' + pixOffY + "," + pixOffX + "," + euclidDistance + "\n";

            Writer.getInstance().write(str);
            Writer.getInstance().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("success " + successType);
        return successType;
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

    public void setStartMilliSec(long startMilliSec) {
        this.startMilliSec = startMilliSec;
    }

    public void setEndMilliSec(long endMilliSec) {
        this.endMilliSec = endMilliSec;
    }

    public void setTrailActiveMilliSec(long trailActiveMilliSec) {
        this.trailActiveMilliSec = trailActiveMilliSec;
    }

    public void setClickDuration(long clickDuration) {
        this.clickDuration = clickDuration;
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

    public long getStartMilliSec() {
        return startMilliSec;
    }

    public long getEndMilliSec() {
        return endMilliSec;
    }

    public long getTrailActiveMilliSec() {
        return trailActiveMilliSec;
    }

    public long getClickDuration() {
        return trailActiveMilliSec;
    }
}
