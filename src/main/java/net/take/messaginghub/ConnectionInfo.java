package net.take.messaginghub;

import org.limeprotocol.SessionCompression;
import org.limeprotocol.SessionEncryption;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionInfo {

    public ConnectionInfo() {
        try {
            instance = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            instance = "default";
        }
        domain = hostName = "msging.net";
        port = 55321;
    }

    public String identifier;

    public String instance;

    public String password;

    public String accessKey;

    public String hostName;

    public Integer port;

    public String domain;

    public SessionCompression compression;

    public SessionEncryption encryption;
}
