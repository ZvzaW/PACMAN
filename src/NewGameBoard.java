import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class NewGameBoard extends JFrame {

    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel lives;
    private JTable gameBoardTable;


    public NewGameBoard(int wiersze, int kolumny){


        gameBoardTable = new JTable(wiersze, kolumny);
        gameBoardTable.setModel(new PacmanTableModel(wiersze,kolumny));


        JPanel plansza = new JPanel();
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JPanel scorePanel = new JPanel(new FlowLayout());
        JPanel timePanel = new JPanel(new FlowLayout());



        topPanel.add(scorePanel, BorderLayout.CENTER);
        topPanel.add(timePanel,BorderLayout.EAST);
        topPanel.setBackground(Color.black);

        bottomPanel.setBackground(Color.black);

        timePanel.setOpaque(false);

        scorePanel.setOpaque(false);


        setLayout(new BorderLayout());


    // ETYKIETA SCORE

        scoreLabel = new JLabel("Score: ");
        JLabel points = new JLabel("0");


        scorePanel.add(scoreLabel);
        scorePanel.add(points);

        topPanel.add(scorePanel, BorderLayout.WEST);


        scoreLabel.setForeground(Color.white);
        points.setForeground(Color.white);

    //ETYKIETA GAMEOVER
        JLabel gameOver = new JLabel("GAME OVER!");
        JPanel over = new JPanel(new FlowLayout());
        over.setOpaque(false);
        gameOver.setBackground(Color.black);
        gameOver.setForeground(Color.yellow);
        over.add(gameOver);
        topPanel.add(over,BorderLayout.CENTER);
        gameOver.setVisible(false);


    // ETYKIETA TIME

        timeLabel = new JLabel("Time: ");
        JLabel time = new JLabel("0");


         timePanel.add(timeLabel);
         timePanel.add(time);
         topPanel.add(timePanel, BorderLayout.EAST);

         timeLabel.setForeground(Color.white);
         time.setForeground(Color.white);


        add(topPanel, BorderLayout.NORTH);


    //ETYKIETA LIVES


        bottomPanel.setLayout(new FlowLayout());

        lives = new JLabel("Lives: ");
        lives.setForeground(Color.white);

    //TABELA HEARTS

        JTable hearts = new JTable(new PacmanTableModel(1,3));
        hearts.setBackground(Color.black);
        hearts.setShowGrid(false);


        for (int i = 0; i < hearts.getColumnCount(); i++){
            hearts.getColumnModel().getColumn(i).setPreferredWidth(10);
            hearts.setValueAt("S",0,i);
            hearts.getColumnModel().getColumn(i).setCellRenderer(new GameBoardRenderer());
        }


        bottomPanel.add(lives);
        bottomPanel.add(hearts);
        add(bottomPanel, BorderLayout.SOUTH);



    //GENEROWANIE PLANSZY

        generateMaze(wiersze,kolumny,gameBoardTable);





        plansza.add(gameBoardTable);
        plansza.setBackground(Color.black);
        add(plansza,BorderLayout.CENTER);



        gameBoardTable.setBackground(Color.BLACK);
        gameBoardTable.setForeground(Color.YELLOW);
        gameBoardTable.setShowGrid(false);
        gameBoardTable.setFocusable(false);
        gameBoardTable.setRowSelectionAllowed(false);
        gameBoardTable.setColumnSelectionAllowed(false);
        gameBoardTable.setTableHeader(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Pac-man");


        setSize(kolumny*47,wiersze*48);
        setLocationRelativeTo(null);



        GameBoardRenderer renderer = new GameBoardRenderer();
        GhostThread ghost1 = new GhostThread("G1", gameBoardTable);
        GhostThread ghost2 = new GhostThread("G2",gameBoardTable);
        GhostThread ghost3 = new GhostThread("G3",gameBoardTable);
        GhostThread ghost4 = new GhostThread("G4",gameBoardTable);
        GhostThread[] ghosts = {ghost1,ghost2,ghost3,ghost4};

        PacmanThread pacmanThread = new PacmanThread(gameBoardTable, renderer, points, ghosts, hearts, gameOver,
                this);

        TimeThread timeThread = new TimeThread(time);


        for (int i = 0; i < kolumny; i++){
            gameBoardTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                pacmanThread.start();
                ghost1.start();
                ghost2.start();
                ghost3.start();
                ghost4.start();


                timeThread.start();
            }
        });



        addKeyListener(new GameKeyAdapter(pacmanThread, ghosts, timeThread, this));

    //COMPONENT_LISTENER

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                int fontSize = e.getComponent().getHeight();
                scoreLabel.setFont(new Font("Silom",Font.BOLD, (int) (fontSize*0.027)));
                points.setFont(new Font("Silom",Font.BOLD, (int) (fontSize*0.027)));
                timeLabel.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.025)));
                time.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.025)));
                lives.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.023)));
                gameOver.setFont(new Font("Silom",Font.PLAIN, (int) (fontSize*0.03)));


                try {
                    int newSize = Math.min(plansza.getWidth() / kolumny, (plansza.getHeight())/ wiersze);
                    gameBoardTable.setRowHeight(newSize);



                    for (int i = 0; i < kolumny; i++) {
                        gameBoardTable.getColumnModel().getColumn(i).setPreferredWidth(newSize);
                    }
                }catch (IllegalArgumentException exception){};
            }
        });

        requestFocusInWindow();
    }




    public static void generateMaze(int wiersze, int kolumny,JTable gameBoardTable){

        for (int i = 1; i<wiersze; i++){
            for (int j = 0; j < kolumny; j++){
                gameBoardTable.setValueAt("o",i,j);
            }
        }

        if (kolumny%2==0) {
            for (int i = 0; i < wiersze; i += 4) {
                for (int j = 1; j < kolumny; j += 5) {
                    gameBoardTable.setValueAt("1", i, j);
                }
            }
        }else{
            for (int i = 0; i < wiersze; i += 4) {
                for (int j = 1; j < kolumny; j += 4) {
                    gameBoardTable.setValueAt("1", i, j);
                }
            }
        }



        for (int i = 0; i<wiersze; i++){
            for (int j = 0; j < kolumny; j++){
                if (i == 0 || i == wiersze-1  || j == 0 || j == kolumny-1) {
                    gameBoardTable.setValueAt("WD",i,j);
                }
            }
        }

//USTAWIENIE GRACZA
        gameBoardTable.setValueAt("P",1,1);


//USTAWIENIE DUCHOW


        for (int i = 0; i < 4; i++){
            String name = "G"+(i+1);
            gameBoardTable.setValueAt(name,wiersze/2,(kolumny-3)/2+i);
        }





    //OD LEWEJ GORA

            for (int i = 2; i < wiersze / 2; i += 2) {
                for (int j = 2; j < kolumny / 2; j += 2) {

                    gameBoardTable.setValueAt("WL", i, j);
                }
            }

            for (int i = 2; i < wiersze / 2; i++) {
                for (int j = 2; j < kolumny / 2; j += 4) {
                    if (i % 3 == 0)
                        gameBoardTable.setValueAt("WL", i, j);
                }
            }

            for (int i = 2; i < wiersze / 2; i += 4) {
                for (int j = 3; j < kolumny / 2; j += 4) {

                    gameBoardTable.setValueAt("WL", i, j);
                }
            }


    //OD LEWEJ DOL

        for (int i = wiersze - 3; i > wiersze / 2; i -= 2) {
            for (int j = 2; j < kolumny / 2; j += 2) {

                gameBoardTable.setValueAt("WL", i, j);
            }
        }

        for (int i = wiersze - 4; i > wiersze / 2; i -= 3) {
            for (int j = 2; j < kolumny / 2; j += 4) {

                gameBoardTable.setValueAt("WL", i, j);
            }
        }


        for (int i = wiersze - 3; i > wiersze / 2; i -= 4) {
            for (int j = 3; j < kolumny / 2; j += 4) {

                gameBoardTable.setValueAt("WL", i, j);
            }
        }





        //dla nieparzystych kolumn

            if(kolumny%2 != 0) {

                //OD PRAWEJ GORA
                for (int i = 2; i < wiersze / 2; i += 2) {
                    for (int j = kolumny - 3; j > kolumny / 2; j -= 2) {

                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }

                for (int i = 2; i < wiersze / 2; i++) {
                    for (int j = kolumny - 3; j > kolumny / 2; j -= 4) {
                        if (i % 3 == 0)
                            gameBoardTable.setValueAt("WL", i, j);
                    }
                }


                for (int i = 2; i < wiersze / 2; i += 4) {
                    for (int j = kolumny - 4; j > kolumny / 2; j -= 4) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }


                //OD PRAWEJ DOL
                for (int i = wiersze - 3; i > wiersze / 2; i -= 2) {
                    for (int j = kolumny - 3; j > kolumny / 2; j -= 2) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }

                for (int i = wiersze - 4; i > wiersze / 2; i -= 3) {
                    for (int j = kolumny - 3; j > kolumny / 2; j -= 4) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }

                for (int i = wiersze - 3; i > wiersze / 2; i -= 4) {
                    for (int j = kolumny - 4; j > kolumny / 2; j -= 4) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }



            }else {

            //dla parzystych kolumn

                //OD PRAWEJ GORA
                for (int i = 2; i < wiersze / 2; i += 2) {
                    for (int j = kolumny - 3; j > (kolumny - 1) / 2; j -= 2) {

                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }

                for (int i = 2; i < wiersze / 2; i++) {
                    for (int j = kolumny - 3; j > (kolumny - 1) / 2; j -= 4) {
                        if (i % 3 == 0)
                            gameBoardTable.setValueAt("WL", i, j);
                    }
                }


                for (int i = 2; i < wiersze / 2; i += 4) {
                    for (int j = kolumny - 4; j > (kolumny - 1) / 2; j -= 4) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }


                //OD PRAWEJ DOL
                for (int i = wiersze - 3; i > wiersze / 2; i -= 2) {
                    for (int j = kolumny - 3; j > (kolumny - 1) / 2; j -= 2) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }

                for (int i = wiersze - 4; i > wiersze / 2; i -= 3) {
                    for (int j = kolumny - 3; j > (kolumny - 1) / 2; j -= 4) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }

                for (int i = wiersze - 3; i > wiersze / 2; i -= 4) {
                    for (int j = kolumny - 4; j > (kolumny - 1) / 2; j -= 4) {
                        gameBoardTable.setValueAt("WL", i, j);
                    }
                }



            }
    }




    //koniec klasy
}


