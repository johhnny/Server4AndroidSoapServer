package edu.agh.sp.jsf;

import edu.agh.sp.ejb.ServersHolderBean;
import edu.agh.sp.model.ASServerObject;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Krzysztof Broncel
 * on 05.01.2015
 */
@ViewScoped
@ManagedBean(name = "serversView")
public class ServersViewMBean implements Serializable {
    private static final Logger logger = Logger.getLogger(ServersViewMBean.class.getName());

    @EJB
    private ServersHolderBean serversHolderBean;

    private Set<ASServerObject> asServerObjects;

    private ASServerObject selectedServerObject;

    @PostConstruct
    private void init() {
        logger.log(Level.FINE, "START");

        asServerObjects = serversHolderBean.getServers();

        logger.log(Level.FINE, "END");
    }

    public Set<ASServerObject> getAsServerObjects() {
        return asServerObjects;
    }

    public void setAsServerObjects(Set<ASServerObject> asServerObjects) {
        this.asServerObjects = asServerObjects;
    }

    public ASServerObject getSelectedServerObject() {
        return selectedServerObject;
    }

    public void setSelectedServerObject(ASServerObject selectedServerObject) {
        this.selectedServerObject = selectedServerObject;
    }

    public ServersHolderBean getServersHolderBean() {
        return serversHolderBean;
    }

    public void setServersHolderBean(ServersHolderBean serversHolderBean) {
        this.serversHolderBean = serversHolderBean;
    }
}
