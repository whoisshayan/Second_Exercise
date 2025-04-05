import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    // Angle for triangle rotation
    private double angle = 0;

    // Scene dimensions (from Main)
    private final double sceneWidth = 700;
    private final double sceneHeight = 700;

    // Orbit and hexagon radii (from Main)
    private final double orbitRadius = (sceneWidth / 3.0) * 0.22;
    private final double hexRadius = orbitRadius - 20;

    // List of animated hexagons (from test2)
    private ArrayList<Path> hexagons;
    private Random random;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Center of the scene
        double centerX = sceneWidth / 2.0;  // 350
        double centerY = sceneHeight / 2.0; // 350

        // **Static Hexagon from Main**
        Polygon staticHexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double theta = Math.toRadians(60 * i - 30);
            double x = centerX + hexRadius * Math.cos(theta);
            double y = centerY + hexRadius * Math.sin(theta);
            staticHexagon.getPoints().addAll(x, y);
        }
        staticHexagon.setStroke(Color.WHITE);
        staticHexagon.setFill(null);
        staticHexagon.setRotate(staticHexagon.getRotate() + 30);

        // **Orbiting Triangle from Main**
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -15.0,   // Tip
                -15.0, 15.0,  // Bottom left
                15.0, 15.0    // Bottom right
        );
        triangle.setFill(Color.ORANGE);
        // Initial position at angle 0
        triangle.setTranslateX(centerX);
        triangle.setTranslateY(centerY - orbitRadius);
        triangle.setRotate(0);

        // **Root Group**
        Group root = new Group(staticHexagon, triangle);

        // **Scene Setup**
        Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.BLACK);

        // **Key Press Handling for Triangle**
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                angle += 60;  // Clockwise
            } else if (e.getCode() == KeyCode.LEFT) {
                angle -= 60;  // Counter-clockwise
            }
            double rad = Math.toRadians(angle);
            double x = centerX + orbitRadius * Math.sin(rad);
            double y = centerY - orbitRadius * Math.cos(rad);
            triangle.setTranslateX(x);
            triangle.setTranslateY(y);
            triangle.setRotate(angle);
        });

        // **Define Hexagon Points from test2**
        double x1 = 300.0, y1 = 0.0;
        double x2 = 150.0, y2 = 259.8;
        double x3 = -150.0, y3 = 259.8;
        double x4 = -300.0, y4 = 0.0;
        double x5 = -150.0, y5 = -259.8;
        double x6 = 150.0, y6 = -259.8;

        // **Create Hexagon Variations**
        hexagons = new ArrayList<>();

        Path hexagon1 = new Path();
        hexagon1.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2), new LineTo(x3, y3),
                new LineTo(x4, y4), new LineTo(x5, y5), new LineTo(x6, y6)
        );
        hexagon1.setStroke(Color.RED);
        hexagon1.setStrokeWidth(3);
        hexagon1.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon1);

        Path hexagon2 = new Path();
        hexagon2.getElements().addAll(
                new MoveTo(x2, y2), new LineTo(x3, y3), new LineTo(x4, y4),
                new LineTo(x5, y5), new LineTo(x6, y6), new LineTo(x1, y1)
        );
        hexagon2.setStroke(Color.RED);
        hexagon2.setStrokeWidth(3);
        hexagon2.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon2);

        Path hexagon3 = new Path();
        hexagon3.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2),
                new MoveTo(x3, y3), new LineTo(x4, y4), new LineTo(x5, y5),
                new LineTo(x6, y6), new LineTo(x1, y1)
        );
        hexagon3.setStroke(Color.RED);
        hexagon3.setStrokeWidth(3);
        hexagon3.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon3);

        Path hexagon4 = new Path();
        hexagon4.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2), new LineTo(x3, y3),
                new MoveTo(x4, y4), new LineTo(x5, y5), new LineTo(x6, y6),
                new LineTo(x1, y1)
        );
        hexagon4.setStroke(Color.RED);
        hexagon4.setStrokeWidth(3);
        hexagon4.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon4);

        Path hexagon5 = new Path();
        hexagon5.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2), new LineTo(x3, y3),
                new LineTo(x4, y4), new MoveTo(x5, y5), new LineTo(x6, y6),
                new LineTo(x1, y1)
        );
        hexagon5.setStroke(Color.RED);
        hexagon5.setStrokeWidth(3);
        hexagon5.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon5);

        Path hexagon6 = new Path();
        hexagon6.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2), new LineTo(x3, y3),
                new LineTo(x4, y4), new LineTo(x5, y5), new MoveTo(x6, y6),
                new LineTo(x1, y1)
        );
        hexagon6.setStroke(Color.RED);
        hexagon6.setStrokeWidth(3);
        hexagon6.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon6);

        Path hexagon12 = new Path();
        hexagon12.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2),
                new MoveTo(x3, y3), new LineTo(x4, y4),
                new MoveTo(x5, y5), new LineTo(x6, y6)
        );
        hexagon12.setStroke(Color.RED);
        hexagon12.setStrokeWidth(3);
        hexagon12.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon12);

        Path hexagon21 = new Path();
        hexagon21.getElements().addAll(
                new MoveTo(x1, y1), new MoveTo(x2, y2), new LineTo(x3, y3),
                new MoveTo(x4, y4), new LineTo(x5, y5), new MoveTo(x6, y6),
                new LineTo(x1, y1)
        );
        hexagon21.setStroke(Color.RED);
        hexagon21.setStrokeWidth(3);
        hexagon21.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon21);
        hexagons.add(hexagon21); // Duplicate as in test2

        random = new Random();

        // **Start Animation**
        animateNextHexagon(root, centerX, centerY);

        // **Stage Setup**
        stage.setScene(scene);
        stage.setTitle("Combined Hexagon Game");
        stage.setResizable(false);
        stage.show();
    }

    // **Animation Method for Hexagons**
    private void animateNextHexagon(Group root, double centerX, double centerY) {
        // Select a random hexagon
        int randomIndex = random.nextInt(hexagons.size());
        Path selectedHexagon = hexagons.get(randomIndex);

        // Center the hexagon in the scene
        selectedHexagon.setTranslateX(centerX);
        selectedHexagon.setTranslateY(centerY);

        // Reset scale
        selectedHexagon.setScaleX(1);
        selectedHexagon.setScaleY(1);

        // Add to root
        root.getChildren().add(selectedHexagon);

        // Animation timeline
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(selectedHexagon.scaleXProperty(), 1),
                        new KeyValue(selectedHexagon.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(2.5),
                        new KeyValue(selectedHexagon.scaleXProperty(), 0),
                        new KeyValue(selectedHexagon.scaleYProperty(), 0)
                )
        );

        // On finish, remove and animate next
        timeline.setOnFinished(event -> {
            root.getChildren().remove(selectedHexagon);
            animateNextHexagon(root, centerX, centerY);
        });
        timeline.play();
    }
}