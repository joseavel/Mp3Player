import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

//plays mp3
public class Player {
    protected static MediaPlayer mediaPlayer;

    public void play(String songPath) {

        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.dispose();
        }

        try {

            Media media = new Media(new File(songPath).toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaPlayer.setOnReady(() -> Player.this.mediaPlayer.play());

        } catch (Exception e) {
            System.out.println("The system could not find File : " + songPath);
        }
    }

    public void autoPlay(CustomButton button, ListView<CustomButton> listOfSongs) {
        mediaPlayer.setOnEndOfMedia(() -> {

            listOfSongs.getSelectionModel().select(button);
            int selected = listOfSongs.getSelectionModel().getSelectedIndex();

            if (selected < listOfSongs.getItems().size() - 1) {
                listOfSongs.getSelectionModel().selectNext();
                listOfSongs.getItems().get(listOfSongs.getSelectionModel().getSelectedIndex()).fire();
            } else {
                System.out.println("End of Playlist");
            }
        });
    }
}
