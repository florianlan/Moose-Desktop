package gui;

import control.Server;
import data.Writer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Moose Desktop");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            Writer.getInstance().prepareCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Server.get().start();
        launch(args);

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Writer.getInstance().close();

    }
}
