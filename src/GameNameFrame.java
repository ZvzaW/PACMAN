import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;



public class GameNameFrame extends JFrame {


    private JFrame newGameBoard;
    private String score;
    public GameNameFrame(JFrame newGameBoard, String score){

        this.newGameBoard = newGameBoard;
        this.score = score;

        JPanel centrum = new JPanel(new GridLayout(2,1));
        centrum.setBackground(Color.black);

        JPanel buttonPanel = new JPanel(new GridLayout(1,1));
        buttonPanel.setBackground(Color.black);

        JLabel info = new JLabel("Name for this game:");
        info.setHorizontalAlignment(JLabel.HORIZONTAL);
        info.setForeground(Color.YELLOW);
        centrum.add(info);

        JTextField textField = new JTextField(15);
        textField.setBackground(Color.white);
        textField.setForeground(Color.black);
        centrum.add(textField);




        add(centrum, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setBorderPainted(false);

        okButton.setForeground(Color.yellow);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameName = textField.getText();
                if (gameName.equals(""))
                    gameName = "Unknown";

                int iScore = Integer.parseInt(score);
                Score wynik = new Score(iScore,gameName);
                try {
                    Ranking.writeToFile(wynik);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                dispose();
                newGameBoard.dispose();
                SwingUtilities.invokeLater(()-> new Menu());
            }
        });

        okButton.setBackground(Color.black);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int fontSize = e.getComponent().getHeight();
                okButton.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.08)));
                textField.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.085)));
                info.setFont(new Font("Silom",Font.BOLD, (int) (fontSize*0.1)));

            }
        });


        addKeyListener(new GameKeyAdapter(this, newGameBoard));


        setBackground(Color.BLACK);
        buttonPanel.add(okButton);
        add(buttonPanel,BorderLayout.PAGE_END);
        setSize(200,145);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newGameBoard.dispose();
                SwingUtilities.invokeLater(()-> new Menu());
            }
        });



        centrum.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textField.setFocusable(false);
                requestFocusInWindow();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                textField.setFocusable(true);
                requestFocusInWindow();
            }
        });

        requestFocusInWindow();

    }
}

