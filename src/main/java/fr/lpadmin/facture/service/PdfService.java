package fr.lpadmin.facture.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import fr.lpadmin.facture.exception.AppRuntimeException;

import java.awt.image.BufferedImage;

@Service
public class PdfService {

    public String toImage(File pdfFile) throws IOException {
     try (PDDocument document = loadPdf(pdfFile)) {

            PDFRenderer renderer = new PDFRenderer(document);

            // first page
            BufferedImage image =
                    renderer.renderImageWithDPI(0, 300);

            ByteArrayOutputStream baos =
                    new ByteArrayOutputStream();

            ImageIO.write(image, "png", baos);

            byte[] imageBytes = baos.toByteArray();

                // save image to file for debug
                // File output = pdfFile.toPath().resolveSibling(pdfFile.getName() + ".png").toFile();
                // try (FileOutputStream fos = new FileOutputStream(output)) {
                //     fos.write(imageBytes);
                // }

            return Base64.getEncoder()
                          .encodeToString(imageBytes);

        }   
    }

    public String toString(File pdfFile) throws IOException {
        try (PDDocument document = loadPdf(pdfFile)) {
        //     PDFRenderer renderer = new PDFRenderer(document);
        //     StringBuilder sb = new StringBuilder();
        //     for (int page = 0; page < 1/*document.getNumberOfPages()*/; page++) {
        //         // extract text from page
                
        //         sb.append(text);
        //     }
        //     return sb.toString();
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setEndPage(1);
            return stripper.getText(document);
        }
    }

    public PDDocument loadPdf(File file) {
        try {
            return Loader.loadPDF(new RandomAccessReadBufferedFile(file));
        } catch (IOException e) {
            throw new AppRuntimeException(e);
        }
    }

}
