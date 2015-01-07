package edu.agh.sp.ejb;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.joda.time.DateTime;

import edu.agh.sp.model.ASServerObject;

/**
 * Created by Krzysztof Broncel on 06.01.2015
 */
@Singleton
public class ServersHolderBean {
	private static final Logger logger = Logger.getLogger(ServersHolderBean.class.getName());

	private Map<String, ASServerObject> serverObjectsMap;

	@PostConstruct
	private void init() {
		logger.log(Level.FINE, "START");
		serverObjectsMap = new ConcurrentHashMap<String, ASServerObject>();
		logger.log(Level.FINE, "END");
	}

	public Collection<ASServerObject> getServers() {
		return serverObjectsMap.values();
	}

	public void addServer(ASServerObject serverObject) {
		serverObjectsMap.put(serverObject.getServerDeviceId(), serverObject);
	}

	public void updatePingTimeForServer(String serverId, DateTime pingTime) {
		ASServerObject asServerObject = serverObjectsMap.get(serverId);
		if (asServerObject != null) {
			asServerObject.setServerLastPing(pingTime);
		}
	}

	public void removeServer(String serverId) {
		serverObjectsMap.remove(serverId);
	}

	public ASServerObject getServerForToken(String token) {
		return serverObjectsMap.get(token);
	}
}