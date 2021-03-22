import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Gui extends JPanel implements ActionListener {
    private static final int cellsPerRow = 30;
    public static final int cellSize = App.windowWidth / cellsPerRow;
    public static boolean right = true;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean down = false;

    public static ArrayList<int[]> snake = new ArrayList<int[]>();
    public static int[] apple;

    public Gui() {
        initGame();
        initSnake();
    }

    private void initGame() {
        createApple();
        Timer timer = new Timer(100, this);
        timer.start();
    }

    public void createApple() {
        // Generate random co-ordinate location
        Random r = new Random();
        int x = r.nextInt(cellsPerRow);
        int y = r.nextInt(cellsPerRow);

        // Update apple static variable
        apple = new int[]{x, y};
    }

    private void initSnake() {
        snake.add(new int[]{2, 4});
        snake.add(new int[]{1, 4});
        snake.add(new int[]{0, 4});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw gridlines
        for (int y = 0; y < App.windowHeight; y = y + cellSize) {
            g.drawLine(0, y, App.windowWidth, y);
        }

        for (int x = 0; x < App.windowWidth; x = x + cellSize) {
            g.drawLine(x, 0, x, App.windowHeight);
        }

        drawSnake(g);
        drawApple(g);
    }

    private void drawSnake(Graphics g) {
        for (int[] part : snake) {
            g.drawOval(part[0] * cellSize, part[1] * cellSize, cellSize, cellSize);
        }
    }

    private void drawApple(Graphics g) {
        g.drawRect(apple[0] * cellSize, apple[1] * cellSize, cellSize, cellSize);
        g.setColor(Color.RED);
        g.fillRect(apple[0] * cellSize, apple[1] * cellSize, cellSize, cellSize);
    }

    private void moveSnake() {
        // Move body into part in fronts previous position
        for (int i = snake.size() - 1; i > 0; i--) {
            int[] nextBody = snake.get(i - 1);
            snake.set(i, nextBody);
        }

        // Move head to correct position
        int[] head = snake.get(0);
        int[] newHead;
        if (up == true) {
            newHead = new int[]{head[0], head[1] - 1};
            snake.set(0, newHead);
        } else if (down == true) {
            newHead = new int[]{head[0], head[1] + 1};
            snake.set(0, newHead);
        } else if (left == true) {
            newHead = new int[]{head[0] - 1, head[1]};
            snake.set(0, newHead);
        } else {
            newHead = new int[]{head[0] + 1, head[1]};
            snake.set(0, newHead);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveSnake();
        checks();
        repaint();
    }

    private void checks() {
        if(!checkSnakeAndApple()) {
            checkSnakeAndWall();
            checkSnakeAndSnake();
        }
    }


    private void checkSnakeAndWall() {
        int[] snakeHead = snake.get(0);

        for (int axis: snakeHead) {
            if ((axis < 0) || (axis > cellsPerRow)) {
                System.out.println("You crashed into the wall.");
                System.exit(0);
            }
        }
    }

    private boolean checkSnakeAndApple() {
        // Check if snake head and apple are equal
        int[] snakeHead = Gui.snake.get(0);
        int[] appleLocation = Gui.apple;

        // Return the result
        boolean areEqual = Arrays.equals(snakeHead, appleLocation);
        if (areEqual) {
            addSnakeBody();
            checkWin();
            createApple();
        }
        return areEqual;
    }

    public void addSnakeBody() {
        snake.add(apple);
    }

    private void checkWin() {
        if (snake.size() == cellsPerRow*cellsPerRow) {
            System.out.println("Congratulations!! You won the game of Snake!");
            System.exit(0);
        }
    }
    private void checkSnakeAndSnake() {
        int[] snakeHead = snake.get(0);
        for(int i=1; i<snake.size();i++) {
            if(Arrays.equals(snake.get(i), snakeHead)) {
                System.out.println("You crashed into yourself.");
                System.exit(0);
            }
        }
    }
}
