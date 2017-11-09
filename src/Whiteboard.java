import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Whiteboard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Whiteboard");

        BorderPane borderPane = new BorderPane();
        Canvas canvas = new Canvas();
        borderPane.setCenter(canvas);

        Scene scene = new Scene(borderPane, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
