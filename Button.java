import java.awt.Color;

public class Button {
    private double x; // x position of button
    private double y; // y position of button
    private double length; // length of button
    private double height; // width of button
    private String buttonText; // text pertaining to button

    // Makes a new button, given the x and y position, length, width, and text
    public Button(double x, double y, double length, double height, String buttonText) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
        this.buttonText = buttonText;
    }

    // draws the button to Stdout
    public void drawButton() {
        StdDraw.rectangle(x, y, length, height);
    }

    // returns whether the user has clicked on the button
    public boolean withinBoundaries() {
        if (StdDraw.isMousePressed()) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            if ((x < this.x + length && x > this.x - length) && (y < this.y + height
                    && y > this.y - height)) {
                return true;
            }
        }
        return false;
    }

    // print button text to Stdout
    public void buttonText() {
        StdDraw.text(x, y, buttonText);
    }

    // Test cases
    // Creates a button object with the given start button dimensions, draws
    // the button and the text to standard output, and checks whether the
    // button is clicked
    public static void main(String[] args) {
        double START_X = 0.5;
        double START_Y = 0.2;
        double START_LENGTH = 0.2;
        double START_HEIGHT = 0.1;
        String START_BUTTON_TEXT = "START";
        Button startButton = new Button(START_X, START_Y,
                                        START_LENGTH, START_HEIGHT,
                                        START_BUTTON_TEXT);
        startButton.buttonText();
        startButton.drawButton();
        while (true) {
            if (startButton.withinBoundaries()) {
                StdDraw.clear(Color.ORANGE);
                break;
            }
        }

    }
}
