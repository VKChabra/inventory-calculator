package inventorycalculator.core;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;

public class ImageRecognition {
    private Tesseract tesseract;

    public ImageRecognition() {
        tesseract = new Tesseract();
        tesseract.setDatapath("tessdata"); // Set path to Tesseract data files
    }

    public String recognizeText(BufferedImage image) throws TesseractException {
        return tesseract.doOCR(image);
    }
}