package edu.agh.sp.ejb;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import edu.agh.sp.model.ASServerObject;
import org.joda.time.DateTime;

/**
 * Created by Krzysztof Broncel on 06.01.2015
 */
@Singleton
public class ServersHolderBean {
	private static final Logger logger = Logger.getLogger(ServersHolderBean.class.getName());

	private static final int TIME_PERIOD_INACTIVE = 30;
	private static final int TIME_PERIOD_OFFLINE = 60;

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

	public void updatePingTimeForServer(String serverId, Date pingTime) {
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

    @Schedule(second="0/10", minute="*",hour="*", persistent=false)
    private void updateServersActivity(){
        logger.log(Level.FINE, "START");

        DateTime inactiveTime = new DateTime().minusSeconds(TIME_PERIOD_INACTIVE);
		DateTime offlineTime = new DateTime().minusSeconds(TIME_PERIOD_OFFLINE);

        Iterator<Map.Entry<String, ASServerObject>> iterator = serverObjectsMap.entrySet().iterator();
        while (iterator.hasNext()) {
			ASServerObject serverObject = iterator.next().getValue();
			DateTime serverLastPing = new DateTime(serverObject.getServerLastPing());

			serverObject.setServerActive(inactiveTime.isBefore(serverLastPing));
			if(!serverObject.getServerActive() && offlineTime.isAfter(serverLastPing)) {
                logger.log(Level.INFO, "Timeout. Removing device with token: " + serverObject.getServerDeviceId());
                iterator.remove();
            }
        }

        logger.log(Level.FINE, "END");
    }
}