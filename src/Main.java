import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    // Angle for triangle rotation
    private double angle = 0;

    // Scene dimensions
    private final double sceneWidth = 700;
    private final double sceneHeight = 700;

    // Orbit and hexagon radii
    private final double orbitRadius = (sceneWidth / 3.0) * 0.22;
    private final double hexRadius = orbitRadius - 20;

    // List of animated hexagons
    private ArrayList<Path> hexagons;
    private Random random;

    // Game over flag
    private boolean gameOver = false;
    // Keep track of the current active hexagon and its timeline
    private Path currentHexagon;
    private Timeline currentTimeline;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Center of the scene
        double centerX = sceneWidth / 2.0;
        double centerY = sceneHeight / 2.0;

        // Static Hexagon for visual reference (closed shape)
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

        // Orbiting Triangle (always closed)
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -5.0,   // Tip
                -5.0, 15.0,  // Bottom left
                5.0, 15.0    // Bottom right
        );
        triangle.setFill(Color.ORANGE);
        triangle.setTranslateX(centerX);
        triangle.setTranslateY(centerY - orbitRadius);
        triangle.setRotate(0);

        // Root Group
        Group root = new Group(staticHexagon, triangle);

        // Scene Setup
        Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.BLACK);

        // Key Press Handling for Triangle (only if game is not over)
        scene.setOnKeyPressed(e -> {
            if (gameOver) return;  // Prevent movement after game over

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

        // Define Hexagon Points
        // Note: Some of these hexagons may be intentionally missing sides (open paths).
        double x1 = 300.0, y1 = 0.0;
        double x2 = 150.0, y2 = 259.8;
        double x3 = -150.0, y3 = 259.8;
        double x4 = -300.0, y4 = 0.0;
        double x5 = -150.0, y5 = -259.8;
        double x6 = 150.0, y6 = -259.8;

        // Create Hexagon Variations
        hexagons = new ArrayList<>();

        // A fully closed hexagon
        Path hexagon1 = new Path();
        hexagon1.getElements().addAll(
                new MoveTo(x3,y3),
                new LineTo(x4,y4),
                new LineTo(x5,y5),
                new LineTo(x6,y6),
                new LineTo(x1,y1),
                new LineTo(x2,y2)
        );
        hexagon1.setStroke(Color.RED);
        hexagon1.setStrokeWidth(20);
        hexagon1.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon1);

        // An open hexagon missing the edge between the last and first point.
        Path hexagon2 = new Path();
        hexagon2.getElements().addAll(
                new MoveTo(x2, y2), new LineTo(x3, y3), new LineTo(x4, y4),
                new LineTo(x5, y5), new LineTo(x6, y6),new LineTo(x1, y1)
                // Note: no edge back to x1,y1 => open side.
        );
        hexagon2.setStroke(Color.RED);
        hexagon2.setStrokeWidth(20);
        hexagon2.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon2);

        // Other variations (example open paths)
        Path hexagon3 = new Path();
        hexagon3.getElements().addAll(
                new MoveTo(x1,y1),
                new LineTo(x2,y2),
                new LineTo(x3,y3),
                new LineTo(x4,y4),
                new LineTo(x5,y5),
                new LineTo(x6,y6)
        );
        hexagon3.setStroke(Color.RED);
        hexagon3.setStrokeWidth(20);
        hexagon3.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon3);

        Path hexagon4 = new Path();
        hexagon4.getElements().addAll(
                new MoveTo(x4,y4),
                new LineTo(x5,y5),
                new LineTo(x6,y6),
                new LineTo(x1,y1),
                new LineTo(x2,y2),
                new LineTo(x3,y3)
        );
        hexagon4.setStroke(Color.RED);
        hexagon4.setStrokeWidth(20);
        hexagon4.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon4);

        Path hexagon5 = new Path();
        hexagon5.getElements().addAll(
                new MoveTo(x5,y5),
                new LineTo(x6,y6),
                new LineTo(x1,y1),
                new LineTo(x2,y2),
                new LineTo(x3,y3),
                new LineTo(x4,y4)
        );
        hexagon5.setStroke(Color.RED);
        hexagon5.setStrokeWidth(20);
        hexagon5.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon5);

        Path hexagon6 = new Path();
        hexagon6.getElements().addAll(
                new MoveTo(x6,y6),
                new LineTo(x1,y1),
                new LineTo(x2,y2),
                new LineTo(x3,y3),
                new LineTo(x4,y4),
                new LineTo(x5,y5)
        );
        hexagon6.setStroke(Color.RED);
        hexagon6.setStrokeWidth(20);
        hexagon6.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon6);


        Path hexagon12 = new Path();
        hexagon12.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2),
                new MoveTo(x3, y3), new LineTo(x4, y4), new LineTo(x5, y5),
                new LineTo(x6, y6)
                // Two subpaths: one from x1->x2, and one open from x3->x4->x5->x6.
        );
        hexagon12.setStroke(Color.RED);
        hexagon12.setStrokeWidth(20);
        hexagon12.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon12);

        Path hexagon13 = new Path();
        hexagon13.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2),
                new LineTo(x3, y3), new LineTo(x4, y4), new MoveTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon13.setStroke(Color.RED);
        hexagon13.setStrokeWidth(20);
        hexagon13.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon13);

        Path hexagon11 = new Path();
        hexagon11.getElements().addAll(
                new MoveTo(x1, y1), new LineTo(x2, y2),
                new LineTo(x3, y3), new MoveTo(x4, y4), new LineTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon11.setStroke(Color.RED);
        hexagon11.setStrokeWidth(20);
        hexagon11.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon11);

        Path hexagon111 = new Path();
        hexagon111.getElements().addAll(
                new MoveTo(x1,y1),
                new LineTo(x2,y2),
                new MoveTo(x3,y3),
                new LineTo(x4,y4),
                new MoveTo(x5,y5),
                new LineTo(x6,y6)
        );
        hexagon111.setStroke(Color.RED);
        hexagon111.setStrokeWidth(20);
        hexagon111.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon111);

        Path hexagon112 = new Path();
        hexagon112.getElements().addAll(
                new MoveTo(x2,y2),
                new LineTo(x3,y3),
                new MoveTo(x4,y4),
                new LineTo(x5,y5),
                new MoveTo(x6,y6),
                new LineTo(x1,y1)
        );
        hexagon112.setStroke(Color.RED);
        hexagon112.setStrokeWidth(20);
        hexagon112.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon112);

        // You can add more variations as needed...
        // For testing, we duplicate one of them.
