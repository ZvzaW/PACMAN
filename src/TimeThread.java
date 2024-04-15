import javax.swing.*;

public class TimeThread extends Thread{

    JLabel time;
    private boolean keyFlag = true;

    public TimeThread(JLabel time) {
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2770);
            int seconds = 0;
            String sTime;
            while(PacmanThread.checkLives()&&keyFlag){
                seconds+=1;
                sTime = "" + seconds + "s";
                time.setText(sTime);
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }
}
