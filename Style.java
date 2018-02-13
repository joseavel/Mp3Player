public class Style {
    public String styleHeightWidth(String minOrMax, String widthOrHeight, String px) {
        return "-fx-" + minOrMax + "-" + widthOrHeight + ":" + px + "px;";
    }

    public String fontWeight(String weight) {
        return "-fx-font-weight:" + weight + ";";
    }

    public String nonFocusSelection(String color) {
        return "-fx-selection-bar-non-focused:" + color + ";";
    }

    public String linearGradient(String firstColor, String secondColor) {
        return "-fx-background-color: linear-gradient(" + firstColor + "," + secondColor + ");";
    }

    public String rBackgroundColor(String color) {
        return "-fx-background-color:" + color + ";";
    }

    public String dropShadow() {
        return "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 6, 0.0 , 0 , 1 );";
    }

    public String backgroundInsets(String insets) {
        return "-fx-background-insets:" + insets + ";";
    }
}
