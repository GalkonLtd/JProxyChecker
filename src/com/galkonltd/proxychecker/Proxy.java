package com.galkonltd.proxychecker;

import java.io.IOException;
import java.net.InetAddress;

/**
 * "The real danger is not that computers will begin to think like men, but that men will begin to think like computers." – Sydney Harris
 * Created on 10/31/2015
 *
 * @author Galkon
 */
public class Proxy {

    private final String host;
    private final int port;

    public Proxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean check() throws IOException {
        InetAddress addr = InetAddress.getByName(this.host);
        if (addr.isReachable(5000)) {
            return true;
        }
        return false;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

}
