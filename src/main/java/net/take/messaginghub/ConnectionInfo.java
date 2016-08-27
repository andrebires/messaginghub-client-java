package net.take.messaginghub;

import org.limeprotocol.Identity;
import org.limeprotocol.SessionCompression;
import org.limeprotocol.SessionEncryption;
import org.limeprotocol.security.Authentication;
import org.limeprotocol.security.GuestAuthentication;
import org.limeprotocol.security.KeyAuthentication;
import org.limeprotocol.security.PlainAuthentication;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
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

    public Identity getIdentity() {
        return new Identity(identifier, domain);
    }

    public Authentication getAuthentication() {
        if (password != null) {
            return new PlainAuthentication() {{
                setPassword(password);
            }};
        }
        if (accessKey != null) {
            return new KeyAuthentication() {{
                setKey(password);
            }};
        }
        return new GuestAuthentication();
    }

    public URI getUri() {
        try {
            return new URI(String.format("net.tcp://%s:%d", hostName, port));
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
