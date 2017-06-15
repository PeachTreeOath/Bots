import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SpeedRunner {
	private boolean isEvent = true;
	private final boolean isLaptop = false;
	private boolean longRun = false;
	private static final double cutOffDelay = 1000 * 60 * 60;
	private static final double cutOffHour = 0.5;
	private static final double cutOffOverride = 0.5;
	private static final double earlyGPressTime = 1000 * 60 * 9;
	private static final long WPressTime = 1000 * 60 * 13;
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
		new SpeedRunner();
	}

	public SpeedRunner() {
		do {
			System.out.println("Run #" + resetNum);
			runBot();
		} while (resetGame);
	}

	private void runBot() {
		try {
			resetGame = false;
			startTime = System.currentTimeMillis();

			if (resetNum == 1 && !longRun) {
				cutOffTime = System.currentTimeMillis() + cutOffDelay * cutOffOverride;
			} else {
				cutOffTime = System.currentTimeMillis() + cutOffDelay * cutOffHour;
			}
			Thread.sleep(startDelay);
			System.out.println("Full Bot started");

			int step = 0;
			if (resetNum == 1) {
				originPoint = MouseInfo.getPointerInfo().getLocation();
			}
			generatePoints();

			Point lastPoint = originPoint;
			robot.mouseMove(originPoint.x, originPoint.y);
			Toolkit.getDefaultToolkit().beep();

			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);

				if (!earlyGPressed && System.currentTimeMillis() - startTime > earlyGPressTime) {
					earlyGPressed = true;
					robot.keyPress(KeyEvent.VK_G);
					robot.keyRelease(KeyEvent.VK_G);
				}

				if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance || earlyGPressed) {
					int minutes = (int) ((System.currentTimeMillis() - startTime) / 60000);
					System.out.println("Phase 1 finished at " + minutes + " mins");
					if (!isLaptop) {
						buyIdols(3000);
						buyUnlocks(3000);
					} else {
						buyIdols(3000);
						buyUnlocks(3000);
					}
					robot.keyPress(KeyEvent.VK_Q);
					robot.keyRelease(KeyEvent.VK_Q);
					Thread.sleep(1000);
					levelMainChicken();
					break;
				}

				switch (step) {
				case 0:
					robot.mouseMove(crusaderF.x, crusaderF.y);
					robot.keyPress(KeyEvent.VK_CONTROL);
					break;
				case 1:
					robot.mouseMove(originPoint.x - (int) (275 * scale), originPoint.y + (int) (60 * scale));
					robot.keyRelease(KeyEvent.VK_CONTROL);
					break;
				case 2:
					robot.mouseMove(originPoint.x - (int) (250 * scale), originPoint.y + (int) (60 * scale));
					break;
				case 3:
					robot.mouseMove(originPoint.x - (int) (225 * scale), originPoint.y + (int) (60 * scale));
					break;
				case 4:
					robot.mouseMove(originPoint.x - (int) (200 * scale), originPoint.y + (int) (60 * scale));
					break;
				case 5:
					robot.mouseMove(originPoint.x, originPoint.y);
					break;
				}

				step++;
				if (step == 6)
					step = 0;
				lastPoint = MouseInfo.getPointerInfo().getLocation();
				Thread.sleep(clickDelay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bot stopped");
	}

	private int numArrowHits = 0;

	/*
	private boolean ArrowExists() {
		Color colorCheck = robot.getPixelColor(goPoint.x, goPoint.y);
		boolean testColor1 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 50, goPoint.y);
		boolean testColor1a = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 55, goPoint.y);
		boolean testColor1b = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 60, goPoint.y);
		boolean testColor2 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 100, goPoint.y);
		boolean testColor3 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 150, goPoint.y);
		boolean testColor4 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 200, goPoint.y);
		boolean testColor5 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 250, goPoint.y);
		boolean testColor6 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 300, goPoint.y);
		boolean testColor7 = TestColorForArrow(colorCheck);
		colorCheck = robot.getPixelColor(goPoint.x - 350, goPoint.y);
		boolean testColor8 = TestColorForArrow(colorCheck);
		if (testColor1 || testColor1a || testColor1b || testColor2 || testColor3 || testColor4 || testColor5
				|| testColor6 || testColor7 || testColor8) {
			numArrowHits++;
		} else {
			numArrowHits = 0;
		}

		if (numArrowHits > 4) {
			numArrowHits = 0;
			return true;
		}

		return false;
	}
*/
	
	private void generatePoints() {
		crusaderA = new Point(originPoint.x - (int) (625 * scale), originPoint.y - (int) (55 * scale));
		crusaderB = new Point(originPoint.x - (int) (340 * scale), originPoint.y - (int) (55 * scale));
		crusaderC = new Point(originPoint.x - (int) (60 * scale), originPoint.y - (int) (55 * scale));
		crusaderD = new Point(originPoint.x - (int) (625 * scale), originPoint.y + (int) (25 * scale));
		crusaderE = new Point(originPoint.x - (int) (340 * scale), originPoint.y + (int) (25 * scale));
		crusaderF = new Point(originPoint.x - (int) (60 * scale), originPoint.y + (int) (25 * scale));
		lArrow = new Point(originPoint.x - (int) (875 * scale), originPoint.y);
		buffA1 = new Point(originPoint.x - (int) (849 * scale), originPoint.y - (int) (20 * scale));
		buffA2 = new Point(originPoint.x - (int) (823 * scale), originPoint.y - (int) (20 * scale));
		buffA3 = new Point(originPoint.x - (int) (797 * scale), originPoint.y - (int) (20 * scale));
		buffA4 = new Point(originPoint.x - (int) (771 * scale), originPoint.y - (int) (20 * scale));
		buffA5 = new Point(originPoint.x - (int) (745 * scale), originPoint.y - (int) (20 * scale));
		buffA6 = new Point(originPoint.x - (int) (719 * scale), originPoint.y - (int) (20 * scale));
		buffA7 = new Point(originPoint.x - (int) (693 * scale), originPoint.y - (int) (20 * scale));
		buffB1 = new Point(originPoint.x - (int) (849 * scale), originPoint.y + (int) (60 * scale));
		buffB2 = new Point(originPoint.x - (int) (823 * scale), originPoint.y + (int) (60 * scale));
		buffB3 = new Point(originPoint.x - (int) (797 * scale), originPoint.y + (int) (60 * scale));
		buffB4 = new Point(originPoint.x - (int) (771 * scale), originPoint.y + (int) (60 * scale));
		buffB5 = new Point(originPoint.x - (int) (745 * scale), originPoint.y + (int) (60 * scale));
		buffB6 = new Point(originPoint.x - (int) (719 * scale), originPoint.y + (int) (60 * scale));
		buffB7 = new Point(originPoint.x - (int) (693 * scale), originPoint.y + (int) (60 * scale));
		buyAllButton = new Point(originPoint.x, originPoint.y + (int) (40 * scale));
		unlockAllButton = new Point(originPoint.x, originPoint.y - (int) (40 * scale));
		goPoint = new Point(originPoint.x - (int) (50 * scale), originPoint.y - (int) (332 * scale));
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

	private void buyIdols(int length) {
		try {
			gatherCoins();
			robot.mouseMove(buyAllButton.x, buyAllButton.y);
			pressMouse(0);
			Thread.sleep(2000);
			Point currentPoint = MouseInfo.getPointerInfo().getLocation();
			if (Math.abs(buyAllButton.x - currentPoint.x) > stopDistance
					|| Math.abs(buyAllButton.y - currentPoint.y) > stopDistance) {
				System.exit(0);
			}
			Thread.sleep(length);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void buyUnlocks(int length) {
		try {
			robot.mouseMove(unlockAllButton.x, unlockAllButton.y);
			pressMouse(0);
			Thread.sleep(2000);
			Point currentPoint = MouseInfo.getPointerInfo().getLocation();
			if (Math.abs(unlockAllButton.x - currentPoint.x) > stopDistance
					|| Math.abs(unlockAllButton.y - currentPoint.y) > stopDistance) {
				System.exit(0);
			}
			Thread.sleep(length);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void resetGame() {
		try {
			buyIdols(2000);

			/*
			 * // Move to end robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_SHIFT); robot.mouseMove(originPoint.x,
			 * originPoint.y); Thread.sleep(500); for (int i = 0; i < 15; i++) {
			 * pressMouse(10); } robot.keyRelease(KeyEvent.VK_SHIFT);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 */

			// Reset buff
			robot.mouseMove(originPoint.x - (int) (150 * scale), originPoint.y + (int) (60 * scale));
			Thread.sleep(50);
			pressMouse(3000);

			// Reset confirm
			robot.mouseMove(originPoint.x - (int) (500 * scale), originPoint.y - (int) (50 * scale));
			Thread.sleep(50);
			pressMouse(3000);

			// Ad skip
			robot.mouseMove(originPoint.x - (int) (375 * scale), originPoint.y - (int) (160 * scale));
			Thread.sleep(50);
			pressMouse(6000);

			// Reset button
			robot.mouseMove(originPoint.x - (int) (430 * scale), originPoint.y - (int) (40 * scale));
			Thread.sleep(50);
			pressMouse(27500);

			// Continue button
			robot.mouseMove(originPoint.x - (int) (430 * scale), originPoint.y - (int) (30 * scale));
			Thread.sleep(50);
			pressMouse(7000);

			// Freeplay button
			// Drag down scrollbar
			robot.mouseMove(originPoint.x - (int) (360 * scale), originPoint.y - (int) (200 * scale));
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(50);
			robot.mouseMove(originPoint.x - (int) (360 * scale), originPoint.y);
			Thread.sleep(50);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(1000);

			if (!isEvent) {
				robot.mouseMove(originPoint.x - (int) (400 * scale), originPoint.y - (int) (0 * scale));
			} else {
				robot.mouseMove(originPoint.x - (int) (400 * scale), originPoint.y - (int) (80 * scale)); // Event!!!
			}

			Thread.sleep(50);
			pressMouse(4000);

			// Start objective button
			robot.mouseMove(originPoint.x - (int) (175 * scale), originPoint.y - (int) (20 * scale));
			Thread.sleep(50);
			pressMouse(4000);

			// Kill mobs
			robot.mouseMove(originPoint.x - (int) (160 * scale), originPoint.y - (int) (270 * scale));
			Thread.sleep(50);
			for (int i = 0; i < 50; i++) {
				// robot.keyPress(KeyEvent.VK_SPACE);
				// robot.keyRelease(KeyEvent.VK_SPACE);
				pressMouse(1000);
			}

			// Buy initial units
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);
			buyIdols(2000);
			buyUnlocks(2000);
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);

			// Restart bot
			robot.mouseMove(originPoint.x, originPoint.y);
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);
			earlyGPressed = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void gatherCoins() {
		try {
			for (int i = 0; i < 8; i++) {
				robot.mouseMove(originPoint.x - (int) (140 * scale), originPoint.y - (int) (260 * scale));
				Thread.sleep(50);
				robot.mouseMove(originPoint.x - (int) (130 * scale), originPoint.y - (int) (260 * scale));
				Thread.sleep(50);
				robot.mouseMove(originPoint.x - (int) (130 * scale), originPoint.y - (int) (250 * scale));
				Thread.sleep(50);
				robot.mouseMove(originPoint.x - (int) (140 * scale), originPoint.y - (int) (250 * scale));
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void levelMainChicken() {
		try {
			long longRunTime = 0;
			boolean newArrow = false;
			long wCutOff = System.currentTimeMillis() + WPressTime;

			// Move to end
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.mouseMove(originPoint.x, originPoint.y);
			Thread.sleep(50);
			for (int i = 0; i < 15; i++) {
				pressMouse(0);
			}
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			robot.mouseMove(lArrow.x, lArrow.y);
			pressMouse(100);
			pressMouse(100);

			Point chosenCrusader = crusaderA;

			robot.mouseMove(chosenCrusader.x, chosenCrusader.y);
			// robot.mouseMove(buyAllButton.x, buyAllButton.y);
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);

			nextStormRiderTime = System.currentTimeMillis() + 7000;
			boolean earlyGPressedPhase2 = false;

			while (true) {
				if (System.currentTimeMillis() > nextStormRiderTime) {
					castStormRider();
					nextStormRiderTime = System.currentTimeMillis() + stormRiderPressDelay;
					// int minutes = (int) ((System.currentTimeMillis() -
					// startTime) / 60000);
					// System.out.println("Storm rider called at " + minutes
					// + " mins");
				}

				if (!earlyGPressedPhase2 && !longRun && System.currentTimeMillis() > cutOffTime) {
					earlyGPressedPhase2 = true;
					robot.keyPress(KeyEvent.VK_G);
					robot.keyRelease(KeyEvent.VK_G);
					// cutOffTime += cutOffDelay;
				}

				if (System.currentTimeMillis() > wCutOff) {
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
				}

				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				// if (Math.abs(buyAllButton.x - currentPoint.x) > stopDistance
				// || Math.abs(buyAllButton.y - currentPoint.y) > stopDistance)
				// {
				if (Math.abs(chosenCrusader.x - currentPoint.x) > stopDistance
						|| Math.abs(chosenCrusader.y - currentPoint.y) > stopDistance) {
					break;
				}
				if (earlyGPressedPhase2) {
					if (!newArrow) {
						newArrow = true;
						longRunTime = System.currentTimeMillis() + longRunPressDelay;
					}

					if (longRun && newArrow && System.currentTimeMillis() > longRunTime) {
						int minutes = (int) ((System.currentTimeMillis() - startTime) / 60000);
						longRunTime = System.currentTimeMillis() + longRunPressDelay;
						System.out.println("G pressed at " + minutes + " mins");
						robot.keyPress(KeyEvent.VK_G);
						robot.keyRelease(KeyEvent.VK_G);
						newArrow = false;
					} else if (!longRun) {
						int minutes = (int) ((System.currentTimeMillis() - startTime) / 60000);
						System.out.println("Phase 2 finished at " + minutes + " mins");
						resetGame = true;
						resetNum++;
						robot.keyPress(KeyEvent.VK_Q);
						robot.keyRelease(KeyEvent.VK_Q);
						resetGame();
						break;
					}
				}
				Thread.sleep(phase3ClickDelay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void castStormRider() {
		long delay = 200;
		try {
			Thread.sleep(delay);
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			Thread.sleep(delay);
			robot.keyPress(KeyEvent.VK_7);
			robot.keyRelease(KeyEvent.VK_7);
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean TestColorForArrow(Color color) {
		if (color.getRed() < 245 || color.getRed() > 254) {
			return false;
		}

		if (color.getGreen() < 210 || color.getGreen() > 240) {
			return false;
		}

		if (color.getBlue() < 35 || color.getBlue() > 110) {
			return false;
		}

		return true;
	}
}
