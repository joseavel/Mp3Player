import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private TableView<Song> songTableView;
    private MenuBar menuBar;
    Menu file;
    public Main(){
        songTableView();
        menuBar();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MP3 Player");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(this.menuBar);
        borderPane.setCenter(this.songTableView);

        Controller controller = new Controller(this.songTableView, this.file);
        primaryStage.setScene(new Scene(borderPane,500,600));
        primaryStage.show();
    }

    private void menuBar(){
        this.menuBar = new MenuBar();
        this.file = new Menu("File");
        this.menuBar.getMenus().add(this.file);
    }

    private void songTableView(){
        this.songTableView = new TableView<>();
        TableColumn<Song, String> songDuration = columns("Duration","songDuration");
        songDuration.setMinWidth(100);
        songDuration.setMaxWidth(100);

        this.songTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.songTableView.getColumns().addAll(columns("Title", "songName"), songDuration);
    }

    private TableColumn<Song,String> columns(String columnName, String objectName){
        TableColumn<Song, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(objectName));
        newColumn.setSortable(false);
        return newColumn;
    }

    public static void main(String[] args){ launch(args); }
}
