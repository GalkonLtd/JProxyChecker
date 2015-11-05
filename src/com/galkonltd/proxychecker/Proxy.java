package com.galkonltd.proxychecker;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
        try {
            InetAddress addr = InetAddress.getByName(this.host);
            if (addr.isReachable(5000)) {
                return (Main.checkGoogle ? checkGoogle() : true);
            }
        } catch (UnknownHostException e) {
            try {
                Socket socket = new Socket(host, port);
                InetSocketAddress addr = new InetSocketAddress("http://www.google.com", 80);
                socket.connect(addr, 10000);
                if (socket.isConnected()) {
                    return true;
                }
            } catch (IOException ex) {
            }
        }
        return false;
    }

    public boolean checkGoogle() {
        boolean connected = false;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://www.google.com/");
            HttpHost proxy = new HttpHost(host, port);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
            HttpResponse response = client.execute(get);
            if (response != null) {
                connected = true;
            }
        } catch (Exception ex) {
        }
        if (!connected) {
            try {
                Socket socket = new Socket(host, port);
                InetSocketAddress addr = new InetSocketAddress("http://www.google.com", 80);
                socket.connect(addr, 10000);
                if (socket.isConnected()) {
                    connected = true;
                }
            } catch (IOException e) {
            }
        }
        return connected;
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
