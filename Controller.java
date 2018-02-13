import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    private Mp3Player mp3Player;
    private MP3 mp3;
    private TableView<Song> songTableView;
    private Menu file;

    public Controller(TableView<Song> songTableView, Menu file){
        this.mp3Player = new Mp3Player();
        this.mp3 = new MP3();
        this.songTableView = songTableView;
        songTableViewHandlers();
        this.file = file;
        //populateSongTableView();
        //create file handlers
        fileHandler();

    }

    private void songTableViewHandlers(){

        this.songTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int indexParams = Controller.this.songTableView.getSelectionModel().getSelectedIndex();
                Controller.this.mp3.mp3State.playSong(Controller.this.mp3Player,indexParams);
            }
        });
    }

    private String songTableViewStyle(String weight){
        return "-fx-font-weight:" + weight + ";";
    }

    private void fileHandler(){
        MenuItem addFile = new MenuItem("Add File");
        this.file.getItems().add(addFile);
        addFile.setOnAction(this::browseFile);

        //MenuItem createPlaylist = new MenuItem("Create Playlist");
        //file.getItems().add(createPlaylist);
        //createPlaylist.setOnAction(this::createPlaylist);
    }

    private void browseFile(ActionEvent event){
        FileChooser choosFile = new FileChooser();
        choosFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("filter search", "*.mp3", "*.mp4"));
        File file = choosFile.showOpenDialog(null);

        try {
            this.mp3Player.addOneSong(file.getPath());
            this.mp3Player.getRun().setOnAction(event1 -> {
                this.mp3.mp3State.addOneSong(mp3Player, songTableView);
            });
        } catch (Exception e) {
            System.out.println("You did not choose a file");
        }
    }

    private void populateSongTableView(){
        //populate the table view with some songs from a folder

        /*
        this.mp3Player = new Mp3Player();
        this.mp3Player.getRun().setOnAction(event -> {
            ArrayList<Song> songArrayList = Controller.this.mp3Player.getSongArrayList();
            ObservableList<Song> songObservableList = FXCollections.observableArrayList(songArrayList);
            Controller.this.songTableView.setItems(songObservableList);
        });
        */
    }


}
