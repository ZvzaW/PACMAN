import javax.swing.*;
import java.util.Random;

public class GhostThread extends Thread{

    private  boolean canGo;
    private String id;
    private int ghostRow ;
    private int ghostColumn;
    private int defaultghostRow ;
    private int defaultghostColumn;
    private JTable gameBoard;
    private boolean keyFlag = true;
    private static boolean isLosingLife;

    private int speed = 230;






    public GhostThread(String id, JTable gameBoard) {
        this.id = id;
        this.gameBoard = gameBoard;
        findAndSetPosition(gameBoard,id);
        canGo = true;
        isLosingLife = false;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2770);

            UpgradeThread upgrade = new UpgradeThread(gameBoard,this, id);
            upgrade.start();

            while (PacmanThread.checkLives() && keyFlag) {

                checkForPacman();


                if (canGo) {
                    moveGhost();
                }

                checkForPacman();

                Thread.sleep(speed);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


      synchronized public void moveGhost() throws InterruptedException {

         int direction = new Random().nextInt(4);


         switch (direction) {
             // w prawo
             case 0 -> {
                 if (!"WD".equals(gameBoard.getValueAt(ghostRow, ghostColumn + 1))
                         && !"WL".equals(gameBoard.getValueAt(ghostRow, ghostColumn + 1))
                         && checkForGhosts(ghostRow, ghostColumn + 1)) {

                     String cellValue = PacmanThread.getTmpBoardValueAt(ghostRow,ghostColumn);

                     gameBoard.setValueAt(cellValue, ghostRow, ghostColumn);
                     if (cellValue.startsWith("G"))
                         gameBoard.setValueAt("",ghostRow,ghostColumn);
                     ++ghostColumn;
                     gameBoard.setValueAt(id, ghostRow, ghostColumn);

                 }
             }
             //w lewo
             case 1 -> {
                 if (!"WD".equals(gameBoard.getValueAt(ghostRow, ghostColumn - 1))
                         && !"WL".equals(gameBoard.getValueAt(ghostRow, ghostColumn - 1))
                         && checkForGhosts(ghostRow, ghostColumn - 1)) {

                     String cellValue = PacmanThread.getTmpBoardValueAt(ghostRow,ghostColumn);

                     gameBoard.setValueAt(cellValue, ghostRow, ghostColumn);
                     if (cellValue.startsWith("G"))
                         gameBoard.setValueAt("",ghostRow,ghostColumn);
                     --ghostColumn;
                     gameBoard.setValueAt(id, ghostRow, ghostColumn);

                 }
             }
             // w gore
             case 2 -> {
                 if (!"WD".equals(gameBoard.getValueAt(ghostRow - 1, ghostColumn))
                         && !"WL".equals(gameBoard.getValueAt(ghostRow - 1, ghostColumn))
                         && checkForGhosts(ghostRow - 1, ghostColumn)) {

                     String cellValue = PacmanThread.getTmpBoardValueAt(ghostRow,ghostColumn);

                     gameBoard.setValueAt(cellValue, ghostRow, ghostColumn);
                     if (cellValue.startsWith("G"))
                         gameBoard.setValueAt("",ghostRow,ghostColumn);
                     --ghostRow;
                     gameBoard.setValueAt(id, ghostRow, ghostColumn);

                 }
             }

             //w dol
             case 3 -> {
                 if (!"WD".equals(gameBoard.getValueAt(ghostRow + 1, ghostColumn))
                         && !"WL".equals(gameBoard.getValueAt(ghostRow + 1, ghostColumn))
                         && checkForGhosts(ghostRow + 1, ghostColumn)) {

                     String cellValue = PacmanThread.getTmpBoardValueAt(ghostRow,ghostColumn);

                     gameBoard.setValueAt(cellValue, ghostRow, ghostColumn);
                     if (cellValue.startsWith("G"))
                         gameBoard.setValueAt("",ghostRow,ghostColumn);
                     ++ghostRow;
                     gameBoard.setValueAt(id, ghostRow, ghostColumn);

                 }
             }
         }
    }


    public void findAndSetPosition(JTable gameBoard, String id){
        for (int i = 0; i < gameBoard.getRowCount(); i++){
            for (int j = 0; j < gameBoard.getColumnCount(); j++){
                if (gameBoard.getValueAt(i,j).equals(id)){
                    ghostRow = i;
                    ghostColumn = j;
                    defaultghostRow = i;
                    defaultghostColumn = j;
                }
            }
        }
    }

     public boolean checkForGhosts(int row, int column){
        return !"G1".equals(gameBoard.getValueAt(row, column))
                && !"G2".equals(gameBoard.getValueAt(row,column))
                && !"G3".equals(gameBoard.getValueAt(row,column))
                && !"G4".equals(gameBoard.getValueAt(row,column));
    }



    public void checkForPacman() throws InterruptedException {
        if (PacmanThread.pacRow == ghostRow && PacmanThread.pacColumn == ghostColumn) {

            gameBoard.setValueAt(id,PacmanThread.pacRow,PacmanThread.pacColumn);
            PacmanThread.hearts.setValueAt("",0,PacmanThread.heart);
            PacmanThread.resetPosition();
            if (PacmanThread.heart!=0)
                --PacmanThread.heart;
        }
    }


    public int getGhostRow() {
        return ghostRow;
    }

    public int getGhostColumn() {
        return ghostColumn;
    }


    public int getDefaultghostRow() {
        return defaultghostRow;
    }

    public int getDefaultghostColumn() {
        return defaultghostColumn;
    }

    public void setGhostRow(int ghostRow) {
        this.ghostRow = ghostRow;
    }

    public void setGhostColumn(int ghostColumn) {
        this.ghostColumn = ghostColumn;
    }


    public String getIdentyfikator() {
        return id;
    }


    public void setCanGo(boolean canGo) {
        this.canGo = canGo;
    }

    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }

    public boolean isKeyFlag() {
        return keyFlag;
    }

    public boolean isCanGo() {
        return canGo;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
