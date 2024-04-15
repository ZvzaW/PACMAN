import javax.swing.*;

public class PacmanThread extends Thread{

    public static JTable gameBoard;
    public static int pacRow;
    public static int pacColumn;
    public static JTable hearts;
    private int speed = 225;
    private int multiplication = 1;

    public static GameBoardRenderer renderer;
    private JLabel points;

    private String[][] originalBoard;

    public static String[][] tmpBoard;
    private static GhostThread[] ghosts;
    public static boolean canGo;

    private static String direction;
    private int iScore = 0;
    private String sScore;
    public static int heart;
    private JLabel gameover;
    private boolean keyFlag = true;
    private JFrame newGameBoard;


    public PacmanThread(JTable gameBoard, GameBoardRenderer renderer, JLabel points, GhostThread[] ghosts,
                        JTable hearts, JLabel gameover, JFrame newGameBoard){

        this.newGameBoard = newGameBoard;
        PacmanThread.gameBoard = gameBoard;
        PacmanThread.renderer = renderer;
        this.points = points;
        this.originalBoard = new String[gameBoard.getRowCount()][gameBoard.getColumnCount()];
        PacmanThread.hearts = hearts;
        heart = 2;
        direction = "RIGHT";
        pacRow = 1;
        pacColumn = 1;
        canGo = true;


        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                originalBoard[i][j] = (String) gameBoard.getValueAt(i, j);
            }
        }

        tmpBoard = new String[gameBoard.getRowCount()][gameBoard.getColumnCount()];

        PacmanThread.ghosts = ghosts;
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                tmpBoard[i][j] = (String) gameBoard.getValueAt(i, j);
            }
        }

        this.gameover = gameover;

    }

    @Override
    public void run() {



        try {
            Thread.sleep(2770);



                while (checkLives() && keyFlag) {

                    if (canGo) {
                        movePacman();
                    }

                    if (calculateTotalPoints() == 0) {
                        resetGame();
                    }

                    Thread.sleep(speed);
                }

                if (heart == 0)
                        gameover.setVisible(true);


                    SwingUtilities.invokeLater(() -> new GameNameFrame(newGameBoard, sScore));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDirection(String direction){
        PacmanThread.direction = direction;
    }

    public int calculateTotalPoints(){
        int total = 0;
        for (int i = 0; i < gameBoard.getRowCount(); i++){
            for (int j = 0; j < gameBoard.getColumnCount(); j++){
                if(tmpBoard[i][j].equals("o") || tmpBoard[i][j].equals("1"))
                    ++total;
            }
        }
        return total;
    }


    public void resetGame() throws InterruptedException {
        canGo = false;
        for (GhostThread ghostThread : ghosts) {
            ghostThread.setCanGo(false);
        }
        Thread.sleep(2000);
        pacRow = 1;
        pacColumn = 1;
        direction = "RIGHT";
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                tmpBoard[i][j] = originalBoard[i][j];
            }
        }
        renderer.setIsPacmanOpen(true,"RIGHT");
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                gameBoard.setValueAt(originalBoard[i][j], i, j);
            }
        }


        gameBoard.setValueAt("P",pacRow,pacColumn);
        for (GhostThread ghost : ghosts) {
            ghost.setGhostRow(ghost.getDefaultghostRow());
            ghost.setGhostColumn(ghost.getDefaultghostColumn());
            gameBoard.setValueAt(ghost.getIdentyfikator(), ghost.getDefaultghostRow(), ghost.getDefaultghostColumn());
        }


        Thread.sleep(2000);


        canGo = true;
        for (GhostThread ghostThread : ghosts) {
            ghostThread.setCanGo(true);
        }


    }


     public static void resetPosition() throws InterruptedException {

       // --heart;
        canGo = false;

        for (GhostThread ghostThread : ghosts) {
            ghostThread.setCanGo(false);
        }
         Thread.sleep(2000);

        if (checkLives()) {
            pacColumn = 1;
            pacRow = 1;
            direction = "RIGHT";
            renderer.setIsPacmanOpen(true, "RIGHT");


            for (int i = 0; i < tmpBoard.length; i++) {
                for (int j = 0; j < tmpBoard[i].length; j++) {
                    gameBoard.setValueAt(tmpBoard[i][j], i, j);
                }
            }

            gameBoard.setValueAt("P", pacRow, pacColumn);
            for (GhostThread ghost : ghosts) {
                ghost.setGhostRow(ghost.getDefaultghostRow());
                ghost.setGhostColumn(ghost.getDefaultghostColumn());
                gameBoard.setValueAt(ghost.getIdentyfikator(), ghost.getDefaultghostRow(), ghost.getDefaultghostColumn());
            }


            Thread.sleep(1500);

            canGo = true;

            for (GhostThread ghostThread : ghosts) {
                ghostThread.setCanGo(true);
            }
        }


    }


     public void movePacman() {


        switch (direction) {
            case ("RIGHT") -> {
                if (!checkNextValue("WD",pacRow, pacColumn + 1)
                        && !checkNextValue("WL",pacRow, pacColumn + 1)) {

                    addPoints(pacRow, pacColumn + 1);
                    checkForUpgrades(pacRow, pacColumn + 1);

                    gameBoard.setValueAt("", pacRow, pacColumn);
                    tmpBoard[pacRow][pacColumn] = "";
                    ++pacColumn;
                    gameBoard.setValueAt("P", pacRow, pacColumn);
                    tmpBoard[pacRow][pacColumn] = "";


                    renderer.setIsPacmanOpen(!renderer.isPacmanOpen, "RIGHT");
                }
            }
            case ("LEFT") -> {
                if (!checkNextValue("WD",pacRow, pacColumn - 1)
                        && !checkNextValue("WL",pacRow, pacColumn - 1)) {

                    addPoints(pacRow, pacColumn - 1);
                    checkForUpgrades(pacRow, pacColumn - 1);

                    gameBoard.setValueAt("", pacRow, pacColumn);
                    tmpBoard[pacRow][pacColumn] = "";

                    --pacColumn;
                    gameBoard.setValueAt("P", pacRow, pacColumn);
                    tmpBoard[pacRow][pacColumn] = "";
                    renderer.setIsPacmanOpen(!renderer.isPacmanOpen, "LEFT");
                }
            }
            case ("UP") -> {
                if (!checkNextValue("WD",pacRow - 1, pacColumn)
                        && !checkNextValue("WL",pacRow - 1, pacColumn)) {

                    addPoints(pacRow - 1, pacColumn);
                    checkForUpgrades(pacRow - 1, pacColumn);

                    gameBoard.setValueAt("", pacRow, pacColumn);


                    --pacRow;
                    gameBoard.setValueAt("P", pacRow, pacColumn);
                    tmpBoard[pacRow][pacColumn] = "";

                    renderer.setIsPacmanOpen(!renderer.isPacmanOpen, "UP");
                }
            }
            case ("DOWN") -> {
                if (!checkNextValue("WD",pacRow + 1, pacColumn)
                        && !checkNextValue("WL",pacRow + 1, pacColumn)) {

                    addPoints(pacRow + 1, pacColumn);
                    checkForUpgrades(pacRow + 1, pacColumn);

                    gameBoard.setValueAt("", pacRow, pacColumn);


                    ++pacRow;
                    gameBoard.setValueAt("P", pacRow, pacColumn);
                    tmpBoard[pacRow][pacColumn] = "";

                    renderer.setIsPacmanOpen(!renderer.isPacmanOpen, "DOWN");
                }
            }

        }

    }



    public static String getTmpBoardValueAt(int row, int column){
        return tmpBoard[row][column];
    }


    public boolean checkNextValue(String value, int row, int column){
        return gameBoard.getValueAt(row,column).equals(value);
    }


    public void addPoints(int row, int column){
        if ("o".equals(gameBoard.getValueAt(row, column))) {
            iScore += 10*multiplication;
            sScore = "" + iScore;
            points.setText(sScore);
        } else if ("1".equals(gameBoard.getValueAt(row, column))) {
            iScore += 50*multiplication;
            sScore = "" + iScore;
            points.setText(sScore);
        }
    }

     public static boolean checkLives(){
        boolean isAnyHeart = false;
        for (int i = 0; i < 3; i++) {
            if (hearts.getValueAt(0, i).equals("S")) {
                isAnyHeart = true;
                break;
            }
        }

        return isAnyHeart;
    }


    public void checkForUpgrades(int row, int column){
        if ("SS".equals(gameBoard.getValueAt(row, column))) {

            if (heart!=2) {
                ++heart;
                for (int i = 0; i < 3; i++)
                    if (hearts.getValueAt(0, i).equals("")) {
                        hearts.setValueAt("S", 0, i);
                        break;
                    }
            }
        } else if ("Speed".equals(gameBoard.getValueAt(row, column))) {
            this.speed = (int) (speed/1.25);

            new Thread(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.speed = 225;
            }).start();

        } else if ("x2".equals(gameBoard.getValueAt(row, column))) {

            this.multiplication = multiplication*2;

            new Thread(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
               this.multiplication = 1;
            }).start();
        }else if ("K".equals(gameBoard.getValueAt(row, column))) {



            for (int i = 0; i < ghosts.length; i++){
                ghosts[i].setCanGo(false);
            }

            new Thread(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (calculateTotalPoints()!=0) {
                    for (int i = 0; i < ghosts.length; i++) {
                        ghosts[i].setCanGo(true);
                    }
                }

            }).start();

        } else if ("Snail".equals(gameBoard.getValueAt(row, column))) {
            for (int i = 0; i < ghosts.length; i++){
                ghosts[i].setSpeed(ghosts[i].getSpeed()*2);
            }


            new Thread(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                    for (int i = 0; i < ghosts.length; i++) {
                        ghosts[i].setSpeed(230);
                    }


            }).start();
        }
    }


    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }




}





