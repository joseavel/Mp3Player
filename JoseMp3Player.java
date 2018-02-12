import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Define Gui components
public class JoseMp3Player extends Application {
    private MenuBar menuBar;
    private Label title;
    private ListView<CustomButton> listOfSongs;
    private ListView<CustomButton> libraryOptions;
    private ComboBox<String> playlistChooser;
    private Menu file;
    private Stage popUpWindow;
    private TextField textField;
    private Button okButton;
    private Button cancelButton;

    public JoseMp3Player() {
        this.menuBar = new MenuBar();
        this.file = new Menu("File");
        fillMenuBar();
        this.listOfSongs = new ListView<>();
        this.title = new Label("Library");
        this.libraryOptions = new ListView<>();
        this.playlistChooser = new ComboBox<>();
        this.playlistChooser.setPromptText("Choose a playlist");
        newStage();

        setStyle();

        Controller controller = new Controller(
                file, this.listOfSongs, this.libraryOptions, this.playlistChooser,
                this.popUpWindow, this.okButton, this.cancelButton, this.textField, this.title
        );
    }

    private void setStyle() {

        Style style = new Style();
        String styl = style.backgroundInsets("1") + style.nonFocusSelection("#0099cc");
        this.listOfSongs.setStyle(styl + style.styleHeightWidth("max", "width", "500"));

        this.libraryOptions.setStyle(styl + style.styleHeightWidth("max", "height", "33"));
        this.playlistChooser.setStyle(style.fontWeight("normal") + style.styleHeightWidth("min", "height", "33"));

        //make lines shorter
        String gradient = style.dropShadow() + style.fontWeight("bold") + style.linearGradient("#ffffff", "#d9d9d9");
        String titleStyle = style.styleHeightWidth("min", "height", "25") + style.styleHeightWidth("min", "width", "424");
        this.title.setStyle(gradient + titleStyle);
        this.title.setAlignment(Pos.CENTER);
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
        VBox vBoxLeft = new VBox();
        vBoxLeft.getChildren().addAll(this.libraryOptions, this.playlistChooser);

        VBox vBoxCenter = new VBox();
        vBoxCenter.getChildren().addAll(this.title, this.listOfSongs);


        background.setTop(this.menuBar);
        background.setCenter(vBoxCenter);
        background.setLeft(vBoxLeft);
        this.libraryOptions.setMaxWidth(150);
        this.playlistChooser.setMaxWidth(150);

        primaryStage.setScene(new Scene(background, 575, 450));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /*
    private void tableViewColumns(){
        TableColumn<CustomButton, String> songLength = columns("Length", "songLength");
        songLength.setMaxWidth(100);
        songLength.setMinWidth(100);

        this.listOfSongs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.listOfSongs.getColumns().addAll(columns("Title", "songName"), songLength);
    }

    private TableColumn<CustomButton,String> columns(String columnName, String objectName){
        TableColumn<CustomButton, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(objectName));
        newColumn.setSortable(false);
        return newColumn;
    }
    */

    private void newStage() {
        this.popUpWindow = new Stage();
        this.popUpWindow.setTitle("Name the new playlist");
        BorderPane borderPane = new BorderPane();

        this.textField = new TextField();
        this.textField.setMaxWidth(277);
        borderPane.setCenter(textField);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        this.okButton = new Button("Ok");
        this.cancelButton = new Button("cancel");
        hBox.getChildren().addAll(this.okButton, this.cancelButton);
        borderPane.setBottom(hBox);

        popUpWindow.setScene(new Scene(borderPane, 300, 80));
    }
}
