import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private TableView<Song> songTableView;
    private MenuBar menuBar;
    private Menu file;
    private Button playButton;

    public Main() {
        songTableView();
        menuBar();
        controlButtons();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("MP3 Player");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(this.menuBar);
        borderPane.setCenter(this.songTableView);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5));
        hBox.getChildren().addAll(this.playButton);

        borderPane.setBottom(hBox);

        Controller controller = new Controller(this.songTableView, this.file, this.playButton);
        primaryStage.setScene(new Scene(borderPane, 900, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void controlButtons(){
        this.playButton = new Button("Play");
        controlButtonStyle(this.playButton,"5","60");
    }

    private void controlButtonStyle(Button button,String radius,String height){
        button.setStyle(
                new Style().radius(radius) +
                new Style().styleHeightWidth("min","height",height) +
                new Style().styleHeightWidth("min","width",height)
        );
    }

    private void menuBar() {
        this.menuBar = new MenuBar();
        this.file = new Menu("File");
        this.menuBar.getMenus().add(this.file);
    }

    private void songTableView() {
        this.songTableView = new TableView<>();
        TableColumn<Song, String> songDuration = columns("Duration", "songDuration");
        songDuration.setMinWidth(100);
        songDuration.setMaxWidth(100);

        this.songTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.songTableView.getColumns().addAll(columns("Title", "songName"), songDuration);
    }

    private TableColumn<Song, String> columns(String columnName, String objectName) {
        TableColumn<Song, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(objectName));
        newColumn.setSortable(false);
        return newColumn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
