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

public class FaceTrainer extends JFrame {
    JPanel panel;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JTextField FName;
    JLabel name;
    JButton captureB;
    JLabel labelPhoto;
    JButton back;
    ImageIcon backIcon;
    private ImageIcon icon;
    private VideoCapture capture;
    private Mat image;
    Rect[] faceArray;
    boolean clicked = false,closed = false;

    FaceTrainer(){
        labelPhoto = new JLabel();
        labelPhoto.setSize(640,480);
        labelPhoto.setVerticalAlignment(JLabel.CENTER);
        labelPhoto.setHorizontalAlignment(JLabel.CENTER);

        panel1 = new JPanel();
        panel1.setBackground(Color.BLACK);
        panel1.setBounds(60,30,660,500);
        panel1.setLayout(new BorderLayout());
        panel1.add(labelPhoto);

        FName = new JTextField();
        FName.setBackground(Color.WHITE);
        FName.setBounds(20,100,150,20);

        name = new JLabel("Name :");
        name.setFont(new Font("Ariel",Font.PLAIN,13));
        name.setBounds(20,80,100,20);

        captureB = new JButton("Capture");
        captureB.setBackground(Color.WHITE);
        captureB.setBorderPainted(false);
        captureB.setFocusable(false);
        captureB.setBounds(45,160,100,30);
        captureB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicked = true;
            }
        });

        panel2 = new JPanel();
        panel2.setBackground(Color.cyan);
        panel2.setBounds(3,3,195,300);
        panel2.setLayout(null);

        panel2.add(FName);
        panel2.add(name);
        panel2.add(captureB);

        panel3 = new JPanel();
        panel3.setBounds(750,40,201,306);
        panel3.setBackground(Color.BLACK);
        panel3.setLayout(null);
        panel3.add(panel2);

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
                FRFE frfe = new FRFE();
            }
        });
        back.setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(1000,600);
        panel.setBackground(Color.BLUE);
        panel.add(back);
        panel.add(panel1);
        panel.add(panel3);

        ImageIcon icon = new ImageIcon("images/icon.png");
        this.setIconImage(icon.getImage());

        this.add(panel);

        this.setLayout(null);
        this.setSize(1000,600);
        this.setTitle("Computer Vision");
        this.setLocationRelativeTo(null);
        this.setFocusable(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
                    Imgproc.rectangle(image, faceArray[i], new Scalar(0, 0, 255), 2);
                }

                Imgcodecs.imencode(".jpg", image, buf);

                imageData = buf.toArray();

                icon = new ImageIcon(imageData);
                labelPhoto.setIcon(icon);


                if (clicked) {
                    Imgcodecs.imwrite("E:\\Python Work\\pictures\\" + FName.getText() + ".jpg", image);
                    JOptionPane.showMessageDialog(null,"Image Saved with name " + FName.getText());
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

    public void disposeFrame(){
        this.dispose();
    }

}