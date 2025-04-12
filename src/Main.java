import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    // Angle for triangle rotation
    private double angle = 0;

    // Time when the game started
    private long gameStartTime;

    // Added fields for pause/resume
    private long totalPausedTime = 0;
    private long pauseStartTime = 0;
    private boolean isPaused = false;
    private VBox pauseMenu;  // Pause menu overlay

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

    // MediaPlayer for background music (so we can mute/unmute)
    private MediaPlayer mediaPlayer;

    // Declare AnimationTimer references so they can be stopped/started on pause/resume
    private AnimationTimer timerDisplay;
    private AnimationTimer collisionTimer;

    // Store the rotation timeline so it can be paused/resumed
    private Timeline rotationTimeline;

    public static String playerName = "Unknown";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // 🎵 Add background music (from Bensound)
        String musicFile = "https://www.bensound.com/bensound-music/bensound-slowmotion.mp3";
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(1.0); // Set volume (1.0 = 100%)
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        mediaPlayer.play(); // Start playing

        double centerX = sceneWidth / 2.0;
        double centerY = sceneHeight / 2.0;

        // ----------------------------------------------------------------
        // Define the background pane (needed for the theme change)
        // ----------------------------------------------------------------
        Pane backgroundPane = new Pane();
        backgroundPane.setPrefSize(sceneWidth, sceneHeight);
        backgroundPane.setStyle("-fx-background-color: #2c3e50;");

        // ===========================================
        // Build the rotating game content (rotates)
        // ===========================================
        Group rotatingGroup = new Group();

        // Static Hexagon for visual reference (closed shape)
        Polygon staticHexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double theta = Math.toRadians(60 * i - 30);
            double x = centerX + hexRadius * Math.cos(theta);
            double y = centerY + hexRadius * Math.sin(theta);
            staticHexagon.getPoints().addAll(x, y);
        }
        staticHexagon.setStroke(Color.WHITE);
        staticHexagon.setFill(Color.BLACK);
        staticHexagon.setRotate(staticHexagon.getRotate() + 30);

        // Add graphic quadrilaterals (game background elements)
        Polygon GraphicQuadrilateral1 = new Polygon();
        GraphicQuadrilateral1.getPoints().setAll(
                381.33, 350.0,
                700.0, 350.0,
                700.0, 700.0,
                365.67, 377.15
        );
        GraphicQuadrilateral1.setFill(Color.WHITE);

        Polygon GraphicQuadrilateral2 = new Polygon();
        GraphicQuadrilateral2.getPoints().setAll(
                365.67, 377.15,
                700.0, 700.0,
                350.0, 700.0,
                350.0, 377.15
        );
        GraphicQuadrilateral2.setFill(Color.BLUE);

        Polygon GraphicQuadrilateral21 = new Polygon();
        GraphicQuadrilateral21.getPoints().setAll(
                350.0, 377.15,
                350.0, 700.0,
                0.0, 700.0,
                334.33, 377.15
        );
        GraphicQuadrilateral21.setFill(Color.WHITE);

        Polygon GraphicQuadrilateral3 = new Polygon();
        GraphicQuadrilateral3.getPoints().setAll(
                334.33, 377.15,
                0.0, 700.0,
                0.0, 350.0,
                318.67, 350.0
        );
        GraphicQuadrilateral3.setFill(Color.BLUE);

        Polygon GraphicQuadrilateral4 = new Polygon();
        GraphicQuadrilateral4.getPoints().setAll(
                318.67, 350.0,
                0.0, 350.0,
                0.0, 0.0,
                334.33, 322.85
        );
        GraphicQuadrilateral4.setFill(Color.WHITE);

        Polygon GraphicQuadrilateral5 = new Polygon();
        GraphicQuadrilateral5.getPoints().setAll(
                334.33, 322.85,
                0.0, 0.0,
                350.0, 0.0,
                350.0, 322.85
        );
        GraphicQuadrilateral5.setFill(Color.BLUE);

        Polygon GraphicQuadrilateral51 = new Polygon();
        GraphicQuadrilateral51.getPoints().setAll(
                350.0, 0.0,
                700.0, 0.0,
                365.67, 322.85,
                350.0, 322.85
        );
        GraphicQuadrilateral51.setFill(Color.WHITE);

        Polygon GraphicQuadrilateral6 = new Polygon();
        GraphicQuadrilateral6.getPoints().setAll(
                365.67, 322.85,
                700.0, 0.0,
                700.0, 350.0,
                381.33, 350.0
        );
        GraphicQuadrilateral6.setFill(Color.BLUE);

        // Orbiting Triangle (always closed)
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, -5.0,
                -5.0, 15.0,
                5.0, 15.0
        );
        triangle.setFill(Color.RED);
        triangle.setTranslateX(centerX);
        triangle.setTranslateY(centerY - orbitRadius);
        triangle.setRotate(0);

        rotatingGroup.getChildren().addAll(
                staticHexagon, GraphicQuadrilateral1, GraphicQuadrilateral2,
                GraphicQuadrilateral21, GraphicQuadrilateral3, GraphicQuadrilateral4,
                GraphicQuadrilateral5, GraphicQuadrilateral51, GraphicQuadrilateral6,
                triangle
        );

        // ===========================================
        // Build the UI overlay (fixed, non-rotating)
        // ===========================================
        Pane uiPane = new Pane();
        Label timerLabel = new Label("Time: 0.00 s");
        timerLabel.setLayoutX(10);
        timerLabel.setLayoutY(10);
        timerLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: red;");
        uiPane.getChildren().add(timerLabel);

        // ===========================================
        // Create the Pause Menu Overlay
        // ===========================================
        pauseMenu = new VBox(20);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setPrefSize(sceneWidth, sceneHeight);
        pauseMenu.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        Button btnBackToMenu = new Button("Back to Menu");
        Button btnMute = new Button("Mute Song");
        Button btnContinue = new Button("Continue");
        btnBackToMenu.setStyle("-fx-font-size: 20px; -fx-padding: 10 20 10 20; -fx-background-color: #3498db; -fx-text-fill: white;");
        btnMute.setStyle("-fx-font-size: 20px; -fx-padding: 10 20 10 20; -fx-background-color: #3498db; -fx-text-fill: white;");
        btnContinue.setStyle("-fx-font-size: 20px; -fx-padding: 10 20 10 20; -fx-background-color: #3498db; -fx-text-fill: white;");
        pauseMenu.getChildren().addAll(btnBackToMenu, btnMute, btnContinue);
        pauseMenu.setVisible(false);

        // ===========================================
        // Combine the backgroundPane, rotatingGroup, UI overlay, and pauseMenu using a StackPane.
        // ===========================================
        StackPane root = new StackPane(backgroundPane, rotatingGroup, uiPane, pauseMenu);
        Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.BLACK);

        // ===========================================
        // Key Press Handling for Pause and Gameplay
        // ===========================================
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                if(!isPaused){
                    pauseGame();
                    isPaused = true;
                } else {
                    resumeGame();
                    isPaused = false;
                }
                e.consume();
                return;
            }
            if(gameOver || isPaused) return;
            if(e.getCode() == KeyCode.RIGHT){
                angle += 60;
            } else if(e.getCode() == KeyCode.LEFT){
                angle -= 60;
            }
            double rad = Math.toRadians(angle);
            double x = centerX + orbitRadius * Math.sin(rad);
            double y = centerY - orbitRadius * Math.cos(rad);
            triangle.setTranslateX(x);
            triangle.setTranslateY(y);
            triangle.setRotate(angle);
        });

        // ===========================================
        // Pause Menu Button Actions
        // ===========================================
        btnBackToMenu.setOnAction(e -> {
            mediaPlayer.stop();
            Menu m = new Menu();
            try {
                m.start(stage);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        btnMute.setOnAction(e -> {
            boolean muted = mediaPlayer.isMute();
            mediaPlayer.setMute(!muted);
            btnMute.setText(muted ? "Mute Song" : "Unmute Song");
        });
        btnContinue.setOnAction(e -> {
            resumeGame();
            isPaused = false;
        });

        // ===========================================
        // Setup Hexagon Obstacles (added to rotatingGroup)
        // ===========================================
        double x1 = 300.0, y1 = 0.0;
        double x2 = 150.0, y2 = 259.8;
        double x3 = -150.0, y3 = 259.8;
        double x4 = -300.0, y4 = 0.0;
        double x5 = -150.0, y5 = -259.8;
        double x6 = 150.0, y6 = -259.8;

        hexagons = new ArrayList<>();
        Path hexagon1 = new Path();
        hexagon1.getElements().addAll(
                new MoveTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6),
                new LineTo(x1, y1),
                new LineTo(x2, y2)
        );
        hexagon1.setStroke(Color.RED);
        hexagon1.setStrokeWidth(20);
        hexagon1.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon1);

        Path hexagon2 = new Path();
        hexagon2.getElements().addAll(
                new MoveTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6),
                new LineTo(x1, y1)
        );
        hexagon2.setStroke(Color.RED);
        hexagon2.setStrokeWidth(20);
        hexagon2.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon2);

        Path hexagon3 = new Path();
        hexagon3.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon3.setStroke(Color.RED);
        hexagon3.setStrokeWidth(20);
        hexagon3.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon3);

        Path hexagon4 = new Path();
        hexagon4.getElements().addAll(
                new MoveTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6),
                new LineTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3)
        );
        hexagon4.setStroke(Color.RED);
        hexagon4.setStrokeWidth(20);
        hexagon4.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon4);

        Path hexagon5 = new Path();
        hexagon5.getElements().addAll(
                new MoveTo(x5, y5),
                new LineTo(x6, y6),
                new LineTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4)
        );
        hexagon5.setStroke(Color.RED);
        hexagon5.setStrokeWidth(20);
        hexagon5.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon5);

        Path hexagon6 = new Path();
        hexagon6.getElements().addAll(
                new MoveTo(x6, y6),
                new LineTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5)
        );
        hexagon6.setStroke(Color.RED);
        hexagon6.setStrokeWidth(20);
        hexagon6.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon6);

        Path hexagon12 = new Path();
        hexagon12.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new MoveTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon12.setStroke(Color.RED);
        hexagon12.setStrokeWidth(20);
        hexagon12.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon12);

        Path hexagon13 = new Path();
        hexagon13.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4),
                new MoveTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon13.setStroke(Color.RED);
        hexagon13.setStrokeWidth(20);
        hexagon13.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon13);

        Path hexagon11 = new Path();
        hexagon11.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new MoveTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon11.setStroke(Color.RED);
        hexagon11.setStrokeWidth(20);
        hexagon11.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon11);

        Path hexagon111 = new Path();
        hexagon111.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new MoveTo(x3, y3),
                new LineTo(x4, y4),
                new MoveTo(x5, y5),
                new LineTo(x6, y6)
        );
        hexagon111.setStroke(Color.RED);
        hexagon111.setStrokeWidth(20);
        hexagon111.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon111);

        Path hexagon112 = new Path();
        hexagon112.getElements().addAll(
                new MoveTo(x2, y2),
                new LineTo(x3, y3),
                new MoveTo(x4, y4),
                new LineTo(x5, y5),
                new MoveTo(x6, y6),
                new LineTo(x1, y1)
        );
        hexagon112.setStroke(Color.RED);
        hexagon112.setStrokeWidth(20);
        hexagon112.setFill(Color.TRANSPARENT);
        hexagons.add(hexagon112);

        // Set game start time and initialize random generator.
        gameStartTime = System.nanoTime();
        random = new Random();

        // ===========================================
        // Timer Display (updates every frame)
        // ===========================================
        timerDisplay = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver) {
                    this.stop();
                } else {
                    double elapsedSeconds = (now - gameStartTime - totalPausedTime) / 1_000_000_000.0;
                    timerLabel.setText(String.format("Time: %.2f s", elapsedSeconds));
                }
            }
        };
        timerDisplay.start();

        // ===========================================
        // Animate Hexagon Obstacles (added to rotatingGroup)
        // ===========================================
        animateNextHexagon(rotatingGroup, centerX, centerY);

        // ===========================================
        // Collision Detection
        // ===========================================
        collisionTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver && currentHexagon != null) {
                    if (checkEdgeCollision(triangle, currentHexagon)) {
                        gameOver = true;
                        if (currentTimeline != null) {
                            currentTimeline.stop();
                        }
                        // Calculate the survival time (excluding paused time) and save the player's score.
                        double elapsedSeconds = (System.nanoTime() - gameStartTime - totalPausedTime) / 1_000_000_000.0;
                        savePlayerScore(playerName, elapsedSeconds);
                        System.out.println("Game Over!");
                        showGameOverUI(uiPane, stage);
                        this.stop();
                    }
                }
            }
        };
        collisionTimer.start();

        // (Optional) Set a scene fill color.
        scene.setFill(Color.RED);

        stage.setScene(scene);
        stage.setTitle("Combined Hexagon Game");
        stage.setResizable(false);
        stage.show();

        // ===========================================
        // Start Rotating the Game Content Only
        // ===========================================
        rotateSceneRandomly(rotatingGroup);

        // -------------------------------------------------------------------
        // NEW CODE: Light theme that changes every 3 seconds.
        // -------------------------------------------------------------------
        Timeline themeTimeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            int choice = random.nextInt(2);
            String bgColor, shapeColor;
            if (choice == 0) {
                bgColor = "lightblue";
                shapeColor = "lightcoral";
            } else {
                bgColor = "lightgreen";
                shapeColor = "lightyellow";
            }
            // Apply the chosen background color to the background pane.
            backgroundPane.setStyle("-fx-background-color: " + bgColor + ";");
            // Also update the scene fill
            scene.setFill(Color.web(bgColor));
            // Update the static hexagon's fill for contrast
            staticHexagon.setFill(Color.web(shapeColor));
        }));
        themeTimeline.setCycleCount(Timeline.INDEFINITE);
        themeTimeline.play();
    }

    // -------------------- Pause/Resume Methods -------------------- //

    private void pauseGame() {
        pauseMenu.setVisible(true);
        pauseStartTime = System.nanoTime();
        timerDisplay.stop();
        collisionTimer.stop();
        if (currentTimeline != null) {
            currentTimeline.pause();
        }
        if (rotationTimeline != null) {
            rotationTimeline.pause();
        }
    }

    private void resumeGame() {
        pauseMenu.setVisible(false);
        long pauseDuration = System.nanoTime() - pauseStartTime;
        totalPausedTime += pauseDuration;
        timerDisplay.start();
        collisionTimer.start();
        if (currentTimeline != null) {
            currentTimeline.play();
        }
        if (rotationTimeline != null) {
            rotationTimeline.play();
        }
    }

    // -------------------- End Pause/Resume Methods -------------------- //

    // ----------------------------------------------
    // Helper method to add Game Over UI elements.
    // ----------------------------------------------
    private void showGameOverUI(Pane uiPane, Stage stage) {
        VBox gameOverBox = new VBox(20);
        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.setPrefSize(sceneWidth, sceneHeight);

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setStyle("-fx-font-size: 48px; -fx-text-fill: orange; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 24px; -fx-padding: 10 20 10 20;");
        backButton.setOnAction(actionEvent -> {
            Menu m = new Menu();
            try {
                m.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gameOverBox.getChildren().addAll(gameOverLabel, backButton);
        uiPane.getChildren().add(gameOverBox);
    }

    // ----------------------------------------------
    // Collision Detection: triangle vs hexagon.
    // ----------------------------------------------
    private boolean checkEdgeCollision(Polygon triangle, Path hexagon) {
        List<LineSegment> triangleEdges = getPolygonEdges(triangle);
        List<LineSegment> hexEdges = getEdgesFromPath(hexagon);
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
        for (int i = 0; i < coords.size(); i++) {
            Point2D p1 = coords.get(i);
            Point2D p2 = coords.get((i + 1) % coords.size());
            edges.add(new LineSegment(p1, p2));
        }
        return edges;
    }

    // Extract drawn edges from a Path.
    private List<LineSegment> getEdgesFromPath(Path path) {
        List<LineSegment> edges = new ArrayList<>();
        double startX = 0, startY = 0, lastX = 0, lastY = 0;
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
                Point2D p1 = path.localToScene(lastX, lastY);
                Point2D p2 = path.localToScene(startX, startY);
                edges.add(new LineSegment(p1, p2));
                inSubpath = false;
            }
        }
        return edges;
    }

    // Helper class representing a line segment.
    private static class LineSegment {
        Point2D start, end;
        public LineSegment(Point2D start, Point2D end) {
            this.start = start;
            this.end = end;
        }
    }

    // Determine if two line segments intersect.
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

    // Computes the cross-product for orientation testing.
    private double direction(double ax, double ay, double bx, double by, double px, double py) {
        return (px - ax) * (by - ay) - (py - ay) * (bx - ax);
    }

    // Check if (px,py) lies on the segment (ax,ay) to (bx,by).
    private boolean onSegment(double ax, double ay, double bx, double by, double px, double py) {
        return Math.min(ax, bx) <= px && px <= Math.max(ax, bx) &&
                Math.min(ay, by) <= py && py <= Math.max(ay, by);
    }

    // Animate the next hexagon obstacle by adding it to the rotating group.
    private void animateNextHexagon(Group rotatingGroup, double centerX, double centerY) {
        if (gameOver) return;
        int randomIndex = random.nextInt(hexagons.size());
        currentHexagon = hexagons.get(randomIndex);
        currentHexagon.setTranslateX(centerX);
        currentHexagon.setTranslateY(centerY);
        currentHexagon.setScaleX(1);
        currentHexagon.setScaleY(1);
        rotatingGroup.getChildren().add(currentHexagon);
        double elapsedSeconds = (System.nanoTime() - gameStartTime - totalPausedTime) / 1_000_000_000.0;
        double newDurationSeconds = Math.max(1.0, 2.5 - elapsedSeconds * 0.1);
        currentTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(currentHexagon.scaleXProperty(), 1),
                        new KeyValue(currentHexagon.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(newDurationSeconds),
                        new KeyValue(currentHexagon.scaleXProperty(), 0),
                        new KeyValue(currentHexagon.scaleYProperty(), 0)
                )
        );
        currentTimeline.setOnFinished(event -> {
            rotatingGroup.getChildren().remove(currentHexagon);
            animateNextHexagon(rotatingGroup, centerX, centerY);
        });
        currentTimeline.play();
    }

    // Continuously rotates only the game content (rotatingGroup).
    private void rotateSceneRandomly(Group rotatingGroup) {
        double newAngle = random.nextDouble() * 360;
        rotationTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), new KeyValue(rotatingGroup.rotateProperty(), newAngle))
        );
        rotationTimeline.setOnFinished(event -> rotateSceneRandomly(rotatingGroup));
        rotationTimeline.play();
    }

    // Save the player's score (name and survival time) in "players.txt".
    private void savePlayerScore(String playerName, double survivalTime) {
        try (FileWriter fw = new FileWriter("players.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(playerName + " - " + String.format("%.2f", survivalTime) + " s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}