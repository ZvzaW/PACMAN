import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Ranking extends JFrame implements Serializable {

    private static ArrayList<Score> scores;
    private boolean  isCtrlPressed = false;
    private boolean isShiftPressed = false;

    public Ranking() throws IOException, ClassNotFoundException {



        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.black);

        JLabel yourGames = new JLabel("YOUR GAMES");
        yourGames.setFont(new Font("Silom",Font.BOLD,16));
        yourGames.setForeground(Color.yellow);
        yourGames.setOpaque(false);

        topPanel.add(yourGames);

        add(topPanel, BorderLayout.NORTH);

        JList jList = new JList<>();
        jList.setCellRenderer(new MyListCellRenderer());

        readFromFile();
        scores.sort(new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Score score : scores){
            String scoreValue = score.toString();
            listModel.addElement(scoreValue);
        }

        jList.setModel(listModel);
        jList.setSelectionBackground(Color.BLUE);
        jList.setOpaque(false);


        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.black);
        JScrollPane jScrollPane = new JScrollPane(jList);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setBackground(Color.black);
        jScrollPane.setBorder(null);



        panel.add(jScrollPane, BorderLayout.CENTER);


        add(panel,BorderLayout.CENTER);


        setBackground(Color.BLACK);

        setSize(300,400);
        setTitle("High Score");

        setVisible(true);
        setLocationRelativeTo(null);
        addKeyListener(new GameKeyAdapter(this));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(()-> new Menu());

            }
        });

        topPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jList.setFocusable(false);
                requestFocusInWindow();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                jList.setFocusable(true);
                requestFocusInWindow();
            }
        });
        requestFocusInWindow();

    }

    public static void writeToFile(Score score) throws IOException {
        FileOutputStream fos = new FileOutputStream("src/scores.ser", true);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(score);
        oos.close();
        fos.close();
    }

    public static void readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("src/scores.ser");
        scores = new ArrayList<Score>();


        Score score;
        while (fis.available() > 0) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            score = (Score) ois.readObject();
            scores.add(score);

        }

        fis.close();



    }

}
