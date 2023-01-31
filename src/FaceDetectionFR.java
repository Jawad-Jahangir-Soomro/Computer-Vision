import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FaceDetectionFR extends JFrame {
    JPanel panel;
    JButton back;
    ImageIcon backIcon;
    JLabel label;
    JPanel panel1;
    JPanel faceP;
    JLabel face;
    JButton capture1;
    Rect[] faceArray;
    private ImageIcon icon;
    private VideoCapture capture;
    private Mat image;
    boolean clicked = false , closed = false;

    FaceDetectionFR(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        back = new JButton();
        backIcon = new ImageIcon("images/back1.png");
        back.setBackground(Color.BLUE);
        back.setBounds(0,0,50,50);
        back.setIcon(backIcon);
        back.setFocusable(false);
        back.setBorderPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capture.release();
                image.release();
                closed = true;
                disposeFrame();
                MainScreen mainScreen = new MainScreen();
            }
        });
        back.setLayout(null);

        label = new JLabel();
        label.setBounds(60, 60, 640, 480);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        panel1 = new JPanel();
        panel1.setBackground(Color.BLACK);
        panel1.setBounds(60,20,680,520);
        panel1.setLayout(new BorderLayout());
        panel1.add(label);

        face = new JLabel();
        face.setSize(80,40);
        face.setFont(new Font("Ariel",Font.BOLD,30));
        face.setVerticalAlignment(JLabel.CENTER);
        face.setHorizontalAlignment(JLabel.CENTER);

        faceP = new JPanel();
        faceP.setBounds(800,100,100,50);
        faceP.setBackground(Color.cyan);
        faceP.setLayout(new BorderLayout());
        faceP.add(face);

        capture1 = new JButton("Capture");
        capture1.setBounds(800,200,100,50);
        capture1.setFocusable(false);
        capture1.setBorderPainted(false);
        capture1.setBackground(Color.cyan);
        capture1.setFont(new Font("Ariel",Font.BOLD,17));
        capture1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicked = true;
            }
        });

        panel = new JPanel();

        panel.setLayout(null);
        panel.setSize(1000,600);
        panel.setBackground(Color.BLUE);

        panel.add(back);
        panel.add(panel1);
        panel.add(faceP);
        panel.add(capture1);

        ImageIcon icon = new ImageIcon("images/icon.png");
        this.setIconImage(icon.getImage());

        this.add(panel);

        this.setLayout(null);
        this.setSize(1000,600);
        this.setTitle("Face Detector");
        this.setLocationRelativeTo(null);
        this.setFocusable(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void disposeFrame(){
        this.dispose();
    }
    public void startCamera(){
        capture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;
        try {
            while (true) {
                capture.read(image);
                final MatOfByte buf = new MatOfByte();

                MatOfRect faces = new MatOfRect();

                Mat greyImage = new Mat();
                Imgproc.cvtColor(image, greyImage, Imgproc.COLOR_BGR2GRAY);

                Imgproc.equalizeHist(greyImage, greyImage);

                int height = greyImage.height();
                int absoluteFaceSize = 0;
                if (Math.round(height * 0.1f) > 0) {
                    absoluteFaceSize = Math.round(height * 0.1f);
                }

                CascadeClassifier faceCascade = new CascadeClassifier();

                faceCascade.load("data/haarcascade_frontalface_alt2.xml");
                faceCascade.detectMultiScale(image, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(absoluteFaceSize, absoluteFaceSize), new Size());

                faceArray = faces.toArray();

                for (int i = 0; i < faceArray.length; i++) {
                    Imgproc.rectangle(image, faceArray[i], new Scalar(0, 0, 255), 3);
                }

                face.setText(String.valueOf(faceArray.length));

                Imgcodecs.imencode(".jpg", image, buf);

                imageData = buf.toArray();

                icon = new ImageIcon(imageData);
                label.setIcon(icon);

                if (clicked) {
                    String name = JOptionPane.showInputDialog(this, "Enter image name");
                    if (name == null) {
                        name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                    }
                    Imgcodecs.imwrite("images/" + name + ".jpg", image);
                    clicked = false;
                }
                if (closed) {
                    break;
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}