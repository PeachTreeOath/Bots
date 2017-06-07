import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Opener {
	private boolean isEvent = true;
	private final boolean isLaptop = false;
	private boolean longRun = false;
	private static final double cutOffDelay = 1000 * 60 * 60;
	private static final double cutOffHour = 3.5;
	private static final double cutOffOverride = 3.5;
	private static final double earlyGPressTime = 10000;
	// private double scale = 1.114;
	private double scale = 1;
	private final int startDelay = 3000;
	private final int clickDelay = 500;
	private final int phase3ClickDelay = 2000;
	private final int stopDistance = 50;
	private static final int longRunPressDelay = 1000 * 60 * 10;
	private static final int stormRiderPressDelay = 1000 * 60 * 10;
	private static long nextStormRiderTime = 0;
	private static Robot robot;
	private Point originPoint;
	private Point crusaderA;
	private Point crusaderB;
	private Point crusaderC;
	private Point crusaderD;
	private Point crusaderE;
	private Point crusaderF;
	private Point lArrow;
	private Point buffA1, buffA2, buffA3, buffA4, buffA5, buffA6, buffA7;
	private Point buffB1, buffB2, buffB3, buffB4, buffB5, buffB6, buffB7;
	private Point buyAllButton;
	private Point unlockAllButton;
	private Point goPoint;

	long startTime;
	double cutOffTime;
	boolean resetGame;
	int resetNum = 1;
	boolean earlyGPressed;

	// System.out.println(Math.abs(currentPoint.x - originPoint.x)
	// + " : " + Math.abs(currentPoint.y - originPoint.y));

	public static void main(String[] args) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		new Opener();
	}

	public Opener() {
		do {
			System.out.println("Run #" + resetNum);
			runBot();
		} while (resetGame);
	}

	private void runBot() {
		try {
			Thread.sleep(startDelay);

			int step = 0;

			originPoint = MouseInfo.getPointerInfo().getLocation();
			generatePoints();

			Point lastPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();

			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();

				if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
					System.exit(0);
				}

				switch (step) {
				case 0:
					robot.mouseMove(originPoint.x, originPoint.y);
					Thread.sleep(50);
					pressMouse(1000);
					break;
				case 1:
					robot.mouseMove(buyAllButton.x, buyAllButton.y);
					Thread.sleep(50);
					pressMouse(45000);
					break;
				case 2:
					robot.mouseMove(unlockAllButton.x, unlockAllButton.y);
					Thread.sleep(50);
					pressMouse(1000);
					break;
				}

				step++;
				if (step == 3)
					step = 0;
				lastPoint = MouseInfo.getPointerInfo().getLocation();
				Thread.sleep(clickDelay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bot stopped");
	}

	private void generatePoints() {
		buyAllButton = new Point(originPoint.x - (int) (38 * scale), originPoint.y - (int) (265 * scale));
		unlockAllButton = new Point(originPoint.x + (int) (405 * scale), originPoint.y - (int) (575 * scale));
	}

	private void pressMouse(int delay) {
		try {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(10);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
