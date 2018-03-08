import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * this class simply plays mp3 files
 */
public class MP3Player extends Application{

    private static MediaPlayer mediaPlayer;
    private static Semaphore semaphore;
    private static ArrayList<Song> songArrayList;
    private Task run;

    private int currentSongIndex;
    private int itemCounter;
    private int totalItems;

    public MP3Player() {
        defaultTask();
        songArrayList = new ArrayList<>();
        semaphore = new Semaphore(1);
        this.currentSongIndex = 0;
        this.itemCounter = 0;
        this.totalItems = 0;
    }

    private void defaultTask(){
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("you did not set the task, please do.");
                return null;
            }
        };
        setRun(task);
    }

    public void addSong(ArrayList<String> songPath) {

        this.totalItems = songPath.size();

        //i want to waitCounter if songPath is empty
        if (songPath.size() == 0) {
            System.out.println("Nothing was passed in");
            return;
        }

        for (String str : songPath) {

            //waitCounter if str is a valid path
            if (new File(str).exists()) {

                try {
                    semaphore.acquire();
                    setMediaPlayer(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                verifyItemCount();
            }
        }
    }

    private void verifyItemCount() {
        this.itemCounter = this.itemCounter + 1;
        if (this.itemCounter == this.totalItems) {
            this.itemCounter = 0;
            this.run.run();
        }
    }


    //create a media player
    private void setMediaPlayer(String songPath) {

        //create a media player.
        Media media = new Media(new File(songPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //trigger
                System.out.println(new File(songPath).getName() + " timed out.");
                timer.cancel();
                semaphore.release();
                verifyItemCount();
            }
        }, 5000);

        mediaPlayer.setOnReady(() -> {
            timer.cancel();

            //TODO:code to create song object
            String songName = new File(songPath).getName().replace(".mp3", "");
            String songDuration = duration(mediaPlayer.getTotalDuration().toMillis());

            Song song = new Song();
            song.setSongName(songName);
            song.setSongDuration(songDuration);
            song.setMediaPlayer(mediaPlayer);
            songArrayList.add(song);
            semaphore.release();
            verifyItemCount();
        });
    }

    public void playSong(int songIndex) {

        //waitCounter if song array list is empty
        if (getSongArrayList().size() == 0) {
            return;
        }

        if (getSongArrayList().get(currentSongIndex).getMediaPlayer() != null) {
            getSongArrayList().get(currentSongIndex).getMediaPlayer().stop();
        }

        execute(songIndex);
    }

    private void execute(int songIndex){
        //make the index is not less than zero or more than song array list size
        if (songIndex >= 0 && songIndex < getSongArrayList().size()) {
            //System.out.println("Playing => " + getSongArrayList().get(songIndex).getSongName());
            getSongArrayList().get(songIndex).getMediaPlayer().play();
            this.currentSongIndex = songIndex;
        } else {
            System.out.println("invalid song selection");
        }
    }

    public void pauseSong(){
        //waitCounter if song array list is empty
        if (getSongArrayList().get(currentSongIndex).getMediaPlayer() == null) { return; }
        getSongArrayList().get(this.currentSongIndex).getMediaPlayer().pause();
    }

    public void resumeSong(){
        //waitCounter if song array list is empty
        if (getSongArrayList().get(currentSongIndex).getMediaPlayer() == null) { return; }
        execute(this.currentSongIndex);
    }

    public void playNext() {

        if (this.currentSongIndex == getSongArrayList().size() - 1) {
            System.out.println("There are no next song to play");
            return;
        }

        playSong(this.currentSongIndex + 1);
    }

    public void playPrevious() {

        if (this.currentSongIndex == 0) {
            System.out.println("There are no previous songs to play");
            return;
        }

        playSong(this.currentSongIndex - 1);
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

    public ArrayList<Song> getSongArrayList() {
        return songArrayList;
    }

    public void setSongArrayList(ArrayList<Song> songArrayList) {
        MP3Player.songArrayList = songArrayList;
    }

    public void setRun(Task run) {
        this.run = run;
    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    //unit test
    public static void main(String[] args) {

        MP3Player n = new MP3Player();
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\Comic Game Loop - Mischief.mp3");
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\East of Tunesia.mp3");
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\Forest Frolic Loop.mp3");
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\Induced Insanity Loop.mp3");
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\Magical Transition.mp3");
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\Backbeat.mp3");
        stringArrayList.add("C:\\Users\\josea\\IdeaProjects\\MP3\\songs\\DJ GIAN   Bachata Mix 2015 (The best).mp3");

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                n.playSong(0);
                //play music
                for(Song song : n.getSongArrayList()){
                    song.getMediaPlayer().setOnEndOfMedia(() -> {
                        n.playNext();
                    });
                }
                return null;
            }
        };
        n.setRun(task);
        n.addSong(stringArrayList);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
    }//only here for unit test

}