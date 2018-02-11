import javafx.scene.control.Button;

import java.io.File;

public class CustomButton extends Button {
    private String songPath;

    public CustomButton(String name, String songPath) {
        super(name);

        this.songPath = songPath;

        setStyle("-fx-background-color: transparent;");
    }

    public String getSongPath() {
        return songPath;
    }
}
