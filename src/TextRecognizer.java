import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextRecognizer extends JFrame {
    JPanel panel;
    JPanel leftPanel;
    JButton back;
    ImageIcon backIcon;
    JButton upload;
    ImageIcon uploadIcon;
    JLabel uploadL;
    JButton download;
    ImageIcon downloadIcon;
    JLabel downloadL;
    JButton sound;
    ImageIcon soundIcon;
    JLabel soundL;
    JTextArea textField;
    TRBackEnd backEnd;
    JScrollPane scrollBar;
    TextRecognizer(){
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
                disposeFrame();
                MainScreen mainScreen = new MainScreen();
            }
        });
        back.setLayout(null);

        upload = new JButton();
        uploadIcon = new ImageIcon("images/Upload4.png");
        upload.setBackground(Color.BLUE);
        upload.setBounds(70,70,80,70);
        upload.setIcon(uploadIcon);
        upload.setFocusable(false);
        upload.setBorderPainted(false);
        upload.setLayout(null);
        uploadL = new JLabel();
        uploadL.setText("Upload");
        uploadL.setForeground(Color.WHITE);
        uploadL.setFont(new Font("Ariel",Font.BOLD,18));
        uploadL.setBounds(180,100,80,25);
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backEnd =new TRBackEnd();
            }
        });

        download = new JButton();
        downloadIcon = new ImageIcon("images/download2.png");
        download.setBackground(Color.BLUE);
        download.setBounds(70,190,80,70);
        download.setIcon(downloadIcon);
        download.setFocusable(false);
        download.setBorderPainted(false);
        download.setLayout(null);
        downloadL = new JLabel();
        downloadL.setText("Save");
        downloadL.setForeground(Color.WHITE);
        downloadL.setFont(new Font("Ariel",Font.BOLD,18));
        downloadL.setBounds(180,210,130,25);

        sound = new JButton();
        soundIcon = new ImageIcon("images/sound1.png");
        sound.setBackground(Color.BLUE);
        sound.setBounds(70,310,80,70);
        sound.setIcon(soundIcon);
        sound.setFocusable(false);
        sound.setBorderPainted(false);
        sound.setLayout(null);
        soundL = new JLabel();
        soundL.setText("Voice");
        soundL.setForeground(Color.WHITE);
        soundL.setFont(new Font("Ariel",Font.BOLD,18));
        soundL.setBounds(180,335,130,25);
        sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                Voice voice;
                VoiceManager vm = VoiceManager.getInstance();
                voice = vm.getVoice("kevin16");
                voice.allocate();
                voice.speak(textField.getText());
            }
        });

        leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(0,0,300,500);
        leftPanel.add(back);
        leftPanel.add(upload);
        leftPanel.add(uploadL);
        leftPanel.add(download);
        leftPanel.add(downloadL);
        leftPanel.add(sound);
        leftPanel.add(soundL);
        leftPanel.setLayout(null);

        textField = new JTextArea();

        textField.setFont(new Font("Consolas",Font.PLAIN,12));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setCaretColor(Color.BLACK);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setText("Upload an Image....");

        scrollBar = new JScrollPane(textField);
        scrollBar.setBounds(400,50,500,360);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(1000,500);
        panel.setBackground(Color.cyan);
        panel.add(leftPanel);
        panel.add(scrollBar);

        ImageIcon icon = new ImageIcon("images/icon.png");

        this.add(panel);
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.setSize(1000,500);
        this.setTitle("TextRecognizer");
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
