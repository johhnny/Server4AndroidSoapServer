package edu.agh.sp.model;

import java.io.Serializable;

/**
 * Created by Krzysztof Broncel
 * on 05.01.2015
 * TODO
 */
public class ASServerObject implements Serializable, Comparable<ASServerObject> {
    private String serverDeviceId;
    private String serverDeviceName;
    private Boolean serverDeviceActive;
    private String serverIpAddress;

    public String getServerDeviceId() {
        return serverDeviceId;
    }

    public void setServerDeviceId(String serverDeviceId) {
        this.serverDeviceId = serverDeviceId;
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
