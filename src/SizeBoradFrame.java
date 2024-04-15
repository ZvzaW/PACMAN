import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SizeBoradFrame extends JFrame {


    public SizeBoradFrame(){


    //pierwszy panel: polecenie

        ImagePanel background = new ImagePanel("resources/sizeBoard.png");
        background.setLayout(new GridLayout(0,1));



        JPanel panel1 = new JPanel();
        JLabel jLabel = new JLabel("Choose board size: ");




        panel1.setOpaque(false);
        jLabel.setForeground(Color.yellow);

        panel1.setLayout(new FlowLayout());
        panel1.add(jLabel);
        background.add(panel1);

    //drugi panel: wiersze

        JPanel panel2 = new JPanel();
        JLabel wiersz = new JLabel("ROWS");
        JTextField field1 = new JTextField(3);
        field1.setSize(new Dimension(120,10));

        panel2.setOpaque(false);
        wiersz.setForeground(Color.yellow);



        panel2.add(wiersz);
        panel2.add(field1);
        background.add(panel2);


    //trzeci panel: kolumny
        JPanel panel3 = new JPanel();
        JLabel kolumna = new JLabel("COLUMNS");
        JTextField field2 = new JTextField(3);
        field2.setSize(new Dimension(120,10));


        panel3.setOpaque(false);
        kolumna.setForeground(Color.yellow);


        panel3.add(kolumna);
        panel3.add(field2);
        background.add(panel3);


    //czwarty panel: buttons

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        panel4.setOpaque(false);

        JButton okej = new JButton("Ok");

        okej.setForeground(Color.white);
        okej.setBorderPainted(false);
        okej.setOpaque(false);


        JButton cancel = new JButton("Cancel");
        cancel.setForeground(Color.white);

        cancel.setBorderPainted(false);
        cancel.setOpaque(false);

        panel4.add(okej);
        panel4.add(cancel);

        background.add(panel4);

    //-----------


        add(background);


        setForeground(Color.white);
        setTitle("New Board");

        setSize(250,260);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(()-> new Menu());
            }
        });


        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(()-> new Menu());
            }
        });

        okej.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numRows = Integer.parseInt(field1.getText());
                    int numCols = Integer.parseInt(field2.getText());
                    if (numRows < 11 || numRows > 25 || numCols < 11 || numCols > 25) {
                        JOptionPane.showMessageDialog(null,
                                "Wrong board size. Pick number from 11 to 25." ,
                                "Wrong Argument",JOptionPane.PLAIN_MESSAGE,
                                new ImageIcon("resources/wrongNumber.png"));
                    } else {
                        dispose();
                        SwingUtilities.invokeLater(()->new NewGameBoard(numRows,numCols));


                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Wrong number format. Pick integer number.",
                            "Wrong Format",JOptionPane.PLAIN_MESSAGE,
                            new ImageIcon("resources/wrongArgument.png"));
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                int fontSize = (e.getComponent().getHeight());
                jLabel.setFont(new Font("Silom",Font.BOLD, (int) (fontSize*0.065)));
                wiersz.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.06)));
                kolumna.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.06)));
                okej.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.05)));
                cancel.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.05)));
            }

        });


        addKeyListener(new GameKeyAdapter(this));

        requestFocusInWindow();

    }


}
