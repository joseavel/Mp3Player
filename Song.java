import javafx.scene.media.MediaPlayer;

/**
 * holds information of a song
 */
public class Song {
    private MediaPlayer mediaPlayer;
    private String songName;
    private String songDuration;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    //unit test
    public static void main (String[] args){
        //launch(args);

    }
}
