import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        HBox control1 = new HBox(5);
        Label addLabel = new Label("Add");
        Button rect = new Button("Rect");
        Button oval = new Button("Oval");
        Button line = new Button("Line");
        Button text = new Button("text");
        control1.getChildren().addAll(addLabel, rect, oval, line, text);

        HBox control2 = new HBox(5);
        Button setColor = new Button("Set Color");
        control2.getChildren().addAll(setColor);

        HBox control3 = new HBox(5);
        TextField textField = new TextField();
        ChoiceBox<String> fonts = new ChoiceBox<>();
        fonts.getItems().addAll("Arial", "Calibri", "Consolas");
        fonts.setValue("Arial");
        control3.getChildren().addAll(textField, fonts);

        HBox control4 = new HBox(5);
        Button moveToFront = new Button("Move To Front");
        Button moveToBack = new Button("Move To Back");
        Button removeShape = new Button("Remove DShape");
        control4.getChildren().addAll(moveToFront, moveToBack, removeShape);

        TableView<DShape> table = new TableView<>();

        TableColumn<DShape, Integer> xCol = new TableColumn<>("X");
        xCol.setMinWidth(100);
        xCol.setCellValueFactory(new PropertyValueFactory<>("x"));
        TableColumn<DShape, Integer> yCol = new TableColumn<>("Y");
        yCol.setMinWidth(100);
        yCol.setCellValueFactory(new PropertyValueFactory<>("y"));
        TableColumn<DShape, Integer> widthCol = new TableColumn<>("Width");
        widthCol.setMinWidth(100);
        widthCol.setCellValueFactory(new PropertyValueFactory<>("width"));
        TableColumn<DShape, Integer> heightCol = new TableColumn<>("Height");
        heightCol.setMinWidth(100);
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));

        table.setItems(getShapes());
        table.getColumns().addAll(xCol, yCol, widthCol, heightCol);

        VBox controlPanel = new VBox(15);
        controlPanel.getChildren().addAll(control1, control2, control3, control4, table);

        borderPane.setLeft(controlPanel);

        Scene scene = new Scene(borderPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ObservableList<DShape> getShapes() {
        ObservableList<DShape> shapes = FXCollections.observableArrayList();
        shapes.add(new DShape(10, 10, 111, 58));
        shapes.add(new DShape(56, 148, 361, 120));
        return shapes;
    }
}
