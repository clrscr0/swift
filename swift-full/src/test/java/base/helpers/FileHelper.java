package base.helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

public class FileHelper {
	public static int getNumFolders(String folderPath) {
		int numFolders = 0;

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			numFolders = listOfFiles.length;
		}

		return numFolders;
	}

	public static void uploadFile(String filePath) {
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		// native key strokes for CTRL, V and ENTER keys
		try {
			Robot robot = new Robot();
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(3000);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
