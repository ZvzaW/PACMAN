import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class Menu extends JFrame {

    private boolean  isCtrlPressed = false;
    private boolean isShiftPressed = false;
    private boolean bChange = true;
    private boolean cChange = true;

    public Menu(){

        JPanel logo = new JPanel();
        JPanel buttons = new JPanel();
        JLabel label = new JLabel();

        JButton newGame = new JButton("New Game");
        JButton highScores = new JButton("High Scores");
        JButton exit = new JButton("Exit");

        ImageIcon imageIcon = new ImageIcon("resources/menu1.png");
        ImageIcon imageIcon2 = new ImageIcon("resources/menu2.png");

        label.setIcon(imageIcon);

        newGame.setForeground(Color.white);
        highScores.setForeground(Color.white);
        exit.setForeground(Color.white);

        Thread backgroundChange = new Thread(){
            @Override
            public void run() {
                while(getContentPane().isVisible()){
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (bChange){
                        label.setIcon(imageIcon);
                        bChange = false;
                    }
                    else {
                        label.setIcon(imageIcon2);
                        bChange = true;
                    }
                }
            }
        };

        Thread fontColorChange = new Thread(){
            @Override
            public void run() {
                while(getContentPane().isVisible()){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (cChange){
                        newGame.setForeground(Color.black);
                        cChange = false;
                    }
                    else {
                        newGame.setForeground(Color.white);
                        cChange = true;
                    }
                }
            }
        };

        backgroundChange.start();
        fontColorChange.start();

        logo.setBackground(Color.BLACK);
        buttons.setBackground(Color.black);

        newGame.setFont(new Font("Silom",Font.PLAIN,25));
        highScores.setFont(new Font("Silom",Font.PLAIN,17));
        exit.setFont(new Font("Silom",Font.PLAIN,17));


        newGame.setBorderPainted(false);
        highScores.setBorderPainted(false);
        exit.setBorderPainted(false);


        logo.setLayout(new FlowLayout());
        logo.add(label);

        buttons.setLayout(new GridLayout(3,1));

        buttons.add(newGame);
        buttons.add(highScores);
        buttons.add(exit);

        setLayout(new GridLayout(0,1));

        add(logo);
        add(buttons);

        setTitle("Menu");
        setSize(400,410);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);




        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                dispose();
                SwingUtilities.invokeLater(()-> new SizeBoradFrame());

            }
        });

        highScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                dispose();
                SwingUtilities.invokeLater(()-> {
                    try {
                        new Ranking();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });


            }
        });


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int fontSize = e.getComponent().getHeight();
                newGame.setFont(new Font("Silom",Font.BOLD, (int) (fontSize*0.07)));
                highScores.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.05)));
                exit.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.05)));

            }
        });

        addKeyListener(new GameKeyAdapter(this));
        requestFocusInWindow();
    }



}