//        hexagons.add(hexagon3);

        random = new Random();

        // Start the hexagon animation and collision detection
        animateNextHexagon(root, centerX, centerY);

        // AnimationTimer to check for collisions continuously based on edge intersections
        AnimationTimer collisionTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver && currentHexagon != null) {
                    // Check if any triangle edge intersects any drawn hexagon edge.
                    if (checkEdgeCollision(triangle, currentHexagon)) {
                        gameOver = true;
                        if (currentTimeline != null) {
                            currentTimeline.stop();
                        }
                        System.out.println("Game Over!");
                        stop();
                    }
                }
            }
        };
        collisionTimer.start();

        // Stage Setup
        stage.setScene(scene);
        stage.setTitle("Combined Hexagon Game");
        stage.setResizable(false);
        stage.show();
    }

    // Collision detection: check if any triangle edge collides with any hexagon edge.
    private boolean checkEdgeCollision(Polygon triangle, Path hexagon) {
        // Get triangle edges (closed polygon, always add last->first)
        List<LineSegment> triangleEdges = getPolygonEdges(triangle);

        // Get hexagon edges using our helper that respects open subpaths.
        List<LineSegment> hexEdges = getEdgesFromPath(hexagon);

        // Test each pair for intersection.
        for (LineSegment tEdge : triangleEdges) {
            for (LineSegment hEdge : hexEdges) {
                if (linesIntersect(
                        tEdge.start.getX(), tEdge.start.getY(), tEdge.end.getX(), tEdge.end.getY(),
                        hEdge.start.getX(), hEdge.start.getY(), hEdge.end.getX(), hEdge.end.getY())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Extract edges from a closed polygon (triangle).
    private List<LineSegment> getPolygonEdges(Polygon polygon) {
        List<LineSegment> edges = new ArrayList<>();
        ObservableList<Double> points = polygon.getPoints();
        List<Point2D> coords = new ArrayList<>();
        for (int i = 0; i < points.size(); i += 2) {
            Point2D pt = polygon.localToScene(points.get(i), points.get(i + 1));
            coords.add(pt);
        }
        // Create edges, wrapping last to first.
        for (int i = 0; i < coords.size(); i++) {
            Point2D p1 = coords.get(i);
            Point2D p2 = coords.get((i + 1) % coords.size());
            edges.add(new LineSegment(p1, p2));
        }
        return edges;
    }

    // Extract only the drawn edges from a Path.
    // This method builds edges for each subpath separately.
    private List<LineSegment> getEdgesFromPath(Path path) {
        List<LineSegment> edges = new ArrayList<>();
        double startX = 0, startY = 0;
        double lastX = 0, lastY = 0;
        boolean inSubpath = false;

        for (PathElement elem : path.getElements()) {
            if (elem instanceof MoveTo) {
                MoveTo moveTo = (MoveTo) elem;
                startX = moveTo.getX();
                startY = moveTo.getY();
                lastX = startX;
                lastY = startY;
                inSubpath = true;
            } else if (elem instanceof LineTo) {
                LineTo lineTo = (LineTo) elem;
                if (inSubpath) {
                    Point2D p1 = path.localToScene(lastX, lastY);
                    Point2D p2 = path.localToScene(lineTo.getX(), lineTo.getY());
                    edges.add(new LineSegment(p1, p2));
                    lastX = lineTo.getX();
                    lastY = lineTo.getY();
                }
            } else if (elem instanceof ClosePath) {
                // Only close the subpath if a ClosePath element is explicitly provided.
                Point2D p1 = path.localToScene(lastX, lastY);
                Point2D p2 = path.localToScene(startX, startY);
                edges.add(new LineSegment(p1, p2));
                inSubpath = false;
            }
        }
        return edges;
    }

    // Helper class to represent a line segment.
    private static class LineSegment {
        Point2D start, end;
        public LineSegment(Point2D start, Point2D end) {
            this.start = start;
            this.end = end;
        }
    }

    // Check if two line segments (p1->p2 and p3->p4) intersect.
    private boolean linesIntersect(double x1, double y1, double x2, double y2,
                                   double x3, double y3, double x4, double y4) {
        double d1 = direction(x3, y3, x4, y4, x1, y1);
        double d2 = direction(x3, y3, x4, y4, x2, y2);
        double d3 = direction(x1, y1, x2, y2, x3, y3);
        double d4 = direction(x1, y1, x2, y2, x4, y4);
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
                ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }
        if (d1 == 0 && onSegment(x3, y3, x4, y4, x1, y1)) return true;
        if (d2 == 0 && onSegment(x3, y3, x4, y4, x2, y2)) return true;
        if (d3 == 0 && onSegment(x1, y1, x2, y2, x3, y3)) return true;
        if (d4 == 0 && onSegment(x1, y1, x2, y2, x4, y4)) return true;
        return false;
    }

    // Computes the cross product (used for orientation testing)
    private double direction(double ax, double ay, double bx, double by, double px, double py) {
        return (px - ax) * (by - ay) - (py - ay) * (bx - ax);
    }

    // Check if point (px,py) lies on the segment (ax,ay)->(bx,by)
    private boolean onSegment(double ax, double ay, double bx, double by, double px, double py) {
        return Math.min(ax, bx) <= px && px <= Math.max(ax, bx) &&
                Math.min(ay, by) <= py && py <= Math.max(ay, by);
    }

    // Animate the next hexagon obstacle.
    private void animateNextHexagon(Group root, double centerX, double centerY) {
        if (gameOver) return;

        // Select a random hexagon.
        int randomIndex = random.nextInt(hexagons.size());
        currentHexagon = hexagons.get(randomIndex);

        // Center the hexagon in the scene.
        currentHexagon.setTranslateX(centerX);
        currentHexagon.setTranslateY(centerY);

        // Reset scale.
        currentHexagon.setScaleX(1);
        currentHexagon.setScaleY(1);

        // Add hexagon to the scene.
        root.getChildren().add(currentHexagon);

        // Animation timeline for scaling down the hexagon.
        currentTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(currentHexagon.scaleXProperty(), 1),
                        new KeyValue(currentHexagon.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(2.5),
                        new KeyValue(currentHexagon.scaleXProperty(), 0),
                        new KeyValue(currentHexagon.scaleYProperty(), 0)
                )
        );

        // When the animation finishes, remove the hexagon and animate the next one.
        currentTimeline.setOnFinished(event -> {
            root.getChildren().remove(currentHexagon);
            animateNextHexagon(root, centerX, centerY);
        });
        currentTimeline.play();
    }
}