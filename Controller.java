import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    private MP3Player mp3Player;
    private MP3 mp3;
    private TableView<Song> songTableView;
    private Menu file;
    private Button playButton;
    private Button previousButton;
    private Button nextButton;

    public Controller(
            TableView<Song> songTableView, Menu file, Button playButton,
            Button previousButton, Button nextButton
    ) {

        this.mp3Player = new MP3Player();
        this.mp3 = new MP3();

        this.file = file;
        this.songTableView = songTableView;
        this.playButton = playButton;
        this.previousButton = previousButton;
        this.nextButton = nextButton;

        /* functionality */
        songTableViewStyle();
        //set handler for mp3 player
        mp3Task();
        //populate the table view with saved songs
        populateSongTableView();
        //set handlers for the play button
        controlButtonHandlers();
    }

    private void controlButtonHandlers(){
        this.playButton.setOnAction(event -> {
            if(this.songTableView.getItems().isEmpty()){return;}
            if(this.playButton.getText().equals("Play")){
                if(this.songTableView.getSelectionModel().getSelectedItem() == null){
                    cellHandler(this.songTableView.getItems().get(0));
                }else{
                    //TODO:resume the MP3Player
                    this.mp3.getMp3State().resumeSong(this.mp3Player);
                    playButtonChange("Pause");
                }

            }else{
                //TODO:pause the MP3Player
                this.mp3.getMp3State().pauseSong(this.mp3Player);
                playButtonChange("Play");
            }

            checkButton();
        });

        this.previousButton.setOnAction(event -> {
            if(this.songTableView.getItems().isEmpty()){return;}
            this.mp3.getMp3State().playPreviousSong(this.mp3Player);
            this.songTableView.getSelectionModel().selectPrevious();
            playButtonChange("Pause");
            checkButton();
        });

        this.nextButton.setOnAction(event -> {
            if(this.songTableView.getItems().isEmpty()){return;}
            this.mp3.getMp3State().playNextSong(this.mp3Player);
            this.songTableView.getSelectionModel().selectNext();
            playButtonChange("Pause");
            checkButton();
        });
    }

    private void checkButton(){

        //some code to disable buttons
        if(this.songTableView.getSelectionModel().getSelectedIndex() <= 0){
            this.previousButton.setDisable(true);
        }else{
            this.previousButton.setDisable(false);
        }

        if(this.songTableView.getSelectionModel().getSelectedIndex() >= this.songTableView.getItems().size() - 1){
            this.nextButton.setDisable(true);
        }else{
            this.nextButton.setDisable(false);
        }
    }

    private void playButtonChange(String change){
        this.playButton.setText(change);
    }

    private void setSongTableViewHandler(Song song) {

        cellStyle(song,"normal");
        //set both to same event because i want them to act as one
        song.getSongName().setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                cellHandler(song);
            }
        });

        song.getSongName().setOnMouseEntered(event -> {
            cellStyle(song,"bold");
        });

        song.getSongName().setOnMouseExited(event -> {
            cellStyle(song,"normal");
        });

        song.getSongDuration().setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                cellHandler(song);
            }
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
        song.getSongName().setStyle(
                s.styleHeightWidth("min", "width", "795") +
                s.rBackgroundColor("Transparent") + s.fontWeight(targetChange) + s.backgroundInsets("-3")
        );

        song.getSongDuration().setAlignment(Pos.CENTER_LEFT);
        song.getSongDuration().setStyle(
                s.styleHeightWidth("min", "width", "95") +
                s.rBackgroundColor("Transparent") + s.fontWeight(targetChange) + s.backgroundInsets("-3")
        );
    }

    private void cellHandler(Song song) {
        this.songTableView.getSelectionModel().select(song);
        this.mp3.getMp3State().playSong(this.mp3Player, songTableView.getSelectionModel().getSelectedIndex());
        playButtonChange("Pause");
        checkButton();
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
                playButton.setDisable(false);
                return null; //there is nothing important to return
            }
        };
        mp3Player.setRun(task);
    }

    private void populateSongTableView() {

        //TODO:load saved songs
        ArrayList<String> stringArrayList = new ArrayList<>();

        String directoryPath = "./songs";
        if(new File(directoryPath).exists()){

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
        }

        //create a thread to handle adding songs to the MP3Player
        Thread thread = new Thread(() -> {
            try {
                this.mp3.getMp3State().addSong(stringArrayList, mp3Player);
            }catch (Exception e){
                System.out.println("Mp3 player did not fully load.");
            }
        });
        thread.start();
    }
}
