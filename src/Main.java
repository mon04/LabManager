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

        stage.setTitle("LabManager");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/media/icon.png"));
        stage.setMinWidth(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
