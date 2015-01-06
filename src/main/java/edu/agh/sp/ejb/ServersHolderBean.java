package edu.agh.sp.ejb;

import edu.agh.sp.model.ASServerObject;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Krzysztof Broncel
 * on 06.01.2015
 */
@Singleton
public class ServersHolderBean {
    private static final Logger logger = Logger.getLogger(ServersHolderBean.class.getName());

    private Set<ASServerObject> serverObjectSet;

    @PostConstruct
    private void init() {
        logger.log(Level.FINE, "START");

        serverObjectSet = new LinkedHashSet<>();
        Random r = new Random();

        for (int i = 0; i < 5; i++) {
            ASServerObject o = new ASServerObject();
            o.setServerDeviceId(UUID.randomUUID().toString());
            o.setServerDeviceName("Super android lololo " + o.getServerDeviceId().hashCode());
            o.setServerDeviceActive(r.nextBoolean());
            o.setServerIpAddress(r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256));
            serverObjectSet.add(o);
        }

        logger.log(Level.FINE, "END");
    }

    public Set<ASServerObject> getServers() {
        return serverObjectSet;
    }

    public boolean addServer(ASServerObject serverObject) {
        logger.log(Level.FINE, serverObject.getServerDeviceId());
        return serverObjectSet.add(serverObject);
    }
}
