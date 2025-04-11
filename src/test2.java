import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import java.util.ArrayList;

public class test2 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {


        double x1 = 300.0, y1 = 0.0;
        double x2 = 150.0, y2 = 259.8;
        double x3 = -150.0, y3 = 259.8;
        double x4 = -300.0, y4 = 0.0;
        double x5 = -150.0, y5 = -259.8;
        double x6 = 150.0, y6 = -259.8;

        ArrayList<javafx.scene.shape.Path> hexagons = new ArrayList<>();

        javafx.scene.shape.Path hexagon1 = new javafx.scene.shape.Path();
        hexagon1.getElements().addAll(
                new MoveTo(x2,y2),
                new LineTo(x3,y3),
                new MoveTo(x4,y4),
                new LineTo(x5,y5),
                new MoveTo(x6,y6),
                new LineTo(x1,y1)
                );
        hexagon1.setStroke(Color.RED);
        hexagon1.setStrokeWidth(20);
        hexagon1.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon1);

        hexagon1.setTranslateX(400);
        hexagon1.setTranslateY(300);

        Group root = new Group();
        root.getChildren().addAll(hexagons);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Combined Hexagon Game");
        stage.setResizable(false);
        stage.show();
    }
}