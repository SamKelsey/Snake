import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {
    public static final int windowHeight = 600;
    public static final int windowWidth = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame.pack();

        Gui gui = new Gui();
        frame.add(gui);
        frame.addKeyListener(new MyKeyListener());

        frame.setVisible(true);
    }

    public static class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!Gui.right)) {
                Gui.up = false;
                Gui.down = false;
                Gui.left = true;
                Gui.right = false;
            } else if ((key == KeyEvent.VK_RIGHT) && (!Gui.left)) {
                Gui.up = false;
                Gui.down = false;
                Gui.left = false;
                Gui.right = true;
            } else if ((key == KeyEvent.VK_UP) && (!Gui.down)) {
                Gui.up = true;
                Gui.down = false;
                Gui.left = false;
                Gui.right = false;
            } else if ((key == KeyEvent.VK_DOWN) && (!Gui.up)) {
                Gui.up = false;
                Gui.down = true;
                Gui.left = false;
                Gui.right = false;
            }
        }
    }
}
