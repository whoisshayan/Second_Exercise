import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application {
    @Override
    public void start(Stage stage) {
        // Define the six points of the hexagon.
        double x1 = 300.0, y1 = 0.0;       // 0° point
        double x2 = 150.0, y2 = 259.8;       // 60° point
        double x3 = -150.0, y3 = 259.8;      // 120° point
        double x4 = -300.0, y4 = 0.0;        // 180° point
        double x5 = -150.0, y5 = -259.8;     // 240° point
        double x6 = 150.0, y6 = -259.8;      // 300° point

        // Create a Path to draw the hexagon with one missing side.
        // In this case, we'll omit the side between (x6, y6) and (x1, y1).
        Path hexagon = new Path();
        hexagon.getElements().add(new MoveTo(x1, y1));
        hexagon.getElements().add(new LineTo(x2, y2));
        hexagon.getElements().add(new LineTo(x3, y3));
        hexagon.getElements().add(new LineTo(x4, y4));
        hexagon.getElements().add(new LineTo(x5, y5));
        hexagon.getElements().add(new LineTo(x6, y6));
        // Notice: We do NOT add a LineTo that connects back to (x1, y1).

        // Set the stroke and fill properties.
        hexagon.setFill(Color.TRANSPARENT);
        hexagon.setStroke(Color.RED);
        hexagon.setStrokeWidth(3);

        // Use a StackPane to center the hexagon.
        StackPane root = new StackPane(hexagon);
        Scene scene = new Scene(root, 600, 600);

        stage.setTitle("Hexagon with One Missing Side");
        stage.setScene(scene);
        stage.show();

        // Animate the hexagon's scale properties to shrink it over 5 seconds.
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(hexagon.scaleXProperty(), 1),
                        new KeyValue(hexagon.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(hexagon.scaleXProperty(), 0),
                        new KeyValue(hexagon.scaleYProperty(), 0)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
