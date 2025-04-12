import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;

public class Menu extends Application {

    private static final String PLAYER_FILE = "players.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 🎵 Add background music (from Bensound)
        String musicFile = "https://www.bensound.com/bensound-music/bensound-slowmotion.mp3";
        Media media = new Media(musicFile);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(1.0); // 100%
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        // Create background pane with decorative shapes.
        Pane backgroundPane = new Pane();
        backgroundPane.setPrefSize(700, 700);
        backgroundPane.setStyle("-fx-background-color: #2c3e50;");

        Polygon hexagon1 = createHexagon(100, 150, 80);
        hexagon1.setFill(Color.web("#2980b9", 0.2));
        Polygon hexagon2 = createHexagon(600, 200, 60);
        hexagon2.setFill(Color.web("#8e44ad", 0.2));
        Polygon hexagon3 = createHexagon(350, 600, 100);
        hexagon3.setFill(Color.web("#27ae60", 0.2));

        Polygon triangle1 = createTriangle(50, 50, 150, 50, 100, 100);
        triangle1.setFill(Color.web("#e74c3c", 0.2));
        Polygon triangle2 = createTriangle(550, 650, 650, 650, 600, 600);
        triangle2.setFill(Color.web("#f1c40f", 0.2));

        backgroundPane.getChildren().addAll(hexagon1, hexagon2, hexagon3, triangle1, triangle2);

        // Create the UI controls (title and buttons)
        Label title = new Label("SuperHexagon");
        title.setStyle("-fx-font-size: 48px; -fx-font-family: 'Segoe UI', sans-serif; -fx-text-fill: #ecf0f1;");

        Button btnStart = new Button("Start");
        Button btnGameHistory = new Button("Game history");
        Button btnSettings = new Button("Settings");
        Button btnExit = new Button("Exit");

        // Define common style for buttons.
        String buttonStyle = "-fx-font-size: 18px; -fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-padding: 10 20 10 20; -fx-background-radius: 5;";
        btnStart.setStyle(buttonStyle);
        btnGameHistory.setStyle(buttonStyle);
        btnSettings.setStyle(buttonStyle);
        btnExit.setStyle(buttonStyle);

        // When "Start" is clicked, show a dialog to input the player's name.
        btnStart.setOnAction(event -> {
            showNameInputAndStartGame(primaryStage, mediaPlayer);
        });

        // When "Game history" is clicked, show a dialog with saved player names.
        btnGameHistory.setOnAction(e -> {
            showGameHistory();
        });

        // When "Settings" is clicked, show the settings dialog (mute/unmute option).
        btnSettings.setOnAction(e -> {
            showSettingsDialog(mediaPlayer);
        });

        // When "Exit" is clicked, stop music and close the application.
        btnExit.setOnAction(e -> {
            mediaPlayer.stop();
            primaryStage.close();
        });

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title, btnStart, btnGameHistory, btnSettings, btnExit);

        // Add the highest score label at the top of the VBox.
        Label highestScoreLabel = new Label(getHighestScore());
        highestScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        highestScoreLabel.setTextFill(Color.DARKBLUE);
        vbox.getChildren().add(0, highestScoreLabel);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundPane, vbox);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Opens a settings dialog with an option to mute/unmute the background music.
    private void showSettingsDialog(MediaPlayer mediaPlayer) {
        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.WINDOW_MODAL);
        settingsStage.setTitle("Settings");

        CheckBox muteCheckBox = new CheckBox("Mute Music");
        muteCheckBox.setSelected(mediaPlayer.isMute());
        muteCheckBox.setOnAction(e -> {
            mediaPlayer.setMute(muteCheckBox.isSelected());
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> settingsStage.close());

        VBox layout = new VBox(10, muteCheckBox, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 150);
        settingsStage.setScene(scene);
        settingsStage.showAndWait();
    }

    // Opens a dialog to input the player's name and then starts the game.
    private void showNameInputAndStartGame(Stage primaryStage, MediaPlayer mediaPlayer) {
        Stage inputStage = new Stage();
        inputStage.initOwner(primaryStage);
        inputStage.initModality(Modality.WINDOW_MODAL);
        inputStage.setTitle("Enter Your Name");

        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        Button btnStartGame = new Button("Start the Game");

        VBox vboxInput = new VBox(10, nameLabel, nameField, btnStartGame);
        vboxInput.setAlignment(Pos.CENTER);
        Scene sceneInput = new Scene(vboxInput, 300, 150);
        inputStage.setScene(sceneInput);
        inputStage.show();

        btnStartGame.setOnAction(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                Main.playerName = playerName; // Pass the name to Main!
                inputStage.close();
                mediaPlayer.stop(); // Optionally stop menu music
                Main m = new Main();
                m.start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your name.", ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    // Saves the player's name to players.txt.
    private void savePlayerName(String playerName) {
        try (FileWriter fw = new FileWriter(PLAYER_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads players.txt and shows the saved names in a dialog.
    private void showGameHistory() {
        StringBuilder history = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                history.append(line).append("\n");
            }
        } catch (IOException e) {
            history.append("No game history found.");
        }

        TextArea textArea = new TextArea(history.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefWidth(300);
        textArea.setPrefHeight(200);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game History");
        alert.setHeaderText("Player Names:");
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    // Helper method to create a hexagon shape given a center point and a radius.
    private Polygon createHexagon(double centerX, double centerY, double radius) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians((60 * i) - 30);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            hexagon.getPoints().addAll(x, y);
        }
        return hexagon;
    }

    // Helper method to create a triangle shape given three vertices.
    private Polygon createTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(x1, y1, x2, y2, x3, y3);
        return triangle;
    }

    // Method to read the highest score from players.txt
    private String getHighestScore() {
        String highestPlayer = "";
        double highestScore = 0.0;

        // Use the PLAYER_FILE constant to read scores from players.txt.
        File file = new File(PLAYER_FILE);
        if (!file.exists()) {
            return "No scores yet";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Expected format per line: "playerName - time s"
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String scoreStr = parts[1].trim();
                    // Remove the trailing "s" if it exists.
                    scoreStr = scoreStr.replace("s", "").trim();
                    double score = Double.parseDouble(scoreStr);
                    if (score > highestScore) {
                        highestScore = score;
                        highestPlayer = name;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        if (highestPlayer.isEmpty()) {
            return "No scores yet";
        }

        return "The highest score is " + highestPlayer + " - " + highestScore + " s";
    }
}