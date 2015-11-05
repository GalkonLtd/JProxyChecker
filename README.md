# JProxyChecker
A free multithreaded proxy checking program written in Java. Load a proxy list and check each proxy to verify it's alive to create a new list of working proxies.

# Instructions
1. Download JProxyChecker.jar here: https://github.com/MrGalkon/JProxyChecker/blob/master/bin/JProxyChecker.jar
2. It is an executable .jar, so you can simply run the .jar. If you wish to create a run script (run.bat, run.sh), use this:

		java -cp .;JProxyChecker.jar; com.galkonltd.proxychecker.Main

3. Put your proxy list in a file named "proxies.txt" in the runtime directory (wherever your run script or .jar file is located). It will automatically parse the list and filter out any duplicates.
4. Set the settings to whatever you wish to use (thread count, check google.com). For a list of proxies that can actually connect to google.com, use the google.com check. For faster processing, use a higher thread count.
5. Hit the 'Run Check' button to begin your proxy checking.
6. All live proxies will be written out to a file named "working_MONTH_DATE_TIMESTAMP.txt".

# Requirements
Java 8+ - http://java.com/download/
