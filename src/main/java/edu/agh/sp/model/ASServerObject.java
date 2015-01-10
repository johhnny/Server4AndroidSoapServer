package edu.agh.sp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysztof Broncel on 05.01.2015
 */
public class ASServerObject implements Serializable, Comparable<ASServerObject> {

	private static final long serialVersionUID = 6061172966827531631L;

	/* UUID */
	private final String serverDeviceId;
	/* Last ping */
	private Date serverLastPing;
	/* Is server marked as 'active'? */
	private Boolean serverActive;
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

	public ASServerObject(String serverDeviceId, Date serverLastPing, String serverIpAddress, Integer serverPort, String serverWsdlCopy) {
		this.serverDeviceId = serverDeviceId;
		this.serverLastPing = serverLastPing;
		this.serverIpAddress = serverIpAddress;
		this.serverPort = serverPort;
		this.serverWsdlCopy = serverWsdlCopy;
		this.serverWsdlExternalLink = "http://" + serverIpAddress + ":" + serverPort + "/?wsdl";
		this.serverActive = true;
	}

	public String getServerDeviceId() {
		return serverDeviceId;
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

	public Date getServerLastPing() {
		return serverLastPing;
	}

	public void setServerLastPing(Date serverLastPing) {
		this.serverLastPing = serverLastPing;
	}

	public List<String> getServerMethodsList() {
		return serverMethodsList;
	}

	public Boolean getServerActive() {
		return serverActive;
	}

	public void setServerActive(Boolean serverActive) {
		this.serverActive = serverActive;
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
