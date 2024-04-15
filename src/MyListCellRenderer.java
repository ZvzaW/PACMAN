import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MyListCellRenderer extends JLabel implements ListCellRenderer {



        public MyListCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {

            setText((String) value);

            setFont(new Font("Silom",Font.PLAIN,15));
            setForeground(Color.WHITE);
            setBackground(Color.black);
            setBorder(new EmptyBorder(5,10,5,10));


            if (isSelected)
                setBackground(Color.gray);
            else setBackground(Color.black);



            return this;
        }

}
