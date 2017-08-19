import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class PointFinder {

	private enum GameType {
		IDLE_CH, ACTIVE;
	}

	private static GameType gameType = GameType.ACTIVE;

	private final int startDelay = 3000;
	private static int clickDelay;
	private static final int idle_CH_clickDelay = 10000;
	private static final int active_clickDelay = 1000;
	private int stopDistance = 50;
	private final boolean rangeFind = true;
	private Robot robot;

	public static void main(String[] args) {
		switch (gameType) {
		case ACTIVE:
			clickDelay = active_clickDelay;
			break;
		case IDLE_CH:
			clickDelay = idle_CH_clickDelay;
			break;
		default:
			break;

		}

		new PointFinder();
	}

	public PointFinder() {
		try {
			if (rangeFind) {
				stopDistance = 1500;
			}
			Thread.sleep(startDelay);
			System.out.println("Click Bot started");

			robot = new Robot();
			Point originPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();
			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				if (Math.abs(originPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(originPoint.y - currentPoint.y) > stopDistance) {
					break;
				}

				if (rangeFind) {

					System.out.println(Math.abs(currentPoint.x - originPoint.x) + " : "
							+ Math.abs(currentPoint.y - originPoint.y));
					//System.out.println(robot.getPixelColor(currentPoint.x, currentPoint.y));
					// Y Values above pixel point are negative
				}
				Thread.sleep(clickDelay);
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Bot stopped");
	}

}
