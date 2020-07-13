package base.helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileHelper {
	private static final Logger log = LogManager.getLogger(FileHelper.class);
	
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
	
	public static void createMissingFolderRecursively(String dir){

		// Nested Directory to be create
		//String dir = "c:a1b2c3d4e5"; // windows path
		// String dir = "/tmp/a1/a2/a3/a4/a5"; // unix path
 
		// create the file pointer
		File file = new File(dir);
 
		// check if directory already exists or not
		if (file.exists()) {
			log.debug("Directory : " + dir + " already exists");
		} else {
 
			// create the non existent directory if any
			// It returns a boolean indicating whether the operation
			// was successful of not
			boolean retval = file.mkdirs();
 
			// evaluate the result
			if (retval) {
				log.debug("Directory : " + dir
						+ " created successfully");
			} else {
				log.debug("Directory : " + dir + " creation failed");
			}
		}
	}
}
