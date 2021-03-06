import javafx.scene.control.TableView;

import java.util.ArrayList;
public interface Mp3State {
    void addSong(ArrayList<String> songPath, MP3Player mp3Player);
    void playSong(MP3Player mp3Player, int selectedIndex);
    void pauseSong(MP3Player mp3Player);
    void resumeSong(MP3Player mp3Player);
    void playNextSong(MP3Player mp3Player);
    void playPreviousSong(MP3Player mp3Player);
}
