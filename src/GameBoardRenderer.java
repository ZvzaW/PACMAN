import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class GameBoardRenderer extends DefaultTableCellRenderer {

    protected static boolean isPacmanOpen;
    private Map<String, ImageIcon> imageMap = new HashMap<>();

    private String dirr;

    GameBoardRenderer(){
        imageMap.put("WD",new ImageIcon("resources/WD.png"));
        imageMap.put("WL",new ImageIcon("resources/WL.png"));
        imageMap.put("G1",new ImageIcon("resources/G1.png"));
        imageMap.put("G2",new ImageIcon("resources/G2.png"));
        imageMap.put("G3",new ImageIcon("resources/G3.png"));
        imageMap.put("G4",new ImageIcon("resources/G4.png"));
        imageMap.put("o",new ImageIcon("resources/o.png"));
        imageMap.put("1",new ImageIcon("resources/1.png"));
        imageMap.put("Speed",new ImageIcon("resources/Speed.png"));
        imageMap.put("x2",new ImageIcon("resources/x2.png"));
        imageMap.put("S",new ImageIcon("resources/Serce.png"));
        imageMap.put("SS",new ImageIcon("resources/SerceSmall.png"));
        imageMap.put("K",new ImageIcon("resources/K.png"));
        imageMap.put("Snail",new ImageIcon("resources/Snail.png"));
        isPacmanOpen = true;
        this.dirr = "RIGHT";


    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

            String text = (String) value;
            ImageIcon originalIcon;
            if (imageMap.containsKey(text)){
                 originalIcon = imageMap.get(text);
            }else if (value.equals("P")){
                if (isPacmanOpen){
                    originalIcon = switch(dirr) {
                        case "RIGHT" -> new ImageIcon("resources/Pacman1.png");
                        case "LEFT" -> new ImageIcon("resources/PacmanLeft.png");
                        case "UP" -> new ImageIcon("resources/PacmanUp.png");
                        default -> new ImageIcon("resources/PacmanDown.png");
                    };

                }else{
                    originalIcon = new ImageIcon("resources/Pacman2.png");
                }
            }else{
                originalIcon = null;
                setIcon(null);
            }


            if (value != "") {

                Image originalImage = originalIcon.getImage();
                int newWidth = table.getCellRect(row, column, false).width;
                int newHeight = table.getCellRect(row, column, false).height;
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                setIcon(scaledIcon);

                return this;
            }


        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    }


    public void setIsPacmanOpen(boolean isPacmanOpen, String dirr){
        this.isPacmanOpen = isPacmanOpen;
        this.dirr = dirr;
    }
}
