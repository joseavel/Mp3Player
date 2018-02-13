import javafx.scene.control.TableView;

public interface Mp3State {
    void addOneSong(Mp3Player mp3Player,TableView<Song> songTableView);
    void playSong(Mp3Player mp3Player,int indexToPlay);
}
