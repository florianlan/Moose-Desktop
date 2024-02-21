package trials;

import data.Data;
import tools.TrialInfo.INFO;

import java.util.*;

public class TrialRun {
    private static TrialRun INSTANCE;
    private List<Trial> trialList;
    private Trial actualTrial;

    /**
     * private Constructor
     */
    private TrialRun() {
        trialList = new ArrayList<>();
        for (int i = 1; i <= Data.getInstance().getCols(); i++) {
            trialList.add(new Trial(INFO.ACTIVE_ROW_1, i));
            trialList.add(new Trial(INFO.ACTIVE_ROW_2, i));
        }
    }

    /**
     * Singleton of TrialRun
     */
    public static TrialRun getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TrialRun();
        }
        return INSTANCE;
    }

    /**
     * start new block after brake
     *
     * @return TrialRun
     */
    public TrialRun startNewBlock() {
        INSTANCE = new TrialRun();
        return INSTANCE;
    }

    public void removeTrial(Trial trial) {
        trialList.remove(trial);
    }

    public Trial getRandomTrial() {
        if (!trialList.isEmpty()) {
            Collections.shuffle(trialList);
            Trial newTrial = trialList.get(0);
            while (trialList.size() > 1 && newTrial == actualTrial) {
                Collections.shuffle(trialList);
                newTrial = trialList.get(0);
            }

            actualTrial = newTrial;
        } else actualTrial = null;

        Data.getInstance().setTrailActiveMilliSec(System.currentTimeMillis());
        return actualTrial;
    }

    /**
     * Getter
     */
    public Trial getActualTrial() {
        return actualTrial;
    }

}
