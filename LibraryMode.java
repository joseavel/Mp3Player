import java.util.ArrayList;

public class LibraryMode implements Mp3State {

    @Override
    public void addSong(ArrayList<String> songPath, MP3Player mp3Player) {
        mp3Player.addSong(songPath);
    }

    @Override
    public void playSong(MP3Player mp3Player, int selectedIndex) {
        mp3Player.playSong(selectedIndex);
    }
}
