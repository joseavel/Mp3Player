import java.util.ArrayList;

public class Paused implements Mp3State {
    private MP3 mode;
    public Paused(MP3 mode) {
        this.mode = mode;
    }
    @Override
    public void addSong(ArrayList<String> songPath, MP3Player mp3Player) {
        //we are not adding any songs to the mp3 player while paused for now
    }

    @Override
    public void playSong(MP3Player mp3Player, int selectedIndex) {
        this.mode.setMp3State(this.mode.getStandBy());
        this.mode.getMp3State().playSong(mp3Player,selectedIndex);
    }

    @Override
    public void pauseSong(MP3Player mp3Player) {
        //cant pause anything if its already paused
    }

    @Override
    public void resumeSong(MP3Player mp3Player) {
        mp3Player.resumeSong();
        this.mode.setMp3State(this.mode.getPlaying());
    }

    @Override
    public void playNextSong(MP3Player mp3Player) {
        this.mode.setMp3State(this.mode.getStandBy());
        this.mode.getMp3State().playNextSong(mp3Player);
    }

    @Override
    public void playPreviousSong(MP3Player mp3Player) {
        this.mode.setMp3State(this.mode.getStandBy());
        this.mode.getMp3State().playPreviousSong(mp3Player);
    }
}
