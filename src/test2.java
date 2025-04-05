import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class test2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        double x1 = 300.0, y1 = 0.0;       // 0° point
        double x2 = 150.0, y2 = 259.8;       // 60° point
        double x3 = -150.0, y3 = 259.8;      // 120° point
        double x4 = -300.0, y4 = 0.0;        // 180° point
        double x5 = -150.0, y5 = -259.8;     // 240° point
        double x6 = 150.0, y6 = -259.8;      // 300° point

        // Create a Path to draw the hexagon with one missing side.
        // In this case, we'll omit the side between (x6, y6) and (x1, y1).
        Path hexagon1 = new Path();
        hexagon1.getElements().add(new MoveTo(x1, y1));
        hexagon1.getElements().add(new LineTo(x2, y2));
        hexagon1.getElements().add(new LineTo(x3, y3));
        hexagon1.getElements().add(new LineTo(x4, y4));
        hexagon1.getElements().add(new LineTo(x5, y5));
        hexagon1.getElements().add(new LineTo(x6, y6));
        // Notice: We do NOT add a LineTo that connects back to (x1, y1).

        // Set the stroke and fill properties.
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

        ArrayList<Path> hexagons = new ArrayList<>(Arrays.asList(hexagon1, hexagon2, hexagon3, hexagon4, hexagon5, hexagon6));
        Random random = new Random();
        int randomIndex = random.nextInt(hexagons.size());
        // Use a StackPane to center the hexagon.
        StackPane root = new StackPane(hexagons.get(randomIndex));
        Scene scene = new Scene(root, 600, 600);

        stage.setTitle("Hexagon with One Missing Side");
        stage.setScene(scene);
        stage.show();
    }
}