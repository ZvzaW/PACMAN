import javax.swing.*;
import java.awt.*;

    class ImagePanel extends JPanel {

        private ImageIcon imageIcon;


        public ImagePanel(String imgFileName)
        {
            loadImage(imgFileName);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imageIcon != null)
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);

        }

        private void loadImage(String imgFileName) {
            imageIcon = new ImageIcon(imgFileName);

            int width = imageIcon.getIconWidth();
            int height = imageIcon.getIconHeight();
            if (width != -1 && height != -1) {
                setPreferredSize(new Dimension(width, height));
            } else
                setPreferredSize(new Dimension(250, 260));
        }

    }

