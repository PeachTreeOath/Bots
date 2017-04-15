import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ProgressionBot {

	private final int startDelay = 3000;
	private static final int clickDelay = 3000;
	private static final int pressDelay = 1000*60*30;

	private long lastPress = 0;
	
	private final int stopDistance = 50;

	public static void main(String[] args) {
		new ProgressionBot();
	}

	public ProgressionBot() {
		try {
			Thread.sleep(startDelay);
			System.out.println("Bot started");

			Robot robot = new Robot();
			Point originPoint = MouseInfo.getPointerInfo().getLocation();
			Toolkit.getDefaultToolkit().beep();
			while (true) {
				Point currentPoint = MouseInfo.getPointerInfo().getLocation();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				Thread.sleep(10);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				if (Math.abs(originPoint.x - currentPoint.x) > stopDistance
						|| Math.abs(originPoint.y - currentPoint.y) > stopDistance) {
					break;
				}
				if(System.currentTimeMillis() - lastPress > pressDelay)
				{
//					robot.keyPress(KeyEvent.VK_Q);
//					robot.keyRelease(KeyEvent.VK_Q);
					robot.keyPress(KeyEvent.VK_G);
					robot.keyRelease(KeyEvent.VK_G);
					lastPress = System.currentTimeMillis();
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
