import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root=new Group();
        Scene scene=new Scene(root, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Fuck javafx");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("you cant escape unless you press q");
        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("q"));
        stage.show();
    }
}