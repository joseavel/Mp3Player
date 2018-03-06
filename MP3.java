//controll the states
public class MP3 {
    private Mp3State mp3State;
    private Mp3State standBy;;
    private Mp3State playing;
    private Mp3State paused;

    public MP3() {
        this.standBy = new StandBy(this);
        this.playing = new Playing(this);
        this.paused = new Paused(this);
        this.mp3State = this.standBy;
    }

    public Mp3State getMp3State() {
        return mp3State;
    }

    public void setMp3State(Mp3State mp3State) {
        this.mp3State = mp3State;
    }

    public Mp3State getStandBy() {
        return standBy;
    }

    public Mp3State getPlaying() {
        return playing;
    }

    public Mp3State getPaused() {
        return paused;
    }
}
