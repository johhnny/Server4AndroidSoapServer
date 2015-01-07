package edu.agh.sp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Created by Krzysztof Broncel on 05.01.2015
 */
public class ASServerObject implements Serializable, Comparable<ASServerObject> {

	private static final long serialVersionUID = 6061172966827531631L;

	/* UUID */
	private final String serverDeviceId;
	/* Last ping */
	private DateTime serverLastPing;
	/* Is device active? */
	private Boolean serverDeviceActive;
	/* Android device IP address */
	private final String serverIpAddress;
	/* Android device port */
	private final Integer serverPort;
	/* WSDL copy */
	private final String serverWsdlCopy;
	/* WSDL link */
	private final String serverWsdlExternalLink;
	/* List of android server methods from WSDL */
	private final List<String> serverMethodsList = new ArrayList<String>();

	public ASServerObject(String serverDeviceId, DateTime serverLastPing, String serverIpAddress, Integer serverPort, String serverWsdlCopy) {
		this.serverDeviceId = serverDeviceId;
		this.serverLastPing = serverLastPing;
		this.serverIpAddress = serverIpAddress;
		this.serverPort = serverPort;
		this.serverWsdlCopy = serverWsdlCopy;
		this.serverDeviceActive = true;
		this.serverWsdlExternalLink = "http://" + serverIpAddress + ":" + serverPort + "/?wsdl";
	}

	public String getServerDeviceId() {
		return serverDeviceId;
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

	public Integer getServerPort() {
		return serverPort;
	}

	public String getServerWsdlCopy() {
		return serverWsdlCopy;
	}

	public String getServerWsdlExternalLink() {
		return serverWsdlExternalLink;
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

	public void addToServerMethodsList(String serverMethod) {
		if (serverMethod != null && !"".equals(serverMethod)) {
			this.serverMethodsList.add(serverMethod);
		}
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
