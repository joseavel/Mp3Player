import java.util.ArrayList;

public class StandBy implements Mp3State {
    private MP3 mode;
    public StandBy(MP3 mode){
        this.mode = mode;
    }

    @Override
    public void addSong(ArrayList<String> songPath, MP3Player mp3Player) {
        mp3Player.addSong(songPath);
    }

    @Override
    public void playSong(MP3Player mp3Player, int selectedIndex) {
        mp3Player.playSong(selectedIndex);
        this.mode.setMp3State(this.mode.getPlaying());
    }

    @Override
    public void pauseSong(MP3Player mp3Player) {
        //cant pause anything while on standby
    }

    @Override
    public void resumeSong(MP3Player mp3Player) {
        //cant resume on standby
    }
}
