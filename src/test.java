import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application {
    @Override
    public void start(Stage stage) {
        // Create a regular hexagon using Polygon.
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(
                300.0, 0.0,         // 0° point
                150.0, 259.8,       // 60° point
                -150.0, 259.8,      // 120° point
                -300.0, 0.0,        // 180° point
                -150.0, -259.8,     // 240° point
                150.0, -259.8       // 300° point
        );

        // Set the hexagon to be empty by using a transparent fill
        // and a visible stroke for the outline.
        hexagon.setFill(Color.TRANSPARENT);
        hexagon.setStroke(Color.RED);
        hexagon.setStrokeWidth(3);

        // Use a StackPane to center the hexagon on the screen
        StackPane root = new StackPane(hexagon);
        Scene scene = new Scene(root, 600, 600);

        stage.setTitle("Shrinking Empty Hexagon");
        stage.setScene(scene);
        stage.show();

        // Animate the hexagon's scale properties to shrink it over 5 seconds.
        Timeline timeline = new Timeline();
        KeyValue scaleXValue = new KeyValue(hexagon.scaleXProperty(), 0);
        KeyValue scaleYValue = new KeyValue(hexagon.scaleYProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(5), scaleXValue, scaleYValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}