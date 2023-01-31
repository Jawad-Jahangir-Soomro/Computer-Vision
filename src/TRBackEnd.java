import net.sourceforge.tess4j.Tesseract;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TRBackEnd {
    Tesseract ts;
    JFileChooser fileChooser;
    FileNameExtensionFilter filter;
    String path;
    TextRecognizer textRecognizer;
    String text;
    public TRBackEnd(){
        ts = new Tesseract();
        ts.setLanguage("eng");

        try {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("user.dir"));

            filter = new FileNameExtensionFilter("All Pic", "png", "jpg");
            fileChooser.addChoosableFileFilter(filter);

            int a = fileChooser.showSaveDialog(null);
            if (a == JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().getAbsolutePath();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        try {
            text = ts.doOCR(getImage(path));

            textRecognizer = new TextRecognizer();
            textRecognizer.textField.setText(text);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            textRecognizer.download.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    text = textRecognizer.textField.getText();
                    createFile(text);
                    textRecognizer.dispose();
                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    private BufferedImage getImage(String imagePath) throws IOException {
        Mat mat ;
        mat = Imgcodecs.imread(imagePath);

        Mat grey = new Mat();
        Imgproc.cvtColor(mat,grey,Imgproc.COLOR_BGR2GRAY);

        Mat resized = new Mat();
        Size size = new Size(grey.width() * 1.9f,grey.height() * 1.9f);
        Imgproc.resize(grey,resized,size);

        final MatOfByte mof = new MatOfByte();
        Imgcodecs.imencode(".jpg,.png",resized,mof);
        byte[] imageData = mof.toArray();

        return ImageIO.read(new ByteArrayInputStream(imageData));
    }

    public void createFile(String textNew){
        String fileName = JOptionPane.showInputDialog(null,"Enter File Name.");

        try {
            File file = new File("TextFiles/" + fileName + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else if (file.exists()) {
                fileName = fileName + "1";
            }

            FileWriter fileWriter = new FileWriter("TextFiles/" + fileName + ".txt");
            fileWriter.write(textNew);
            fileWriter.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}