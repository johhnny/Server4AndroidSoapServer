package edu.agh.sp.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Krzysztof Broncel
 * on 05.01.2015
 */
public class ASServerObject implements Serializable, Comparable<ASServerObject> {
    private String serverDeviceId;
    private String serverDeviceName;
    private Boolean serverDeviceActive;
    private String serverIpAddress;
    private String serverPort;
    private String serverWsdl;

    public ASServerObject() {
        serverDeviceId = UUID.randomUUID().toString();
        serverDeviceActive = true;
    }

    public String getServerDeviceId() {
        return serverDeviceId;
    }

    public String getServerDeviceName() {
        return serverDeviceName;
    }

    public void setServerDeviceName(String serverDeviceName) {
        this.serverDeviceName = serverDeviceName;
    }

    public Boolean getServerDeviceActive() {
        return serverDeviceActive;
    }

    public void setServerDeviceActive(Boolean serverDeviceActive) {
        this.serverDeviceActive = serverDeviceActive;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerWsdl() {
        return serverWsdl;
    }

    public void setServerWsdl(String serverWsdl) {
        this.serverWsdl = serverWsdl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ASServerObject)) {
            return false;
        }

        ASServerObject that = (ASServerObject) o;
        if (!serverDeviceId.equals(that.serverDeviceId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return serverDeviceId.hashCode();
    }

    @Override
    public int compareTo(ASServerObject o) {
        return serverDeviceId.compareTo(o.getServerDeviceId());
    }

}
