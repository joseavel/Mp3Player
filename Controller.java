import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

//GUI components handlers
public class Controller {
    private ListView<CustomButton> listOfSongs;
    private ArrayList<String> StringCopy;
    private ListView<CustomButton> libraryOptions;
    private ComboBox<String> playlist;
    private Stage popUpWindow;
    private Button okButton;
    private Button cancelButton;
    private TextField textField;
    private Label title;
    private int objectInUse;
    private Style style;

    public Controller(
            Menu file, ListView<CustomButton> listOfSongs,
            ListView<CustomButton> libraryOptions, ComboBox<String> playlist,
            Stage popUpWindow, Button okButton, Button cancelButton,
            TextField textField, Label title) {

        this.style = new Style();
        this.listOfSongs = listOfSongs;
        this.libraryOptions = libraryOptions;
        this.playlist = playlist;
        this.popUpWindow = popUpWindow;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        this.textField = textField;
        this.title = title;
        this.objectInUse = -1;
        this.StringCopy = new ArrayList<>();
        FileBarHandler(file);
        playListHandler();
        libraryHandler();

    }

    private void libraryHandler() {
        CustomButton library = new CustomButton("Library", "./library.data");
        library.setAlignment(Pos.BASELINE_LEFT);
        library.setMinWidth(134);
        this.libraryOptions.getItems().add(library);
        library.setOnAction(event -> {

            playlist.getSelectionModel().select(null);
            playlist.setStyle(playlistStyle("normal"));
            libraryOptions.getSelectionModel().select(library);
            title.setText(library.getText());

            loadLibrary();

        });

        library.fire();

        this.libraryOptions.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                libStyle("bold");
            }
        });

        this.libraryOptions.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                libStyle("normal");
            }
        });
    }

    private void libStyle(String weight){
        String styl = style.backgroundInsets("1") + style.nonFocusSelection("#0099cc");
        String stylExtend = style.rBackgroundColor("transparent") + style.fontWeight(weight);
        libraryOptions.setStyle(styl + style.styleHeightWidth("max","height","33") + stylExtend);
    }

    private void playListHandler() {

        try {
            //testing
            ArrayList<String> listOfPlaylist;
            listOfPlaylist = (ArrayList<String>) Serialization.read("./listOfPlaylist.data");

            for (String str : listOfPlaylist) {
                playlist.getItems().add(str);
            }
        } catch (Exception e) {
            System.out.println("you havent created any playlist");
        }

        this.playlist.setOnAction(event -> {

            libraryOptions.getSelectionModel().select(-1);
            String selectedValue = playlist.getSelectionModel().getSelectedItem();
            playlist.setStyle(playlistStyle("normal"));
            listOfSongs.getItems().clear();
            StringCopy.clear();

            title.setText(playlist.getValue());

            //TODO:load playlist
        });

        this.playlist.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playlist.setStyle(playlistStyle("bold"));
            }
        });

        this.playlist.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playlist.setStyle(playlistStyle("normal"));
            }
        });
    }

    private String playlistStyle(String weigth){
        return style.fontWeight(weigth) + style.styleHeightWidth("min","height","33");
    }

    private void loadLibrary() {

        try {

            ArrayList<String> library;
            library = (ArrayList<String>) Serialization.read("./library.data");
            this.listOfSongs.getItems().clear();
            this.StringCopy.clear();

            for (String lib : library) {
                addButton(new File(lib).getName().replace(".mp3", ""), lib);
            }

            //everytime i refresh library i want to highlight the song the was previously
            //playing if there was one
            this.listOfSongs.getSelectionModel().select(objectInUse);

        } catch (Exception e) {
            System.out.println("Library is empty");
        }
    }

    private void FileBarHandler(Menu file) {

        MenuItem addFile = new MenuItem("Add File");
        file.getItems().add(addFile);
        addFile.setOnAction(this::BrowserHandler);

        MenuItem createPlaylist = new MenuItem("Create Playlist");
        file.getItems().add(createPlaylist);
        createPlaylist.setOnAction(this::createPlaylist);

    }

    private void createPlaylist(ActionEvent actionEvent) {
        this.okButton.setOnAction(event -> {
            ArrayList<String> storePlaylist = new ArrayList<>();
            try {
                for (String cb : playlist.getItems()) {
                    storePlaylist.add(cb);
                }

                playlist.getItems().add(textField.getText());
                storePlaylist.add(textField.getText());
                Serialization.write(storePlaylist, "./listOfPlaylist.data");
            } catch (Exception e) {
                System.out.println("playlist box might be empty");
            }
            textField.clear();
            popUpWindow.close();
        });

        this.cancelButton.setOnAction(event -> {
            textField.clear();
            popUpWindow.close();
        });
        popUpWindow.show();
    }

    private void BrowserHandler(ActionEvent actionEvent) {
        FileChooser choosFile = new FileChooser();
        choosFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("filter search", "*.mp3", "*.mp4"));
        File file = choosFile.showOpenDialog(null);

        try {
            addButton(file.getName().replace(".mp3", ""), file.getPath());
        } catch (Exception e) {
            System.out.println("You did not choose a file");
        }
    }

    private void addButton(String buttonName, String pathName) {
        CustomButton button = new CustomButton(buttonName, pathName);

        //a little style here because this is always changing
        button.setPrefWidth(409);
        button.setAlignment(Pos.BASELINE_LEFT);

        this.listOfSongs.getItems().add(button);

        //seems redundant but i need a field that i could serialize
        //without going to fetch every class with a reference to it
        this.StringCopy.add(button.getSongPath());

        Serialization.write(this.StringCopy, "./library.data");

        button.setOnAction(event -> {
            Player player = new Player();
            listOfSongs.getSelectionModel().select(button);
            objectInUse = listOfSongs.getSelectionModel().getSelectedIndex();
            player.play(button.getSongPath());
            player.autoPlay(button, this.listOfSongs);
        });

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setStyle(buttonStyle("bold"));

            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setStyle(buttonStyle("normal"));
            }
        });
    }

    private String buttonStyle(String weight){
        return style.rBackgroundColor("transparent") + style.fontWeight(weight);
    }
}