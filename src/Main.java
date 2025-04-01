import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Main extends Application {

    // Angle that determines the triangle's position on the circle (in degrees).
    private double angle = 0;

    // Define scene dimensions.
    private final double sceneWidth = 700;
    private final double sceneHeight = 700;

    // Set the radius to be one-third of the scene's width, then reduced to 0.22 times that.
    private final double radius = (sceneWidth / 3.0) * 0.22;

    // The center of the scene.
    private final double centerX = sceneWidth / 2.0;
    private final double centerY = sceneHeight / 2.0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Create a triangle with coordinates relative to its own coordinate space.
        // This triangle is defined so that its tip is at (0, -15) and the base at (-15, 15) and (15, 15).
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -15.0,  // Top (tip)
                -15.0, 15.0, // Bottom left
                15.0, 15.0   // Bottom right
        );
        triangle.setFill(Color.ORANGE);

        // Set the initial position on the circle (angle = 0).
        // When angle is 0, the triangle is at (centerX, centerY - radius) and its tip faces upward.
        triangle.setTranslateX(centerX);
        triangle.setTranslateY(centerY - radius);

        Scene scene = getScene(triangle);

        stage.setScene(scene);
        stage.setTitle("Triangle Revolving on a Circle");
        stage.setResizable(false);
        stage.setWidth(sceneWidth);
        stage.setHeight(sceneHeight);
        stage.show();

        // Optionally request focus if key events are not received automatically:
        // scene.requestFocus();
    }

    private Scene getScene(Polygon triangle) {
        Group root = new Group(triangle);
        Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.BLACK);

        // Listen for key presses to update the angle, position, and rotation of the triangle.
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                angle += 60;  // Increase angle for clockwise movement.
            } else if (e.getCode() == KeyCode.LEFT) {
                angle -= 60;  // Decrease angle for counter-clockwise movement.
            }

            // Convert angle to radians for the trigonometric functions.
            double rad = Math.toRadians(angle);

            // Calculate the new x and y coordinates along the circle.
            double x = centerX + radius * Math.sin(rad);
            double y = centerY - radius * Math.cos(rad);
            triangle.setTranslateX(x);
            triangle.setTranslateY(y);

            // Rotate the triangle so its tip always faces outward.
            // Since the triangle's default orientation points upward (which is the outward direction at angle=0),
            // we simply set its rotation to the current angle.
            triangle.setRotate(angle);
        });
        return scene;
    }
}
