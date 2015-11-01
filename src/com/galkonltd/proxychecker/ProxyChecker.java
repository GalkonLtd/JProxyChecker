package com.galkonltd.proxychecker;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * "The real danger is not that computers will begin to think like men, but that men will begin to think like computers." � Sydney Harris
 * Created on 10/31/2015
 *
 * @author Galkon
 */
public class ProxyChecker {

    private static ExecutorService PROXY_CHECKER;
    private static final ExecutorService PROXY_WRITER = Executors.newSingleThreadExecutor();

    private static final Logger LOGGER = Logger.getLogger(ProxyChecker.class.getName());

    private final List<Proxy> proxyList;
    private int deadProxies;
    private int workingProxies;

    private final Date date = new Date();

    public ProxyChecker(int threadCount) {
        this.proxyList = new ArrayList<>();
        this.deadProxies = this.workingProxies = 0;
        PROXY_CHECKER = Executors.newFixedThreadPool(threadCount);
    }

    public void parseProxies(String file) throws IOException {
        File proxyFile = new File(file);
        if (!proxyFile.exists()) {
            throw new RuntimeException("Unable to find proxy file: " + proxyFile.getAbsolutePath());
        }
        LOGGER.info("Parsing proxies...");
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String line;
        int passed = 0;
        int failed = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains(":")) {
                String[] args = line.split(":");
                if (args.length == 2) {
                    String host = args[0];
                    int port = Integer.parseInt(args[1]);
                    Proxy proxy = new Proxy(host, port);
                    if (!proxyList.contains(proxy)) {
                        proxyList.add(proxy);
                    }
                    passed++;
                } else {
                    failed++;
                }
            }
        }
        br.close();
        LOGGER.info("Parsed proxy list. " + passed + " passed & " + failed + " failed.");
    }

    public void verifyProxies() throws IOException {
        LOGGER.info("Verifying " + this.proxyList.size() + " proxies...");
        for (Proxy proxy : this.proxyList) {
            verifyProxy(proxy);
        }
    }

    private void verifyProxy(Proxy proxy) {
        PROXY_CHECKER.submit(() -> {
            try {
                LOGGER.info("Verifying proxy: " + proxy.toString());
                if (proxy.check()) {
                    writeWorkingProxy(proxy);
                    workingProxies++;
                } else {
                    deadProxies++;
                }
                LOGGER.info("Proxy checking status: " + workingProxies + " working, " + deadProxies + " dead.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeWorkingProxy(Proxy proxy) throws IOException {
        PROXY_WRITER.submit(() -> {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("working_" + (this.date.getMonth() + 1) + "-" + this.date.getDay() + "-" + this.date.getTime() + ".txt", true));
                if (workingProxies > 0) {
                    out.write("\n");
                }
                out.write(proxy.getHost() + ":" + proxy.getPort());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}