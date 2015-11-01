# JProxyChecker
A free multithreaded proxy checking program written in Java. Load a proxy list and check each proxy to verify it's alive.

To run out of the box, use the .jar located at "/bin/JProxyCheckerAIO.jar". Here is the start script (for batch or shell scripts):

    java -cp .;JProxyCheckerAIO.jar; com.galkonltd.proxychecker.Main
  
If you want to specify the thread count to use, add an argument to that line. Default thread count is 64. For example, to use 128 threads:

    java -cp .;JProxyCheckerAIO.jar; com.galkonltd.proxychecker.Main 128

Put your "proxies.txt" file in the runtime directory, and it will automatically parse it and filter any duplicates. All live proxies will be written out to a file titled "working_MONTH_DATE_TIMESTAMP.txt" in the runtime directory.

# Requirements
Java 8+ - http://java.com/download/
