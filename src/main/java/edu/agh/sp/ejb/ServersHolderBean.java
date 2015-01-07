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

    /**
     * Jesli AndroidSoapServer jest offline przez dlugi czas (5 minut) a nie wyslal komunikatu
     * "removeServer" bo na przyklad utracil polaczenie, to trzeba go usunac recznie (raz na 10 minut).
     */
    @Schedule(second="0", minute="*/10",hour="*", persistent=false)
    private void removeOfflineServers(){
        logger.log(Level.FINE, "START");
        DateTime compareTime = new DateTime().minusMinutes(5);

        Iterator<Map.Entry<String, ASServerObject>> iterator = serverObjectsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ASServerObject> entry = iterator.next();
            if(compareTime.isAfter(new DateTime(entry.getValue().getServerLastPing()))) {
                logger.log(Level.FINE, "Timeout. Removing device with token: " + entry.getValue().getServerLastPing());
                iterator.remove();
            }
        }

        logger.log(Level.FINE, "END");
    }
}