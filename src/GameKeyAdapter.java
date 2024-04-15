import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyAdapter extends KeyAdapter {

    private PacmanThread pacmanThread;
    private GhostThread[] ghosts;
    private TimeThread timeThread;
    private boolean isShiftPressed = false;
    private boolean isCtrlPressed = false;
    private JFrame frame;
    private JFrame newGameBoard;

    public GameKeyAdapter(PacmanThread pacmanThread, GhostThread[] ghosts, TimeThread timeThread, JFrame frame) {

        this.pacmanThread = pacmanThread;
        this.ghosts = ghosts;
        this.timeThread = timeThread;
        this.frame = frame;

    }

    public GameKeyAdapter(JFrame frame, JFrame newGameBoard ) {
        this.frame = frame;
        this.newGameBoard = newGameBoard;
    }

    public GameKeyAdapter(JFrame frame) {
        this.frame = frame;
    }




    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP -> {
                if (pacmanThread != null)
                    pacmanThread.setDirection("UP");}

            case KeyEvent.VK_DOWN -> {
                if (pacmanThread != null)
                    pacmanThread.setDirection("DOWN");}

            case KeyEvent.VK_LEFT -> {
                if (pacmanThread != null)
                    pacmanThread.setDirection("LEFT");}

            case KeyEvent.VK_RIGHT -> {
                if (pacmanThread != null)
                    pacmanThread.setDirection("RIGHT");}

            case KeyEvent.VK_CONTROL -> isCtrlPressed = true;

            case KeyEvent.VK_SHIFT -> isShiftPressed = true;

            case KeyEvent.VK_Q -> {
                if(isCtrlPressed && isShiftPressed) {
                    if (newGameBoard != null)
                        newGameBoard.dispose();

                    frame.dispose();

                    if(!(frame instanceof NewGameBoard))
                    SwingUtilities.invokeLater(()-> new Menu());


                    if(pacmanThread !=null && ghosts != null && timeThread != null) {
                        for (int i = 0; i < ghosts.length; i++)
                            ghosts[i].setKeyFlag(false);
                        pacmanThread.setKeyFlag(false);
                        timeThread.setKeyFlag(false);
                    }


                }
            }



        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            isCtrlPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = false;
        }
    }
}
