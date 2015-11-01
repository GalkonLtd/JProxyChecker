# JProxyChecker
A free multithreaded proxy checking program written in Java. Load a proxy list and check each proxy to verify it's alive to create a new list of working proxies.

# Instructions
1. Download JProxyChecker.jar here: https://github.com/MrGalkon/JProxyChecker/blob/master/bin/JProxyChecker.jar
2. Create a run script (run.bat, run.sh) and use this:
        java -cp .;JProxyChecker.jar; com.galkonltd.proxychecker.Main
3. Put your proxy list in a file named "proxies.txt" in the runtime directory (wherever your run script is executed). It will automatically parse the list and filter out any duplicates.
4. All live proxies will be written out to a file named "working_MONTH_DATE_TIMESTAMP.txt".

Additionally, you can specify the thread count to use. The default is 64 threads. Simply add an argument to the run command to specify it, for example:
        java -cp .;JProxyChecker.jar; com.galkonltd.proxychecker.Main 128

# Requirements
Java 8+ - http://java.com/download/
