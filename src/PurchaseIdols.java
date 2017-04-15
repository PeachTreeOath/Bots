import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class PurchaseIdols {

	private boolean hitG = false;
	
	private final int startDelay = 3000;
	private final int clickDelay = 500;
	private final int hermitClickDelay = 2000;
	private final int stopDistance = 50;
	private final long phase2Delay = 1000 * 60 * 80; // 80 mins
	private Robot robot;
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

	// System.out.println(Math.abs(currentPoint.x - originPoint.x)
	// + " : " + Math.abs(currentPoint.y - originPoint.y));

	public static void main(String[] args) {
		new PurchaseIdols();
	}

	public PurchaseIdols() {
		try {
			long startTime = System.currentTimeMillis();
			Thread.sleep(startDelay);
			System.out.println("Bot started");

			// 0 = right arrow
			// 1 = recruit button
			// 2 = buff 1
			// 3 = buff 2
			// 4 = buff 3
			// 5 = buff 4
			int step = 0;

			robot = new Robot();
			originPoint = MouseInfo.getPointerInfo().getLocation();
			generatePoints();

			Point lastPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();

			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);

//					robot.keyPress(KeyEvent.VK_G);
					buyIdols();
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bot stopped");
	}

	private void generatePoints() {
		crusaderA = new Point(originPoint.x - 625, originPoint.y - 55);
		crusaderB = new Point(originPoint.x - 340, originPoint.y - 55);
		crusaderC = new Point(originPoint.x - 60, originPoint.y - 55);
		crusaderD = new Point(originPoint.x - 625, originPoint.y + 25);
		crusaderE = new Point(originPoint.x - 340, originPoint.y + 25);
		crusaderF = new Point(originPoint.x - 60, originPoint.y + 25);
		lArrow = new Point(originPoint.x - 875, originPoint.y);
		buffA1 = new Point(originPoint.x - 849, originPoint.y - 20);
		buffA2 = new Point(originPoint.x - 823, originPoint.y - 20);
		buffA3 = new Point(originPoint.x - 797, originPoint.y - 20);
		buffA4 = new Point(originPoint.x - 771, originPoint.y - 20);
		buffA5 = new Point(originPoint.x - 745, originPoint.y - 20);
		buffA6 = new Point(originPoint.x - 719, originPoint.y - 20);
		buffA7 = new Point(originPoint.x - 693, originPoint.y - 20);
		buffB1 = new Point(originPoint.x - 849, originPoint.y + 60);
		buffB2 = new Point(originPoint.x - 823, originPoint.y + 60);
		buffB3 = new Point(originPoint.x - 797, originPoint.y + 60);
		buffB4 = new Point(originPoint.x - 771, originPoint.y + 60);
		buffB5 = new Point(originPoint.x - 745, originPoint.y + 60);
		buffB6 = new Point(originPoint.x - 719, originPoint.y + 60);
		buffB7 = new Point(originPoint.x - 693, originPoint.y + 60);
	}

	private void buyIdols() {
		try {
			levelCrusaderPage();

			robot.mouseMove(lArrow.x, lArrow.y);
			for (int i = 0; i < 3; i++) {
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(250);
			}

			levelCrusaderPage();

			robot.mouseMove(lArrow.x, lArrow.y);
			for (int i = 0; i < 3; i++) {
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(250);
			}

			levelCrusaderPage();

			robot.mouseMove(lArrow.x, lArrow.y);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(10);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(250);

			gatherCoins();
			robot.mouseMove(crusaderD.x, crusaderD.y);
			levelUpCrusader();

			gatherCoins();
			robot.mouseMove(crusaderA.x, crusaderA.y);
			levelUpCrusader();

			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.mouseMove(originPoint.x, originPoint.y);
			for (int i = 0; i < 7; i++) {
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				Thread.sleep(250);
			}
			robot.keyRelease(KeyEvent.VK_SHIFT);

			gatherCoins();

			robot.mouseMove(originPoint.x - 200, originPoint.y + 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	private void levelCrusaderPage() {
		try {
			gatherCoins();
			robot.mouseMove(crusaderF.x, crusaderF.y);
			levelUpCrusader();

			gatherCoins();
			robot.mouseMove(crusaderC.x, crusaderC.y);
			levelUpCrusader();

			gatherCoins();
			robot.mouseMove(crusaderE.x, crusaderE.y);
			levelUpCrusader();

			gatherCoins();
			robot.mouseMove(crusaderB.x, crusaderB.y);
			levelUpCrusader();

			gatherCoins();
			robot.mouseMove(crusaderD.x, crusaderD.y);
			levelUpCrusader();

			gatherCoins();
			robot.mouseMove(crusaderA.x, crusaderA.y);
			levelUpCrusader();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void levelUpCrusader() throws InterruptedException {
		Point lastPoint = MouseInfo.getPointerInfo().getLocation();

		robot.keyPress(KeyEvent.VK_CONTROL);
		for (int i = 0; i < 20; i++) {
			Point currentPoint = MouseInfo.getPointerInfo().getLocation();
			if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
					|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
				robot.keyRelease(KeyEvent.VK_CONTROL);
				System.exit(0);
			}
			robot.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(10);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(25);
		}
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	private void gatherCoins() {
		try {
			for (int i = 0; i < 8; i++) {
				robot.mouseMove(originPoint.x - 140, originPoint.y - 260);
				Thread.sleep(50);
				robot.mouseMove(originPoint.x - 130, originPoint.y - 260);
				Thread.sleep(50);
				robot.mouseMove(originPoint.x - 130, originPoint.y - 250);
				Thread.sleep(50);
				robot.mouseMove(originPoint.x - 140, originPoint.y - 250);
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void purchaseBuffs() {
		try {
			for (int j = 0; j < 7; j++) {
				for (int i = 0; i < 15; i++) {
					switch (i) {
					case 0:
						robot.mouseMove(lArrow.x, lArrow.y);
						break;
					case 1:
						Thread.sleep(400);
						robot.mouseMove(buffA1.x, buffA1.y);
						break;
					case 2:
						robot.mouseMove(buffA2.x, buffA2.y);
						break;
					case 3:
						robot.mouseMove(buffA3.x, buffA3.y);
						break;
					case 4:
						robot.mouseMove(buffA4.x, buffA4.y);
						break;
					case 5:
						robot.mouseMove(buffA5.x, buffA5.y);
						break;
					case 6:
						robot.mouseMove(buffA6.x, buffA6.y);
						break;
					case 7:
						robot.mouseMove(buffA7.x, buffA7.y);
						break;
					case 8:
						robot.mouseMove(buffB1.x, buffB1.y);
						break;
					case 9:
						robot.mouseMove(buffB2.x, buffB2.y);
						break;
					case 10:
						robot.mouseMove(buffB3.x, buffB3.y);
						break;
					case 11:
						robot.mouseMove(buffB4.x, buffB4.y);
						break;
					case 12:
						robot.mouseMove(buffB5.x, buffB5.y);
						break;
					case 13:
						robot.mouseMove(buffB6.x, buffB6.y);
						break;
					case 14:
						robot.mouseMove(buffB7.x, buffB7.y);
						break;
					}
					Point lastPoint = MouseInfo.getPointerInfo().getLocation();
					robot.mousePress(InputEvent.BUTTON1_MASK);
					Thread.sleep(10);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					Thread.sleep(25);
					Point currentPoint = MouseInfo.getPointerInfo()
							.getLocation();
					if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
							|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
						break;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void levelHermit() {
		try {
			robot.mouseMove(crusaderC.x, crusaderC.y);
			levelUpCrusader();
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyPress(KeyEvent.VK_G);

			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				if (Math.abs(crusaderC.x - currentPoint.x) > stopDistance
						|| Math.abs(crusaderC.y - currentPoint.y) > stopDistance) {
					break;
				}
				Thread.sleep(hermitClickDelay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}