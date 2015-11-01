# JProxyChecker
A free proxy checking program. Load a proxy list and check each proxy to verify it's alive.

To run out of the box, use the .jar located at "/bin/JProxyCheckerAIO.jar". Here is the start script (for batch or shell scripts):

    java -cp .;JProxyCheckerAIO.jar; com.galkonltd.proxychecker.Main
  
If you want to specify the thread count to use, add an argument to that line. Default thread count is 64. For example, to use 128 threads:

    java -cp .;JProxyCheckerAIO.jar; com.galkonltd.proxychecker.Main 128
