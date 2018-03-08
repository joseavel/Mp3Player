import java.util.ArrayList;

public class Playing implements Mp3State {
    private MP3 mode;
    public Playing(MP3 mode) {
        this.mode = mode;
    }

    @Override
    public void addSong(ArrayList<String> songPath, MP3Player mp3Player) {
        //we are not adding song to mp3 player while playing for now
    }

    @Override
    public void playSong(MP3Player mp3Player, int selectedIndex) {
        this.mode.setMp3State(this.mode.getStandBy());
        mode.getMp3State().playSong(mp3Player,selectedIndex);
    }

    @Override
    public void pauseSong(MP3Player mp3Player) {
        mp3Player.pauseSong();
        this.mode.setMp3State(this.mode.getPaused());
    }

    @Override
    public void resumeSong(MP3Player mp3Player) {
        //cant resume if its already playing
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
