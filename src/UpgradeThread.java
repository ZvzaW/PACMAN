import javax.swing.*;
import java.util.Random;

public class UpgradeThread extends Thread {

    private JTable gameBoard;
    private GhostThread ghost;
    private String id;

    public UpgradeThread(JTable gameBoard, GhostThread ghost, String id) {
        this.gameBoard = gameBoard;
        this.ghost = ghost;
        this.id = id;
    }

    Random random = new Random();

    @Override
    public void run() {

        try {
            Thread.sleep(2770);
        while(PacmanThread.checkLives() && ghost.isKeyFlag()){

            int randomNum = random.nextInt(101);

                Thread.sleep(5000);


                if (PacmanThread.checkLives() && ghost.isKeyFlag() && ghost.isCanGo()) {
                    if (randomNum <= 25) {
                        generateUpgrade();
                    }
                }

        }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public void generateUpgrade(){
        int upgrade = random.nextInt(2);

        switch (id){
            // dodatkowe zycie
            case "G2" ->{
                PacmanThread.tmpBoard[ghost.getGhostRow()][ghost.getGhostColumn()] = "SS";
                int row = ghost.getGhostRow();
                int column = ghost.getGhostColumn();
                new Thread(()->{
                    try {
                        Thread.sleep(ghost.getSpeed());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    gameBoard.setValueAt("SS",row,column);
                }).start();

            }
            //zwieksza predkosc pacmana
            case "G4" ->{
                PacmanThread.tmpBoard[ghost.getGhostRow()][ghost.getGhostColumn()] = "Speed";
                int row = ghost.getGhostRow();
                int column = ghost.getGhostColumn();
                new Thread(()->{
                    try {
                        Thread.sleep(ghost.getSpeed());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    gameBoard.setValueAt("Speed",row,column);
                }).start();
            }
            //punkty nabjaja sie dwa razy wiecej
            case "G1" ->{
                PacmanThread.tmpBoard[ghost.getGhostRow()][ghost.getGhostColumn()] = "x2";
                int row = ghost.getGhostRow();
                int column = ghost.getGhostColumn();
                new Thread(()->{
                    try {
                        Thread.sleep(ghost.getSpeed());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    gameBoard.setValueAt("x2",row,column);
                }).start();
            }
            //zatrzymanie duszkow lub spowolnienie
            case "G3" ->{
                if (upgrade == 0) {
                    PacmanThread.tmpBoard[ghost.getGhostRow()][ghost.getGhostColumn()] = "K";
                    int row = ghost.getGhostRow();
                    int column = ghost.getGhostColumn();
                    new Thread(()->{
                        try {
                            Thread.sleep(ghost.getSpeed());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        gameBoard.setValueAt("K",row,column);
                    }).start();
                }else{
                    PacmanThread.tmpBoard[ghost.getGhostRow()][ghost.getGhostColumn()] = "Snail";
                    int row = ghost.getGhostRow();
                    int column = ghost.getGhostColumn();
                    new Thread(()->{
                        try {
                            Thread.sleep(ghost.getSpeed());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        gameBoard.setValueAt("Snail",row,column);
                    }).start();
                }
            }
        }
    }
}
