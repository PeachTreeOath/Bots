import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CoinCollector {
	private boolean isEvent = true;
	private final boolean isLaptop = false;
	private boolean longRun = false;
	private final boolean hitG = false;
	private final int startDelay = 3000;
	private final int clickDelay = 500;
	private final int hermitClickDelay = 2000;
	private final int stopDistance = 50;
	private final long phase2Delay = 1000 * 60 * 70; // mins
	private final long phase3Delay = 1000 * 60 * 325; // mins
	// private final long phase2Delay = 1000 * 60 * 120; // mins
	// private final long phase3Delay = 1000 * 60 * 3600; // mins
	// private final long phase3Delay = 0; // for testing reset
	private static final int pressDelay = 1000 * 60 * 30;
	private long lastPress = 0;
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
	private Point buyAllConfirm;

	long startTime;
	boolean resetGame;
	int resetNum = 1;

	// System.out.println(Math.abs(currentPoint.x - originPoint.x)
	// + " : " + Math.abs(currentPoint.y - originPoint.y));

	public static void main(String[] args) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		new CoinCollector();
	}

	public CoinCollector() {
		do {
			System.out.println("Run #" + resetNum);
			runBot();
		} while (resetGame);
	}

	private void runBot() {
		try {
			resetGame = false;
			startTime = System.currentTimeMillis();
			Thread.sleep(startDelay);
			System.out.println("Bot started");

			int step = 0;

			originPoint = MouseInfo.getPointerInfo().getLocation();
			generatePoints();

			Point lastPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();

			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();

				if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
					break;
				}
				gatherCoins();
				lastPoint = MouseInfo.getPointerInfo().getLocation();
				Thread.sleep(clickDelay);
			}
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
		buyAllButton = new Point(originPoint.x, originPoint.y + 40);
		unlockAllButton = new Point(originPoint.x, originPoint.y - 40);
		buyAllConfirm = new Point(originPoint.x - 500, originPoint.y - 200);
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
			robot.mouseMove(buyAllConfirm.x, buyAllConfirm.y);
			pressMouse(10);
			pressMouse(10);
			pressMouse(10);
			pressMouse(10);
			pressMouse(10);
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
			robot.mouseMove(buyAllConfirm.x, buyAllConfirm.y);
			pressMouse(10);
			pressMouse(10);
			pressMouse(10);
			pressMouse(10);
			pressMouse(10);
			Thread.sleep(length);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void resetGame() {
		try {
			buyIdols(7000);

			// Move to end
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.mouseMove(originPoint.x, originPoint.y);
			Thread.sleep(500);
			for (int i = 0; i < 15; i++) {
				pressMouse(10);
			}
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			// Reset buff
			robot.mouseMove(originPoint.x - 150, originPoint.y + 60);
			Thread.sleep(50);
			pressMouse(4000);

			// Reset confirm
			robot.mouseMove(originPoint.x - 525, originPoint.y - 60);
			Thread.sleep(50);
			pressMouse(4000);

			// Reset button
			robot.mouseMove(originPoint.x - 430, originPoint.y - 40);
			Thread.sleep(50);
			pressMouse(25000);

			// Continue button
			robot.mouseMove(originPoint.x - 430, originPoint.y - 30);
			Thread.sleep(50);
			pressMouse(4000);

			// Freeplay button
			// Drag down scrollbar
			robot.mouseMove(originPoint.x - 360, originPoint.y - 200);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(50);
			robot.mouseMove(originPoint.x - 360, originPoint.y);
			Thread.sleep(50);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(1000);

			if (!isEvent) {
				robot.mouseMove(originPoint.x - 400, originPoint.y - 100);
			} else {
				robot.mouseMove(originPoint.x - 400, originPoint.y - 180); // Event!
			}

			Thread.sleep(50);
			pressMouse(4000);

			// Start objective button
			robot.mouseMove(originPoint.x - 175, originPoint.y - 20);
			Thread.sleep(50);
			pressMouse(4000);

			// Kill mobs
			robot.mouseMove(originPoint.x - 140, originPoint.y - 260);
			Thread.sleep(50);
			for (int i = 0; i < 100; i++) {
				pressMouse(1000);
			}

			// Buy initial units
			robot.keyPress(KeyEvent.VK_E);
			robot.keyRelease(KeyEvent.VK_E);
			buyIdols(3000);

			// Restart bot
			robot.mouseMove(originPoint.x, originPoint.y);
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void levelUpCrusader() throws InterruptedException {
		Point lastPoint = MouseInfo.getPointerInfo().getLocation();

		robot.keyPress(KeyEvent.VK_CONTROL);
		for (int i = 0; i < 10; i++) {
			Point currentPoint = MouseInfo.getPointerInfo().getLocation();
			if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
					|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
				robot.keyRelease(KeyEvent.VK_CONTROL);
				System.exit(0);
			}
			if (!isLaptop) {
				pressMouse(25);
			} else {
				pressMouse(175);
			}
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

	// Currently Sal
	private void levelMainChicken() {
		try {
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

			robot.mouseMove(crusaderA.x, crusaderA.y);
			levelUpCrusader();
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);
			if (phase3Delay != 0) {
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_G);
			}

			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				if (Math.abs(crusaderA.x - currentPoint.x) > stopDistance
						|| Math.abs(crusaderA.y - currentPoint.y) > stopDistance) {
					break;
				}
				if (System.currentTimeMillis() - startTime > phase3Delay) {
					if (longRun) {
						if (System.currentTimeMillis() - lastPress > pressDelay) {
							robot.keyPress(KeyEvent.VK_G);
							robot.keyRelease(KeyEvent.VK_G);
							lastPress = System.currentTimeMillis();
						}
					} else {
						resetGame = true;
						resetNum++;
						resetGame();
						break;
					}
				}
				Thread.sleep(hermitClickDelay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

// private void purchaseSingleCrusaderBuffs(Point crusaderPoint) {
// Point currPoint = new Point(crusaderPoint.x, crusaderPoint.y);
// // Move to first buff
// currPoint.x -= 224;
// currPoint.y += 35;
// for (int j = 0; j < 7; j++) {
// robot.mouseMove(currPoint.x, currPoint.y);
// pressMouse(200);
// currPoint.x += 26;
// }
// }
// private void purchaseBuffs() {
// robot.keyPress(KeyEvent.VK_Q);
// robot.keyRelease(KeyEvent.VK_Q);
// robot.keyPress(KeyEvent.VK_E);
// robot.keyRelease(KeyEvent.VK_E);
//
// try {
// // Buy first page first
// purchaseSingleCrusaderBuffs(crusaderA);
// purchaseSingleCrusaderBuffs(crusaderB);
// purchaseSingleCrusaderBuffs(crusaderC);
// purchaseSingleCrusaderBuffs(crusaderD);
// purchaseSingleCrusaderBuffs(crusaderE);
//
// for (int j = 0; j < 7; j++) {
// for (int i = 0; i < 15; i++) {
// switch (i) {
// case 0:
// robot.mouseMove(lArrow.x, lArrow.y);
// break;
// case 1:
// robot.mouseMove(buffA1.x, buffA1.y);
// if (!isLaptop) {
// Thread.sleep(400);
// } else {
// Thread.sleep(800);
// }
// break;
// case 2:
// robot.mouseMove(buffA2.x, buffA2.y);
// break;
// case 3:
// robot.mouseMove(buffA3.x, buffA3.y);
// break;
// case 4:
// robot.mouseMove(buffA4.x, buffA4.y);
// break;
// case 5:
// robot.mouseMove(buffA5.x, buffA5.y);
// break;
// case 6:
// robot.mouseMove(buffA6.x, buffA6.y);
// break;
// case 7:
// robot.mouseMove(buffA7.x, buffA7.y);
// break;
// case 8:
// robot.mouseMove(buffB1.x, buffB1.y);
// break;
// case 9:
// robot.mouseMove(buffB2.x, buffB2.y);
// break;
// case 10:
// robot.mouseMove(buffB3.x, buffB3.y);
// break;
// case 11:
// robot.mouseMove(buffB4.x, buffB4.y);
// break;
// case 12:
// robot.mouseMove(buffB5.x, buffB5.y);
// break;
// case 13:
// robot.mouseMove(buffB6.x, buffB6.y);
// break;
// case 14:
// robot.mouseMove(buffB7.x, buffB7.y);
// break;
// }
// Point lastPoint = MouseInfo.getPointerInfo().getLocation();
// robot.mousePress(InputEvent.BUTTON1_MASK);
// Thread.sleep(10);
// robot.mouseRelease(InputEvent.BUTTON1_MASK);
// Thread.sleep(200);
// Point currentPoint = MouseInfo.getPointerInfo()
// .getLocation();
// if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
// || Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
// break;
// }
// }
// }
// } catch (InterruptedException e) {
// e.printStackTrace();
// }
// }

// private void buyIdols() {
// try {
// levelCrusaderPage();
//
// robot.mouseMove(lArrow.x, lArrow.y);
// for (int i = 0; i < 3; i++) {
// robot.mousePress(InputEvent.BUTTON1_MASK);
// Thread.sleep(10);
// robot.mouseRelease(InputEvent.BUTTON1_MASK);
// Thread.sleep(250);
// }
//
// levelCrusaderPage();
//
// robot.mouseMove(lArrow.x, lArrow.y);
// for (int i = 0; i < 3; i++) {
// robot.mousePress(InputEvent.BUTTON1_MASK);
// Thread.sleep(10);
// robot.mouseRelease(InputEvent.BUTTON1_MASK);
// Thread.sleep(250);
// }
//
// levelCrusaderPage();
//
// robot.mouseMove(lArrow.x, lArrow.y);
// robot.mousePress(InputEvent.BUTTON1_MASK);
// Thread.sleep(10);
// robot.mouseRelease(InputEvent.BUTTON1_MASK);
// Thread.sleep(250);
//
// gatherCoins();
// robot.mouseMove(crusaderD.x, crusaderD.y);
// levelUpCrusader();
//
// gatherCoins();
// robot.mouseMove(crusaderA.x, crusaderA.y);
// levelUpCrusader();
//
// robot.keyPress(KeyEvent.VK_SHIFT);
// robot.mouseMove(originPoint.x, originPoint.y);
// robot.mousePress(InputEvent.BUTTON1_MASK);
// Thread.sleep(10);
// robot.mouseRelease(InputEvent.BUTTON1_MASK);
// robot.keyRelease(KeyEvent.VK_SHIFT);
//
// gatherCoins();
//
// robot.mouseMove(originPoint.x - 200, originPoint.y + 60);
// } catch (InterruptedException e) {
// e.printStackTrace();
// }
//
// robot.keyRelease(KeyEvent.VK_CONTROL);
// }

// private void levelCrusaderPage() {
// try {
// gatherCoins();
// robot.mouseMove(crusaderF.x, crusaderF.y);
// levelUpCrusader();
//
// gatherCoins();
// robot.mouseMove(crusaderC.x, crusaderC.y);
// levelUpCrusader();
//
// gatherCoins();
// robot.mouseMove(crusaderE.x, crusaderE.y);
// levelUpCrusader();
//
// gatherCoins();
// robot.mouseMove(crusaderB.x, crusaderB.y);
// levelUpCrusader();
//
// gatherCoins();
// robot.mouseMove(crusaderD.x, crusaderD.y);
// levelUpCrusader();
//
// gatherCoins();
// robot.mouseMove(crusaderA.x, crusaderA.y);
// levelUpCrusader();
//
// } catch (InterruptedException e) {
// e.printStackTrace();
// }
// }
