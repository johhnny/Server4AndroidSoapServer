package edu.agh.sp.model;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Krzysztof Broncel
 * on 05.01.2015
 */
public class ASServerObject implements Serializable, Comparable<ASServerObject> {
    /* UUID */
    private String serverDeviceId;
    /* Last ping */
    private DateTime serverLastPing;
    /* Is device active? */
    private Boolean serverDeviceActive;
    /* Android device IP address */
    private String serverIpAddress;
    /* Android device port */
    private String serverPort;
    /* WSDL copy */
    private String serverWsdlCopy;
    /* WSDL link */
    private String serverWsdlExternalLink;
    /* List of android server methods from WSDL */
    private List<String> serverMethodsList;
    /* TODO - czy bedzie potrzebne? */
    private String serverDeviceName;

    public ASServerObject() {
        serverDeviceId = UUID.randomUUID().toString();
        serverDeviceActive = true;
        serverLastPing = new DateTime();
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

    public String getServerWsdlCopy() {
        return serverWsdlCopy;
    }

    public void setServerWsdlCopy(String serverWsdlCopy) {
        this.serverWsdlCopy = serverWsdlCopy;
    }

    public String getServerWsdlExternalLink() {
        return serverWsdlExternalLink;
    }

    public void setServerWsdlExternalLink(String serverWsdlExternalLink) {
        this.serverWsdlExternalLink = serverWsdlExternalLink;
    }

    public DateTime getServerLastPing() {
        return serverLastPing;
    }

    public void setServerLastPing(DateTime serverLastPing) {
        this.serverLastPing = serverLastPing;
    }

    public List<String> getServerMethodsList() {
        return serverMethodsList;
    }

    public void setServerMethodsList(List<String> serverMethodsList) {
        this.serverMethodsList = serverMethodsList;
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
