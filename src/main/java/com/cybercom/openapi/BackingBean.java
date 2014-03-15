package com.cybercom.openapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.primefaces.event.map.PointSelectEvent;

/**
 *
 * @author oope
 */
@Named
@RequestScoped
public class BackingBean implements Serializable {

    public void onPointSelect(PointSelectEvent event) {
        double lat = event.getLatLng().getLat();
        double lng = event.getLatLng().getLng();

        String url = "http://ws.audioscrobbler.com/2.0/?method=geo.getEvents&format=json&"
                + "api_key=4cbe1f4e4c84d5d9f0b923a41c5bac41&long=" + lng + "&lat=" + lat;

        System.out.println(url);

        Client client = ClientBuilder.newClient();
        String get = client.target(url).request(MediaType.APPLICATION_JSON).get(String.class);
        
        System.out.println(get);

        //JsonElement jelement = new JsonParser().parse(get);
        
        //TODO

    }

}
