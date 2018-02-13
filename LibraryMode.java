import javafx.scene.control.TableView;

public class LibraryMode implements Mp3State {
    @Override
    public void addOneSong(Mp3Player mp3Player,TableView<Song> songTableView) {

        int lastInserted = mp3Player.getSongArrayList().size() - 1;
        Song song = mp3Player.getSongArrayList().get(lastInserted);
        songTableView.getItems().add(song);

    }

    @Override
    public void playSong(Mp3Player mp3Player, int indexToPlay) {
        mp3Player.play(indexToPlay);
    }
}
