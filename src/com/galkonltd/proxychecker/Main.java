package com.galkonltd.proxychecker;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * "The real danger is not that computers will begin to think like men, but that men will begin to think like computers." – Sydney Harris
 * Created on 10/31/2015
 *
 * @author Galkon
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        int threadCount = 64;
        if (args != null) {
            if (args.length >= 1) {
                threadCount = Integer.parseInt(args[0]);
            }
        }
        ProxyChecker checker = new ProxyChecker(threadCount);
        try {
            LOGGER.info("Parsing proxy list...");
            checker.parseProxies("proxies.txt");
            LOGGER.info("Verifying proxies using " + threadCount + " threads...");
            checker.verifyProxies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
