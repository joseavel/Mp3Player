import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;

/**
 * holds information of a song
 */
public class Song {

    private MediaPlayer mediaPlayer;
    private Button songName;
    private Button songDuration;

    public void setSongName(String songName) {
        this.songName = new Button(songName);
    }
    public Button getSongName() {
        return songName;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = new Button(songDuration);
    }
    public Button getSongDuration() {
        return songDuration;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    //unit test
    public static void main(String[] args) {
        //launch(args);
    }
}
