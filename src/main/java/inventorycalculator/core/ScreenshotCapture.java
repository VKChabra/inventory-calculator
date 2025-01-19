package inventorycalculator.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ScreenshotCapture {
    private Rectangle selectionRectangle;

    public BufferedImage captureSelectedArea() throws AWTException {
        ScreenSelectionWindow selectionWindow = new ScreenSelectionWindow();
        selectionWindow.showAndCapture();

        // Wait for the user to make a selection
        while (selectionWindow.isCapturing()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Get the selected area
        selectionRectangle = selectionWindow.getSelectedArea();
        if (selectionRectangle == null) {
            throw new IllegalStateException("No area selected");
        }

        // Capture the selected area
        Robot robot = new Robot();
        BufferedImage screenshot = robot.createScreenCapture(selectionRectangle);

        // Process the screenshot (call your processing logic here)
        processImage(screenshot);

        return screenshot;
    }

    private void processImage(BufferedImage screenshot) {
        // Example: Display the image in a JFrame (for testing)
        JFrame frame = new JFrame("Captured Screenshot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JLabel imageLabel = new JLabel(new ImageIcon(screenshot));
        frame.add(imageLabel);

        frame.setVisible(true);

        // Pass this image to the recognition system (ImageRecognition class)
        try {
            ImageRecognition recognizer = new ImageRecognition();
            String recognizedText = recognizer.recognizeText(screenshot);
            System.out.println("Recognized Text: " + recognizedText);
        } catch (Exception e) {
            System.err.println("Error processing image: " + e.getMessage());
        }
    }
}

// Custom Swing window for selecting a screen area (same as before)
class ScreenSelectionWindow extends JFrame {
    private Point startPoint;
    private Point endPoint;
    private Rectangle selectedArea;
    private boolean capturing;

    public ScreenSelectionWindow() {
        this.capturing = true;
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setOpacity(0.5f);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 50));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                endPoint = null;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                calculateSelectionRectangle();
                capturing = false;
                dispose();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                calculateSelectionRectangle();
                repaint();
            }
        });
    }

    public void showAndCapture() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public boolean isCapturing() {
        return capturing;
    }

    public Rectangle getSelectedArea() {
        return selectedArea;
    }

    private void calculateSelectionRectangle() {
        if (startPoint != null && endPoint != null) {
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(startPoint.x - endPoint.x);
            int height = Math.abs(startPoint.y - endPoint.y);
            selectedArea = new Rectangle(x, y, width, height);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (startPoint != null && endPoint != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(selectedArea.x, selectedArea.y, selectedArea.width, selectedArea.height);
        }
    }
}
