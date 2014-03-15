package com.cybercom.openapi;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.map.PointSelectEvent;

/**
 *
 * @author oope
 */
@Named
@RequestScoped
public class BackingBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(BackingBean.class.getName());
    private String get;

    public void onPointSelect(PointSelectEvent event) {
        double lat = event.getLatLng().getLat();
        double lng = event.getLatLng().getLng();

        String url = "http://ws.audioscrobbler.com/2.0/?method=geo.getEvents&format=json&"
                + "api_key=4cbe1f4e4c84d5d9f0b923a41c5bac41&long=" + lng + "&lat=" + lat;

        System.out.println(url);

        Client client = ClientBuilder.newClient();
        get = client.target(url).request(MediaType.APPLICATION_JSON).get(String.class);

        System.out.println(get);
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

}
