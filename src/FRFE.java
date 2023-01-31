import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FRFE extends JFrame {
    JPanel panel;
    JLabel topBarText;
    JPanel panelTop;
    JPanel panelBottom;
    JButton buttonFT;
    ImageIcon iconFT;
    JLabel labelFT;

    JButton buttonFR;
    ImageIcon iconFR;
    JLabel labelFR;
    JButton back;
    ImageIcon backIcon;
    FRFE(){
        back = new JButton();
        backIcon = new ImageIcon("images/back1.png");
        back.setBackground(Color.BLUE);
        back.setBounds(0,60,50,50);
        back.setIcon(backIcon);
        back.setFocusable(false);
        back.setBorderPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeFrame();
                MainScreen mainScreen = new MainScreen();
            }
        });
        back.setLayout(null);


        topBarText = new JLabel();
        topBarText.setForeground(Color.white);
        topBarText.setText("Face Recognition");
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

        buttonFT = new JButton();
        iconFT = new ImageIcon("images/FD1.png");
        buttonFT.setIcon(iconFT);
        buttonFT.setBounds(200,100,200,200);
        buttonFT.setBackground(Color.WHITE);
        buttonFT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposeFrame();
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        FaceTrainer fd = new FaceTrainer();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                fd.startCamera();
                            }
                        }).start();
                    }
                });
            }
        });
        labelFT = new JLabel();
        labelFT.setText("Face Trainer");
        labelFT.setFont(new Font("Ariel",Font.BOLD,25));
        labelFT.setBounds(225,320,200,40);

        buttonFR = new JButton();
        iconFR = new ImageIcon("images/FR1.png");
        buttonFR.setIcon(iconFR);
        buttonFR.setBounds(600,100,200,200);
        buttonFR.setBackground(Color.WHITE);
        buttonFR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String path = "E:\\Python Work\\files\\practice.py";
                    File file = new File(path);
                    if (file.exists()){
                        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
                        pro.waitFor();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"File is missing");
                    }

                }
                catch (Exception f){
                    System.out.println(e);
                }
            }
        });
        labelFR = new JLabel();
        labelFR.setText("Face Recognizer");
        labelFR.setFont(new Font("Ariel",Font.BOLD,25));
        labelFR.setBounds(605,320,200,40);

        panel = new JPanel();

        panel.add(panelTop);
        panel.add(panelBottom);
        panel.add(buttonFR);
        panel.add(labelFR);
        panel.add(buttonFT);
        panel.add(labelFT);
        panel.add(back);

        panel.setLayout(null);
        panel.setSize(1000,500);
        panel.setBackground(Color.cyan);


        ImageIcon icon = new ImageIcon("images/icon.png");
        this.setIconImage(icon.getImage());

        this.add(panel);

        this.setLayout(null);
        this.setSize(1000,500);
        this.setTitle("Face Recognition");
        this.setLocationRelativeTo(null);
        this.setFocusable(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void disposeFrame(){
        this.dispose();
    }
}
