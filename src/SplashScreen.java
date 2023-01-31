import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame implements Runnable{

    JLabel label;
    JLabel label1;
    ImageIcon image;
    JProgressBar bar;

    public SplashScreen(){
        bar = new JProgressBar();
        bar.setValue(0);
        bar.setBounds(50,400,768,10);
        bar.setStringPainted(true);
        bar.setFont(new Font("MV Boli",Font.BOLD,8));
        bar.setForeground(Color.blue);
        bar.setBackground(Color.CYAN);

        image = new ImageIcon("images/splashScreen5.png");

        label1 = new JLabel("Loading...");
        label1.setForeground(Color.white);
        label1.setBounds(60,360,100,50);

        label = new JLabel();
        label.setSize(875,489);
        label.setIcon(image);
        label.add(bar);
        label.add(label1);

        this.setLayout(null);

        ImageIcon icon = new ImageIcon("images/icon.png");
        this.setIconImage(icon.getImage());

        this.setSize(875,489);
        this.add(label);
        this.setLocationRelativeTo(null);
        this.setFocusable(false);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        run();
    }

    @Override
    public void run() {
        fill();
    }

    public void fill() {
        int counter =0;

        while(counter<=100) {

            bar.setValue(counter);
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
            counter +=1;
            if (counter == 100){
                this.dispose();
                MainScreen mainScreen = new MainScreen();
            }
        }
    }
}