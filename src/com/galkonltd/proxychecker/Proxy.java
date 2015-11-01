package com.galkonltd.proxychecker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

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
            return connect(this.host, this.port);
        }
        return false;
    }

    private boolean connect(String host, int port) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://www.google.com/");
            HttpHost proxy = new HttpHost(host, port);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
            HttpResponse response = client.execute(get);
            HttpEntity enti = response.getEntity();
            if (response != null) {
                return true;
            }
        } catch (Exception ex) {
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
