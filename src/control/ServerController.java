//Server Code from original Moose Project (Mahmoud Sadeghi)
//https://github.com/mahci/Proto-Moose

package control;


/**
 * Class responsible for getting the data from the Server and perform the actions
 */
public class ServerController {
    private final String NAME = "Controller/"; // class tag

    public static ServerController instance; // Singleton

    //------------------------------------------------------------------------------------------

    /**
     * Get the single instance
     * @return Singleton instnace
     */
    public static ServerController get() {
        if (instance == null) instance = new ServerController();
        return instance;
    }

    /**
     * Contrsuctor
     */
    private ServerController() {
        String TAG = NAME;

    }

    // Actions ---------------------------------------------------------------------------------------


    // Runnables -------------------------------------------------------------------------------------



}
