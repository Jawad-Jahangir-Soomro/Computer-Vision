import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    JPanel panel;
    JLabel topBarText;
    JPanel panelTop;
    JPanel panelBottom;
    JButton buttonTR;
    ImageIcon iconTR;
    JLabel labelTR;

    JButton buttonFD;
    ImageIcon iconFD;
    JLabel labelFD;
    JButton buttonFR;
    ImageIcon iconFR;
    JLabel labelFR;

    MainScreen(){
        topBarText = new JLabel();
        topBarText.setForeground(Color.white);
        topBarText.setText("ComputerVision");
        topBarText.setVerticalAlignment(JLabel.CENTER);
        topBarText.setHorizontalAlignment(JLabel.CENTER);
        topBarText.setFont(new Font("Ariel",Font.BOLD,25));

        panelTop = new JPanel();
        panelTop.setBackground(Color.BLUE);
        panelTop.setBounds(0,20,1000,35);
        panelTop.setLayout(new BorderLayout());
        panelTop.add(topBarText);

        panelBottom = new JPanel();
        panelBottom.setBackground(Color.BLUE);
        panelBottom.setBounds(0,428,1000,35);
        panelBottom.setLayout(new BorderLayout());

        buttonTR = new JButton();
        iconTR = new ImageIcon("images/TR2.png");
        buttonTR.setIcon(iconTR);
        buttonTR.setBounds(100,100,200,200);
        buttonTR.setBackground(Color.WHITE);
        labelTR = new JLabel();
        labelTR.setText("Text Recognizer");
        labelTR.setFont(new Font("Ariel",Font.BOLD,25));
        labelTR.setBounds(105,320,200,40);
        buttonTR.addActionListener(e -> {
            disposeFrame();
            new TextRecognizer();
        });

        buttonFD = new JButton();
        iconFD = new ImageIcon("images/FD1.png");
        buttonFD.setIcon(iconFD);
        buttonFD.setBounds(400,100,200,200);
        buttonFD.setBackground(Color.WHITE);
        labelFD = new JLabel();
        labelFD.setText("Face Detector");
        labelFD.setFont(new Font("Ariel",Font.BOLD,25));
        labelFD.setBounds(420,320,200,40);
        buttonFD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeFrame();

                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        FaceDetectionFR ld = new FaceDetectionFR();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ld.startCamera();
                            }
                        }).start();
                    }
                });
            }
        });

        buttonFR = new JButton();
        iconFR = new ImageIcon("images/FR1.png");
        buttonFR.setIcon(iconFR);
        buttonFR.setBounds(700,100,200,200);
        buttonFR.setBackground(Color.WHITE);
        labelFR = new JLabel();
        labelFR.setText("Face Recognizer");
        labelFR.setFont(new Font("Ariel",Font.BOLD,25));
        labelFR.setBounds(705,320,200,40);
        buttonFR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeFrame();
                new FRFE();
            }
        });

        panel = new JPanel();

        panel.add(panelTop);
        panel.add(panelBottom);
        panel.add(buttonTR);
        panel.add(labelTR);
        panel.add(buttonFD);
        panel.add(labelFD);
        panel.add(buttonFR);
        panel.add(labelFR);

        panel.setLayout(null);
        panel.setSize(1000,500);
        panel.setBackground(Color.cyan);

        ImageIcon icon = new ImageIcon("images/icon.png");
        this.setIconImage(icon.getImage());

        this.add(panel);
        this.setLayout(null);
        this.setSize(1000,500);
        this.setTitle("Computer Vision");
        this.setLocationRelativeTo(null);
        this.setFocusable(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    public void disposeFrame(){
        this.dispose();
    }
}