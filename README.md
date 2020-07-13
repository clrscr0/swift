# Selenium WebDriver Integrated Framework and Tools

| Release |
| :--- |
| 0.2-ALPHA |

### This framework uses:
* Selenium 3
* JDK
* Maven
* Cucumber
* JUnit

Please follow below guide after importing the project in Eclipse.

## Set m2 repository proxy (if required)
1. Go to m2 repository location in C:\\Users\\{user}\\.m2
2. Download <a href="settings.xml">settings.xml</a> and paste on this location.

## Eclipse setup
1. Make sure you have an updated copy of the repository by pulling all changes from Git
2. Right-click on project
3. Select Maven > Update Project

## Install dependencies
1. Right click on project (on Eclipse)
2. Select Run As > Maven install. 
3. Right click on the project again
4. Select Run As > Maven clean. This will remove all errors in the project.

## Final checks
1. Go to src\test\java
2. Expand base.testrunner package
3. Right-click on CucumberTestRunner.java
4. Select Run As > JUnit Test
 
## Troubleshooting
### Execution fails on Internet Explorer
* Make sure Internet Explorer is zoomed at 100%
* Check that that setting is the same for Enable Protected Mode in all types of security settings zone:
  1. Click on gear icon (upper-right) of Internet Explorer
  2. Click on Internet options
  3. Select Security tab
  4. Mark Enable Protected Mode checkbox in all zones (make sure setting for said checkbox is the same)
  5. Save all changes and close Internet options window then restart Internet Explorer.

### Execution fails on Firefox
1. Click on firefox burger icon (upper right)
2. Click on Options
3. Select Privacy tab
4. Click on 'clear your recent history'
5. Delete everything (all details, time range = everything)

### JRE or Java version issues
1. Right-click on project
2. Select Build Path > Configure Build Path
3. Click Libraries tab
4. Click on JRE System Library
5. Click Edit button (right)
6. Select the latest JDK (1.8) from the Execute environment dropdown. Then apply and close all dialog boxes (you can stop at this step. If you don't see any JDK option in the dropdown, proceed to the next steps).
7. Click Installed JREs button
8. Click Add button
9. Select Standard VM and click Next
10. Click Directory and look for your JDK installation directory before the bin folder (ex. C:\Program Files\Java\jdk1.8.0_112)
11. Click Finish
12. Click Apply then OK button to close the dialog box. The JDK option will now be available in the Execute environment dropdown. Go back to Step 6.
