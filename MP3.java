//controll the states
public class MP3 {
    Mp3State mp3State;
    Mp3State library;
    public MP3(){
        this.library = new LibraryMode();
        this.mp3State = library;
    }
}
