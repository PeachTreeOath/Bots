import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

public class ChampRunner {
	private boolean longRun = true;
	private static final double cutOffDelay = 1000 * 60 * 60;
	private static final double cutOffHour = 0.5;
	private static final int longRunPressDelay = 1000 * 60 * 5;
	private boolean ARROW_COLOR_DEBUG = false;
	private final int startDelay = 3000;
	private final int clickDelay = 500;
	private final int stopDistance = 50;
	private static Robot robot;
	private Point originPoint;
	private Point formationPoint;
	private Point buffStartingPoint;
	private Point perkStartingPoint;
	private Point goPoint;
	private float scale = 1f;
	long startTime;
	double cutOffTime;
	boolean resetGame;
	long longRunFarmTime;
	int resetNum = 1;
	int skillXIncrement = 15;
	int perkXIncrement = 25;
	private Point completeAdventurePoint;
	private Point confirmCompletePoint;
	private Point continuePoint;
	private Point chooseTownPoint;
	private Point chooseMissionPoint;
	private Point confirmMissionPoint;
	private Point autoProgressPoint;

	public static void main(String[] args) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		new ChampRunner();
	}

	public ChampRunner() {
		do {
			System.out.println("Run #" + resetNum);
			runBot();
		} while (resetGame);
	}

	private void runBot() {
		try {
			resetGame = false;
			startTime = System.currentTimeMillis();

			cutOffTime = System.currentTimeMillis() + cutOffDelay * cutOffHour;

			Thread.sleep(startDelay);
			System.out.println("Full Bot started");

			int step = 0;
			if (resetNum == 1) {
				originPoint = MouseInfo.getPointerInfo().getLocation();
			}
			generatePoints();

			Point lastPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();

			outerloop: while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(30);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);

				if (DoINeedToReset()) {
					// Farm for x seconds then check for arrow again if doing
					// long run
					if (longRun) {
						// Turn on auto-progress
						robot.mouseMove(autoProgressPoint.x, autoProgressPoint.y);
						Thread.sleep(50);
						pressMouse(1000);

						longRunFarmTime = System.currentTimeMillis() + longRunPressDelay;
					} else {
						resetGame = true;
						resetNum++;
						resetGame();
						break;
					}
				}

				if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
					break;
				}

				switch (step) {
				case 0:
					robot.mouseMove(formationPoint.x, formationPoint.y);
					break;
				case 1:
					int currx = buffStartingPoint.x;
					robot.mouseMove(currx, buffStartingPoint.y);
					lastPoint = MouseInfo.getPointerInfo().getLocation();
					for (int i = 0; i < 60; i++) {
						currentPoint = MouseInfo.getPointerInfo().getLocation();
						robot.mousePress(InputEvent.BUTTON1_MASK);
						Thread.sleep(30);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);

						if (DoINeedToReset()) {
							// Farm for x seconds then check for arrow again if doing
							// long run
							if (longRun) {
								// Turn on auto-progress
								robot.mouseMove(autoProgressPoint.x, autoProgressPoint.y);
								Thread.sleep(50);
								pressMouse(1000);

								longRunFarmTime = System.currentTimeMillis() + longRunPressDelay;
							} else {
								resetGame = true;
								resetNum++;
								resetGame();
								break outerloop;
							}
						}

						if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
								|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
							break outerloop;
						}

						currx -= skillXIncrement;
						robot.mouseMove(currx, buffStartingPoint.y);

						lastPoint = MouseInfo.getPointerInfo().getLocation();
						Thread.sleep(150);
					}
					break;
				case 2:
					int currx2 = perkStartingPoint.x;
					robot.mouseMove(currx2, perkStartingPoint.y);
					lastPoint = MouseInfo.getPointerInfo().getLocation();
					for (int i = 0; i < 32; i++) {
						currentPoint = MouseInfo.getPointerInfo().getLocation();
						robot.mousePress(InputEvent.BUTTON1_MASK);
						Thread.sleep(30);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);

						if (DoINeedToReset()) {
							// Farm for x seconds then check for arrow again if doing
							// long run
							if (longRun) {
								// Turn on auto-progress
								robot.mouseMove(autoProgressPoint.x, autoProgressPoint.y);
								Thread.sleep(50);
								pressMouse(1000);

								longRunFarmTime = System.currentTimeMillis() + longRunPressDelay;
							} else {
								resetGame = true;
								resetNum++;
								resetGame();
								break outerloop;
							}
						}

						if (Math.abs(lastPoint.x - currentPoint.x) > stopDistance
								|| Math.abs(lastPoint.y - currentPoint.y) > stopDistance) {
							break outerloop;
						}

						currx2 += perkXIncrement;
						robot.mouseMove(currx2, perkStartingPoint.y);

						lastPoint = MouseInfo.getPointerInfo().getLocation();
						Thread.sleep(150);
					}
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
		formationPoint = new Point(originPoint.x - (int) (1152 * scale), originPoint.y - (int) (30 * scale));
		buffStartingPoint = new Point(originPoint.x - (int) (0 * scale), originPoint.y + (int) (57 * scale));
		perkStartingPoint = new Point(originPoint.x - (int) (756 * scale), originPoint.y - (int) (65 * scale));
		goPoint = new Point(originPoint.x + (int) (40 * scale), originPoint.y - (int) (450 * scale));

		completeAdventurePoint = new Point(originPoint.x - (int) (971 * scale), originPoint.y - (int) (556 * scale));
		confirmCompletePoint = new Point(originPoint.x - (int) (616 * scale), originPoint.y - (int) (115 * scale));
		continuePoint = new Point(originPoint.x - (int) (532 * scale), originPoint.y - (int) (39 * scale));
		chooseTownPoint = new Point(originPoint.x - (int) (536), originPoint.y - (int) (277 * scale));
		chooseMissionPoint = new Point(originPoint.x - (int) (755 * scale), originPoint.y - (int) (505 * scale));
		confirmMissionPoint = new Point(originPoint.x - (int) (383 * scale), originPoint.y - (int) (104 * scale));
		autoProgressPoint = new Point(originPoint.x - (int) (-64 * scale), originPoint.y - (int) (533 * scale));
	}

	private void pressMouse(int delay) {
		try {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			Thread.sleep(30);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void resetGame() {
		try {
			System.out.println("RESETTING GAME");
			// Complete adventure
			robot.mouseMove(completeAdventurePoint.x, completeAdventurePoint.y);
			Thread.sleep(50);
			pressMouse(2000);

			// Reset confirm
			robot.mouseMove(confirmCompletePoint.x, confirmCompletePoint.y);
			Thread.sleep(50);
			pressMouse(12000);

			// Continue button
			robot.mouseMove(continuePoint.x, continuePoint.y);
			Thread.sleep(50);
			pressMouse(2000);

			// Choose town
			robot.mouseMove(chooseTownPoint.x, chooseTownPoint.y);
			Thread.sleep(50);
			pressMouse(1000);

			// Choose mission
			robot.mouseMove(chooseMissionPoint.x, chooseMissionPoint.y);
			Thread.sleep(50);
			pressMouse(1000);

			// Confirm mission
			robot.mouseMove(confirmMissionPoint.x, confirmMissionPoint.y);
			Thread.sleep(50);
			pressMouse(2500);

			// Turn on auto-progress
			robot.mouseMove(autoProgressPoint.x, autoProgressPoint.y);
			Thread.sleep(50);
			pressMouse(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int numArrowHits = 0;

	private boolean ArrowExists() {
		Color colorCheck = robot.getPixelColor(goPoint.x, goPoint.y);
		boolean testColor = TestColorForArrow(colorCheck);
		if (ARROW_COLOR_DEBUG) {
			System.out.println("Arrow pixel color = " + colorCheck + " is " + testColor);
		}
		if (testColor) {
			numArrowHits++;
		} else {
			numArrowHits = 0;
		}
		if (numArrowHits > 8) {
			numArrowHits = 0;
			return true;
		}

		return false;
	}

	private boolean TestColorForArrow(Color color) {
		if (color.getRed() < 220) {
			return false;
		}

		if (color.getGreen() < 140 || color.getGreen() > 240) {
			return false;
		}

		if (color.getBlue() < 45 || color.getBlue() > 75) {
			return false;
		}

		return true;
	}

	private boolean DoINeedToReset() {
		return ArrowExists() && System.currentTimeMillis() > longRunFarmTime;
	}
}
