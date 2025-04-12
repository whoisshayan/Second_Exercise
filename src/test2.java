import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class test2 extends Application {
    @Override
    public void start(Stage primaryStage) {
        String musicFile = "https://www.bensound.com/bensound-music/bensound-slowmotion.mp3";

        Media media = new Media(musicFile);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Volume and loop settings
        mediaPlayer.setVolume(1.0);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Debug output
        mediaPlayer.setOnReady(() -> System.out.println("Media is ready!"));
        mediaPlayer.setOnPlaying(() -> System.out.println("Playing audio..."));
        mediaPlayer.setOnError(() -> {
            System.err.println("MediaPlayer error: " + mediaPlayer.getError());
        });
        media.setOnError(() -> {
            System.err.println("Media error: " + media.getError());
        });

        mediaPlayer.play();

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Online Music Player");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
