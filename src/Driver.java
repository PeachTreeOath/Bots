import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Driver {

	private enum GameType {
		IDLE_CH, ACTIVE;
	}

	private static GameType gameType = GameType.ACTIVE;

	private final int startDelay = 3000;
	private static int clickDelay;
	private static final int idle_CH_clickDelay = 10000;
	private static final int active_clickDelay = 25;
	private static final int stormRiderPressDelay = 1000 * 60 * 10;
	private boolean castStormRider = false;
	private static long nextStormRiderTime = 0;
	private int stopDistance = 50;
	private final boolean delayNeeded = true;
	private final boolean rangeFind = false;
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

		new Driver();
	}

	public Driver() {
		try {
			if (rangeFind) {
				stopDistance = 1000;
			}
			Thread.sleep(startDelay);
			System.out.println("Click Bot started");

			nextStormRiderTime = System.currentTimeMillis();
			robot = new Robot();
			Point originPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();
			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				// if (gameType == GameType.IDLE_CH)
				 robot.keyPress(KeyEvent.VK_CONTROL);
				
				if(!rangeFind)
				{
					robot.mousePress(InputEvent.BUTTON1_MASK);						
					
					if (delayNeeded)
						Thread.sleep(10);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				}

				// if (gameType == GameType.IDLE_CH)
				 robot.keyRelease(KeyEvent.VK_CONTROL);
				if (Math.abs(originPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(originPoint.y - currentPoint.y) > stopDistance) {
					break;
				}

				if (!rangeFind) {
					if (castStormRider && System.currentTimeMillis() > nextStormRiderTime) {
						castStormRider();
						nextStormRiderTime = System.currentTimeMillis() + stormRiderPressDelay;
					}
				}

				if (rangeFind) {
					System.out.println(Math.abs(currentPoint.x - originPoint.x) + " : "
							+ Math.abs(currentPoint.y - originPoint.y));
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

	private void castStormRider() {
		long delay = 300;
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
}
