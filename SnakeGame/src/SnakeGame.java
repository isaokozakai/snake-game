import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
	private static final int B_WIDTH = 500;
	private static final int B_HEIGHT = 500;
	private static final int DOT_SIZE = 10;
	private final int RAND_POS = 40;
	private final int DELAY = 100;

	private final int x[] = new int[B_WIDTH / DOT_SIZE];
	private final int y[] = new int[B_HEIGHT / DOT_SIZE];

	private int dots;
	private int apple_x;
	private int apple_y;
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = true;
	private boolean inGame = true;
	private Timer timer;
	public static Dimension dim;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setResizable(false);
		f.setSize(B_WIDTH, B_HEIGHT);
        f.setVisible(true);
		
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width / 2 - B_WIDTH / 2, dim.height / 2 - B_HEIGHT / 2);

		SnakeGame snakeGame = new SnakeGame();
		snakeGame.initGame();
		f.add(snakeGame);
		f.addKeyListener(snakeGame);
	}

	private void initGame() {
		dots = 4;

		for (int z = 0; z < dots; z++) {
			x[z] = 50 - z * DOT_SIZE;
			y[z] = 50;
		}

		locateApple();
		timer = new Timer(DELAY, this);
	}

	private void move() {
		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		if (leftDirection) {
			x[0] -= DOT_SIZE;
		}

		if (rightDirection) {
			x[0] += DOT_SIZE;
		}

		if (upDirection) {
			y[0] -= DOT_SIZE;
		}

		if (downDirection) {
			y[0] += DOT_SIZE;
		}
	}

	private void checkCollision() {

		for (int z = dots; z > 0; z--) {
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				inGame = false;
			}
		}

		if (y[0] >= B_HEIGHT) {
			inGame = false;
		}

		if (y[0] < 0) {
			inGame = false;
		}

		if (x[0] >= B_WIDTH) {
			inGame = false;
		}

		if (x[0] < 0) {
			inGame = false;
		}

		if (!inGame) {
			timer.stop();
		}
	}

	private void locateApple() {
		Random r = new Random();
		apple_x = r.nextInt(RAND_POS + 1) * DOT_SIZE;

		r = new Random();
		apple_y = r.nextInt(RAND_POS + 1) * DOT_SIZE;
	}

	private void checkApple() {
		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
		}
	}

	private void gameOver(Graphics g) {
		String msg = "Game Over";

		g.setColor(Color.white);
		g.drawString(msg, B_WIDTH / 2, B_HEIGHT / 2);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.black);
		for (int z = 0; z < dots; z++) {
			if (z == 0) {
				g.setColor(Color.yellow);
				g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
				g.setColor(Color.black);
				g.drawRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
			} else {
				g.setColor(Color.green);
				g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
				g.setColor(Color.black);
				g.drawRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
			}
		}
		g.setColor(Color.red);
		g.fillRect(apple_x, apple_y, DOT_SIZE, DOT_SIZE);

		if (!inGame) {
			gameOver(g);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
			leftDirection = true;
			upDirection = false;
			downDirection = false;
			timer.start();
		}

		if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
			rightDirection = true;
			upDirection = false;
			downDirection = false;
			timer.start();
		}

		if ((key == KeyEvent.VK_UP) && (!downDirection)) {
			upDirection = true;
			rightDirection = false;
			leftDirection = false;
			timer.start();
		}

		if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
			downDirection = true;
			rightDirection = false;
			leftDirection = false;
			timer.start();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (inGame) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}

}
