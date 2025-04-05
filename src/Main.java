import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    // Angle that determines the triangle's position on the orbit.
    private double angle = 0;

    // Scene dimensions.
    private final double sceneWidth = 700;
    private final double sceneHeight = 700;

    // Orbit radius for the triangle.
    private final double orbitRadius = (sceneWidth / 3.0) * 0.22;

    // Hexagon's radius (for the orbit hexagon).
    private final double hexRadius = orbitRadius - 20;

    @Override
    public void start(Stage stage) {
        double centerX = sceneWidth / 2.0;
        double centerY = sceneHeight / 2.0;

        // --- Create the orbit hexagon and triangle ---
        // Orbit hexagon (for visual reference of the triangle's path).
        Polygon orbitHexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double theta = Math.toRadians(60 * i - 30);
            double x = centerX + hexRadius * Math.cos(theta);
            double y = centerY + hexRadius * Math.sin(theta);
            orbitHexagon.getPoints().addAll(x, y);
        }
        orbitHexagon.setStroke(Color.WHITE);
        orbitHexagon.setFill(null);
        orbitHexagon.setRotate(orbitHexagon.getRotate() + 30);

        // The orbiting triangle.
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -15.0,   // Tip (points outward when not rotated)
                -15.0, 15.0,  // Bottom left
                15.0, 15.0    // Bottom right
        );
        triangle.setFill(Color.ORANGE);
        // Initial position: at angle 0 (above the center).
        triangle.setTranslateX(centerX);
        triangle.setTranslateY(centerY - orbitRadius);

        // --- Create the obstacle hexagon that shrinks repeatedly ---
        Polygon obstacleHexagon = new Polygon();
        obstacleHexagon.getPoints().addAll(
                300.0, 0.0,         // 0° point
                150.0, 259.8,       // 60° point
                -150.0, 259.8,      // 120° point
                -300.0, 0.0,        // 180° point
                -150.0, -259.8,     // 240° point
                150.0, -259.8       // 300° point
        );
        obstacleHexagon.setFill(Color.TRANSPARENT);
        obstacleHexagon.setStroke(Color.WHITE);
        obstacleHexagon.setStrokeWidth(3);
        // Position it at the center (you can adjust as needed).
        obstacleHexagon.setTranslateX(centerX);
        obstacleHexagon.setTranslateY(centerY);

        // --- Combine all nodes into one scene ---
        Group root = new Group(orbitHexagon, triangle, obstacleHexagon);
        Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.BLACK);

        // Listen for key presses to update the triangle's orbit position and rotation.
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                angle += 60;  // Rotate clockwise.
            } else if (e.getCode() == KeyCode.LEFT) {
                angle -= 60;  // Rotate counter-clockwise.
            }
            double rad = Math.toRadians(angle);
            double x = centerX + orbitRadius * Math.sin(rad);
            double y = centerY - orbitRadius * Math.cos(rad);
            triangle.setTranslateX(x);
            triangle.setTranslateY(y);
            triangle.setRotate(angle);
        });

        // --- Set up the obstacle hexagon animation ---
        Timeline obstacleTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(obstacleHexagon.scaleXProperty(), 1),
                        new KeyValue(obstacleHexagon.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(obstacleHexagon.scaleXProperty(), 0),
                        new KeyValue(obstacleHexagon.scaleYProperty(), 0)
                )
        );
        obstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        obstacleTimeline.play();

        stage.setScene(scene);
        stage.setTitle("Combined Animation");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}