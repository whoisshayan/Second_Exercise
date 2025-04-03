import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Main extends Application {

    // Angle that determines the triangle's position on the orbit (in degrees).
    private double angle = 0;

    // Scene dimensions.
    private final double sceneWidth = 700;
    private final double sceneHeight = 700;

    // Define the orbit radius for the triangle's center.
    private final double orbitRadius = (sceneWidth / 3.0) * 0.22;

    // Define the hexagon's radius (smaller than orbitRadius so the triangle won't intersect it).
    private final double hexRadius = orbitRadius - 20;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Calculate the center of the scene.
        double centerX = sceneWidth / 2.0;
        double centerY = sceneHeight / 2.0;

        // Create a hexagon centered at (centerX, centerY).
        Polygon hexagon = new Polygon();
        // Generate 6 vertices with a -30° offset so the top is flat.
        for (int i = 0; i < 6; i++) {
            double theta = Math.toRadians(60 * i - 30);
            double x = centerX + hexRadius * Math.cos(theta);
            double y = centerY + hexRadius * Math.sin(theta);
            hexagon.getPoints().addAll(x, y);
        }
        hexagon.setStroke(Color.WHITE); // Outline for visibility.
        hexagon.setFill(null);          // Transparent fill.
        hexagon.setRotate(hexagon.getRotate() +30);
        // Create a triangle with its points defined relative to its own coordinate space.
        // The tip is at (0, -15) and the base at (-15, 15) and (15, 15).
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -15.0,   // Tip (points outward when not rotated)
                -15.0, 15.0,  // Bottom left
                15.0, 15.0    // Bottom right
        );
        triangle.setFill(Color.ORANGE);

        // Set the triangle's initial position along the orbit.
        // At angle 0, its center will be at (centerX, centerY - orbitRadius).
        triangle.setTranslateX(centerX);
        triangle.setTranslateY(centerY - orbitRadius);

        // Add both shapes to the scene.
        Group root = new Group(hexagon, triangle);
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

            // Rotate the triangle so that its tip always faces outward.
            triangle.setRotate(angle);
        });

        stage.setScene(scene);
        stage.setTitle("Centered Hexagon and Orbiting Triangle");
        stage.setResizable(false);
        stage.setWidth(sceneWidth);
        stage.setHeight(sceneHeight);
        stage.show();
    }
}