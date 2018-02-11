import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Define Gui components
public class JoseMp3Player extends Application {
    private MenuBar menuBar;
    private ListView<CustomButton> listOfSongs;
    private ListView<CustomButton> libraryOptions;
    private ComboBox<String> playlistChooser;
    private Menu file;

    public JoseMp3Player() {
        this.menuBar = new MenuBar();
        this.file = new Menu("File");
        fillMenuBar();

        this.listOfSongs = new ListView<>();
        this.libraryOptions = new ListView<>();
        this.playlistChooser = new ComboBox<>();
        this.playlistChooser.setPromptText("Choose a playlist");

        setStyle();

        Controller controller = new Controller(file, this.listOfSongs, this.libraryOptions, this.playlistChooser);
    }

    private void setStyle() {
        this.listOfSongs.setStyle("-fx-selection-bar-non-focused:#0099cc;-fx-background-insets: 1;");
        this.libraryOptions.setStyle("-fx-selection-bar-non-focused:#0099cc;-fx-background-insets: 1;-fx-max-height:33px;");
        this.playlistChooser.setStyle("-fx-background-color: #e6e6e6;-fx-background-insets:0;-fx-min-height:33px;" +
                "-fx-border-color: #d9d9d9;" + "-fx-border-width: 1.5 1.5 1.5 1.5;");
    }

    private void fillMenuBar() {
        this.menuBar.getMenus().add(this.file);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MP3 Player");
        BorderPane background = new BorderPane();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.libraryOptions, this.playlistChooser);

        background.setTop(this.menuBar);
        background.setCenter(this.listOfSongs);
        background.setLeft(vBox);
        this.libraryOptions.setMaxWidth(150);
        this.playlistChooser.setMaxWidth(150);

        primaryStage.setScene(new Scene(background, 550, 300));
        primaryStage.show();
    }
}
