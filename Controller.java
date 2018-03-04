import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    private MP3Player mp3Player;
    private MP3 mp3;
    private TableView<Song> songTableView;
    private Menu file;

    public Controller(TableView<Song> songTableView, Menu file) {
        this.mp3Player = new MP3Player();
        this.mp3 = new MP3();

        this.file = file;
        this.songTableView = songTableView;
        songTableViewStyle();
        //set handler for mp3 player
        mp3Task();
        //populate the table view with saved songs
        populateSongTableView();
    }

    private void setSongTableViewHandler(Song song) {

        cellStyle(song,"normal");
        //set both to same event because i want them to act as one
        song.getSongName().setOnMouseClicked(event -> {
            cellHandler(song);
        });

        song.getSongName().setOnMouseEntered(event -> {
            cellStyle(song,"bold");
        });

        song.getSongName().setOnMouseExited(event -> {
            cellStyle(song,"normal");
        });

        song.getSongDuration().setOnMouseClicked(event -> {
            cellHandler(song);
        });

        song.getSongDuration().setOnMouseEntered(event -> {
            cellStyle(song,"bold");
        });

        song.getSongDuration().setOnMouseExited(event -> {
            cellStyle(song,"normal");
        });

    }

    private void songTableViewStyle(){
        this.songTableView.setStyle(
                new Style().nonFocusSelection("#0099cc") +
                new Style().backgroundInsets("1")
        );
    }

    private void cellStyle(Song song,String targetChange){

        Style s = new Style();
        //i want to make the button transparent
        song.getSongName().setAlignment(Pos.CENTER_LEFT);
        song.getSongName().setStyle(s.styleHeightWidth(
                "min", "width", "795") +
                s.rBackgroundColor("Transparent") + s.fontWeight(targetChange)
        );

        song.getSongDuration().setAlignment(Pos.CENTER_LEFT);
        song.getSongDuration().setStyle(s.styleHeightWidth(
                "min", "width", "95") +
                s.rBackgroundColor("Transparent") + s.fontWeight(targetChange)
        );
    }

    private void cellHandler(Song song) {
        this.songTableView.getSelectionModel().select(song);
        this.mp3.mp3State.playSong(this.mp3Player, songTableView.getSelectionModel().getSelectedIndex());
    }

    private void mp3Task() {

        //this task will be used as a signal to
        //update the mp3 player every time a song
        //is added
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                //update main table view
                songTableView.getItems().clear();
                ObservableList<Song> observableList;
                observableList = FXCollections.observableArrayList(mp3Player.getSongArrayList());

                for (Song song : observableList) {
                    setSongTableViewHandler(song);
                }

                songTableView.setItems(observableList);
                songTableView.getSelectionModel().focus(-1);
                return null; //there is nothing important to return
            }
        };
        mp3Player.setRun(task);
    }

    private void populateSongTableView() {

        //TODO:load saved songs
        ArrayList<String> stringArrayList = new ArrayList<>();

        String directoryPath = "./songs";
        File directory = new File(directoryPath);
        String[] files = directory.list();

        if (files.length == 0) {
            System.out.println("The directory is empty");
        } else {
            for (String file : files) {
                if(file.contains(".mp3")){
                    stringArrayList.add("./songs/" + file);
                }
            }
        }

        //create a thread to handle adding songs to the MP3Player
        Thread thread = new Thread(() -> {
            this.mp3.mp3State.addSong(stringArrayList, mp3Player);
        });
        thread.start();
    }
}
