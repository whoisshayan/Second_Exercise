import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Pane for background shapes
        Pane backgroundPane = new Pane();
        backgroundPane.setPrefSize(700, 700);
        // Set a dark blue-gray background color to match the UI theme
        backgroundPane.setStyle("-fx-background-color: #2c3e50;");

        // Create some hexagon shapes as decorative elements
        Polygon hexagon1 = createHexagon(100, 150, 80);
        hexagon1.setFill(Color.web("#2980b9", 0.2));
        Polygon hexagon2 = createHexagon(600, 200, 60);
        hexagon2.setFill(Color.web("#8e44ad", 0.2));
        Polygon hexagon3 = createHexagon(350, 600, 100);
        hexagon3.setFill(Color.web("#27ae60", 0.2));

        // Create a couple of triangle shapes
        Polygon triangle1 = createTriangle(50, 50, 150, 50, 100, 100);
        triangle1.setFill(Color.web("#e74c3c", 0.2));
        Polygon triangle2 = createTriangle(550, 650, 650, 650, 600, 600);
        triangle2.setFill(Color.web("#f1c40f", 0.2));

        // Add all background shapes into the background pane
        backgroundPane.getChildren().addAll(hexagon1, hexagon2, hexagon3, triangle1, triangle2);

        // Create the UI controls (title and buttons)
        Label title = new Label("SuperHexagon");
        title.setStyle("-fx-font-size: 48px; -fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #ecf0f1;");

        Button btnStart = new Button("Start");
        Button btnGameHistory = new Button("Game history");
        Button btnSettings = new Button("Settings");
        Button btnExit = new Button("Exit");

        // Define a common button style for modern appearance
        String buttonStyle = "-fx-font-size: 18px; -fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-padding: 10 20 10 20; -fx-background-radius: 5;";

        btnStart.setStyle(buttonStyle);
        btnGameHistory.setStyle(buttonStyle);
        btnSettings.setStyle(buttonStyle);
        btnExit.setStyle(buttonStyle);


        // On buttonStart press, create a new instance of test2 and call its start() method using the same Stage.
        btnStart.setOnAction(event -> {
        Main m =new Main();
            try {
                    m.start(primaryStage);  // This replaces the current scene with test2's scene.
            } catch (Exception e) {
                 e.printStackTrace();
            }});


        // Set action for the exit button to close the application
        btnExit.setOnAction(e -> primaryStage.close());

        // Arrange the title and buttons in a vertical box
        VBox vbox = new VBox(20);  // 20px spacing between nodes
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title, btnStart, btnGameHistory, btnSettings, btnExit);

        // Create the root layout using StackPane to layer the background and the UI on top
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundPane, vbox);

        Scene scene = new Scene(root, 700, 700);

        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create a hexagon shape given a center point and a radius
    private Polygon createHexagon(double centerX, double centerY, double radius) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            // Calculate each vertex angle offset by -30 degrees to make the flat side horizontal.
            double angle = Math.toRadians((60 * i) - 30);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            hexagon.getPoints().addAll(x, y);
        }
        return hexagon;
    }

    // Helper method to create a triangle shape given three vertices
    private Polygon createTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(x1, y1, x2, y2, x3, y3);
        return triangle;
    }
}