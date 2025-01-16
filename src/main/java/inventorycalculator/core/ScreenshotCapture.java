package inventorycalculator.core;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenshotCapture {
    public BufferedImage captureScreenshot() throws AWTException {
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return robot.createScreenCapture(screenRect);
    }
}