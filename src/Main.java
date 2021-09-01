import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("LabManager for MULE");
        stage.setScene(scene);
        //stage.setMinWidth(603);
        //stage.setMinHeight(835);
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
