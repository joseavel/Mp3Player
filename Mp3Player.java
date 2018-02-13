import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * plays mp3
 */
public class Mp3Player {

    //the fields useful to this class
    private static ArrayList<Song> songArrayList;
    private static int currentIndex;
    private static int tracker;
    private Button run;

    //passing in one or many songPath
    public Mp3Player() {

        this.songArrayList = new ArrayList<>();
        this.run = new Button();
        this.currentIndex = 0;
        this.tracker = 0;
    }

    public void addManySong(ArrayList<String> songPath){

        if (songPath.size() == 0) {
            System.out.println("Error : you did not pass in anything");
            return;
        } else {
            ArrayList<String> filtered = new ArrayList<>();

            for (String str : songPath) {
                if (new File(str).exists()) {
                    filtered.add(str);
                } else {
                    System.out.println("Error : Path " + str + " does not exist");
                }
            }

            if (filtered.size() == 0) {
                System.out.println("Warning : There were no valid files passed in");
            } else {
                for (String str : filtered) {
                    setMediaPlayer(str, filtered.size());
                }
            }
        }
    }

    public void addOneSong(String path){
        if (new File(path).exists()) {
            setMediaPlayer(path,1);
        } else {
            System.out.println("Error : Path " + path + " does not exist");
        }
    }

    public void play(int index) {
        if (this.songArrayList.size() == 0) {
            System.out.println("Error: there is nothing to play");
        } else {

            try {

                if (this.songArrayList.get(this.currentIndex).getMediaPlayer() != null) {
                    this.songArrayList.get(this.currentIndex).getMediaPlayer().stop();
                }

                this.songArrayList.get(index).getMediaPlayer().play();

            } catch (Exception e) {
                System.out.println("Error : " + e);
                return;
            }

            this.currentIndex = index;
        }
    }

    public void playNext() {

        if (this.currentIndex < this.songArrayList.size() - 1) {
            play(this.currentIndex + 1);
        } else {
            System.out.println("there is no next song");
        }
    }

    public void playPrevious() {

        if (this.currentIndex > 0) {
            play(this.currentIndex - 1);
        } else {
            System.out.println("there is no previous song");
        }
    }

    public ArrayList<Song> getSongArrayList() {
        if (this.songArrayList.size() == 0) {
            System.out.println("Warning : the media player is empty");
        }
        return songArrayList;
    }

    public Button getRun() {
        if (songArrayList.size() == 0) {
            System.out.println("Warning : the media player is empty");
        }
        return run;
    }

    //create a media player
    private void setMediaPlayer(String songPath, int target) {

        Media media = new Media(new File(songPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        Song song = new Song();
        this.songArrayList.add(song);

        mediaPlayer.setOnReady(() -> {

            String songName = new File(songPath).getName().replace(".mp3", "");
            String songDuration = duration(mediaPlayer.getTotalDuration().toMillis());

            song.setSongName(songName);
            song.setSongDuration(songDuration);
            song.setMediaPlayer(mediaPlayer);

            //my goal is to wait for all media to be ready before proceeding
            fireRun(target);
        });
    }

    private void fireRun(int target) {

        this.tracker = this.tracker + 1;
        if (this.tracker == target) {
            this.tracker = 0;
            this.run.fire();
        }
    }

    private String duration(double duration) {

        //converting the double to long
        long newDuration = (long) duration;

        //breaking down the string format to make code more elegant
        String format = "%02d:%02d:%02d";
        long hours = TimeUnit.MILLISECONDS.toHours(newDuration);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(newDuration);
        long minutesExtended = TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(newDuration));

        long seconds = TimeUnit.MILLISECONDS.toSeconds(newDuration);
        long secondsExtended = TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(newDuration));

        //returning the new formatted duration
        return String.format(format, hours, minutes - minutesExtended, seconds - secondsExtended);
    }
}
