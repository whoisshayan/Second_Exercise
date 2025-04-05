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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class test2 extends Application {

    private ArrayList<Path> hexagons;
    private StackPane root;
    private Random random;

    @Override
    public void start(Stage stage) throws Exception {
        double x1 = 300.0, y1 = 0.0;       // 0° point
        double x2 = 150.0, y2 = 259.8;       // 60° point
        double x3 = -150.0, y3 = 259.8;      // 120° point
        double x4 = -300.0, y4 = 0.0;        // 180° point
        double x5 = -150.0, y5 = -259.8;     // 240° point
        double x6 = 150.0, y6 = -259.8;      // 300° point

        // Create different hexagon variations.
        Path hexagon1 = new Path();
        hexagon1.getElements().add(new MoveTo(x1, y1));
        hexagon1.getElements().add(new LineTo(x2, y2));
        hexagon1.getElements().add(new LineTo(x3, y3));
        hexagon1.getElements().add(new LineTo(x4, y4));
        hexagon1.getElements().add(new LineTo(x5, y5));
        hexagon1.getElements().add(new LineTo(x6, y6));
        hexagon1.setFill(Color.TRANSPARENT);
        hexagon1.setStroke(Color.RED);
        hexagon1.setStrokeWidth(3);

        Path hexagon2 = new Path();
        hexagon2.getElements().add(new MoveTo(x1, y1));
        hexagon2.getElements().add(new MoveTo(x2, y2));
        hexagon2.getElements().add(new LineTo(x3, y3));
        hexagon2.getElements().add(new LineTo(x4, y4));
        hexagon2.getElements().add(new LineTo(x5, y5));
        hexagon2.getElements().add(new LineTo(x6, y6));
        hexagon2.getElements().add(new LineTo(x1, y1));
        hexagon2.setFill(Color.TRANSPARENT);
        hexagon2.setStroke(Color.RED);
        hexagon2.setStrokeWidth(3);

        Path hexagon3 = new Path();
        hexagon3.getElements().add(new MoveTo(x1, y1));
        hexagon3.getElements().add(new LineTo(x2, y2));
        hexagon3.getElements().add(new MoveTo(x3, y3));
        hexagon3.getElements().add(new LineTo(x4, y4));
        hexagon3.getElements().add(new LineTo(x5, y5));
        hexagon3.getElements().add(new LineTo(x6, y6));
        hexagon3.getElements().add(new LineTo(x1, y1));
        hexagon3.setFill(Color.TRANSPARENT);
        hexagon3.setStroke(Color.RED);
        hexagon3.setStrokeWidth(3);

        Path hexagon4 = new Path();
        hexagon4.getElements().add(new MoveTo(x1, y1));
        hexagon4.getElements().add(new LineTo(x2, y2));
        hexagon4.getElements().add(new LineTo(x3, y3));
        hexagon4.getElements().add(new MoveTo(x4, y4));
        hexagon4.getElements().add(new LineTo(x5, y5));
        hexagon4.getElements().add(new LineTo(x6, y6));
        hexagon4.getElements().add(new LineTo(x1, y1));
        hexagon4.setFill(Color.TRANSPARENT);
        hexagon4.setStroke(Color.RED);
        hexagon4.setStrokeWidth(3);

        Path hexagon5 = new Path();
        hexagon5.getElements().add(new MoveTo(x1, y1));
        hexagon5.getElements().add(new LineTo(x2, y2));
        hexagon5.getElements().add(new LineTo(x3, y3));
        hexagon5.getElements().add(new LineTo(x4, y4));
        hexagon5.getElements().add(new MoveTo(x5, y5));
        hexagon5.getElements().add(new LineTo(x6, y6));
        hexagon5.getElements().add(new LineTo(x1, y1));
        hexagon5.setFill(Color.TRANSPARENT);
        hexagon5.setStroke(Color.RED);
        hexagon5.setStrokeWidth(3);

        Path hexagon6 = new Path();
        hexagon6.getElements().add(new MoveTo(x1, y1));
        hexagon6.getElements().add(new LineTo(x2, y2));
        hexagon6.getElements().add(new LineTo(x3, y3));
        hexagon6.getElements().add(new LineTo(x4, y4));
        hexagon6.getElements().add(new LineTo(x5, y5));
        hexagon6.getElements().add(new MoveTo(x6, y6));
        hexagon6.getElements().add(new LineTo(x1, y1));
        hexagon6.setFill(Color.TRANSPARENT);
        hexagon6.setStroke(Color.RED);
        hexagon6.setStrokeWidth(3);

        Path hexagon12 = new Path();
        hexagon12.getElements().add(new MoveTo(x1, y1));
        hexagon12.getElements().add(new LineTo(x2, y2));
        hexagon12.getElements().add(new MoveTo(x3, y3));
        hexagon12.getElements().add(new LineTo(x4, y4));
        hexagon12.getElements().add(new MoveTo(x5, y5));
        hexagon12.getElements().add(new LineTo(x6, y6));
        hexagon12.setFill(Color.TRANSPARENT);
        hexagon12.setStroke(Color.RED);
        hexagon12.setStrokeWidth(3);

        Path hexagon21 = new Path();
        hexagon21.getElements().add(new MoveTo(x1, y1));
        hexagon21.getElements().add(new MoveTo(x2, y2));
        hexagon21.getElements().add(new LineTo(x3, y3));
        hexagon21.getElements().add(new MoveTo(x4, y4));
        hexagon21.getElements().add(new LineTo(x5, y5));
        hexagon21.getElements().add(new MoveTo(x6, y6));
        hexagon21.getElements().add(new LineTo(x1, y1));
        hexagon21.setFill(Color.TRANSPARENT);
        hexagon21.setStroke(Color.RED);
        hexagon21.setStrokeWidth(3);

        // Store hexagons in an ArrayList.
        hexagons = new ArrayList<>(Arrays.asList(hexagon1, hexagon2, hexagon3, hexagon4, hexagon5, hexagon6, hexagon12, hexagon21, hexagon21));
        random = new Random();

        // Create a StackPane to hold the hexagon.
        root = new StackPane();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Random Hexagon Animation");
        stage.setScene(scene);
        stage.show();

        // Start the animation loop.
        animateNextHexagon();
    }

    // This method selects a random hexagon, adds it to the scene, and animates it.
    private void animateNextHexagon() {
        // Select a random hexagon.
        int randomIndex = random.nextInt(hexagons.size());
        Path selectedHexagon = hexagons.get(randomIndex);

        // Clear the previous hexagon and add the new one.
        root.getChildren().clear();
        root.getChildren().add(selectedHexagon);

        // Reset the hexagon's scale.
        selectedHexagon.setScaleX(1);
        selectedHexagon.setScaleY(1);

        // Create a timeline animation that scales the hexagon down to 0 over 5 seconds.
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(selectedHexagon.scaleXProperty(), 1),
                        new KeyValue(selectedHexagon.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(selectedHexagon.scaleXProperty(), 0),
                        new KeyValue(selectedHexagon.scaleYProperty(), 0)
                )
        );

        // When the animation finishes, call this method again to start a new random hexagon.
        timeline.setOnFinished(event -> animateNextHexagon());
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}