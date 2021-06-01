package aplicattion;

import Data.SpaceMarine;
import Data.SpaceMarines;
import client.Client;
import graphica.MainController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;


public class MainApp extends Application {
    public static String answerLine="";
    public static Client client = new Client();
    public static String username;
    public static String pass;
    public static MainController mainController;
    public static SpaceMarine person;
    public static LinkedList<SpaceMarine> space;
    public static Integer id;
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        client.launch();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../graphica/auth.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(root,640,480));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../pictures/nek.png")));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(t -> System.exit(0));
        primaryStage.show();
    }
}
