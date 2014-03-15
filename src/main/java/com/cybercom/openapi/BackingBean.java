package com.cybercom.openapi;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
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
@ViewScoped
public class BackingBean implements Serializable {
    
    private double lat;
    private double lng;
    private List<MusicEvent> events = new ArrayList<MusicEvent>();
    private MusicEvent selectedEvent;

    private static final Logger LOG = Logger.getLogger(BackingBean.class.getName());
    private String get;

    public void onPointSelect(PointSelectEvent event) {
        selectedEvent = new MusicEvent();
        selectedEvent.setArtist("Thore Skogman");
        lat = event.getLatLng().getLat();
        lng = event.getLatLng().getLng();
        
        String url = "http://ws.audioscrobbler.com/2.0/?method=geo.getEvents&format=json&"
                + "api_key=4cbe1f4e4c84d5d9f0b923a41c5bac41&long=" + lng + "&lat=" + lat;

        System.out.println(url);

        Client client = ClientBuilder.newClient();
        get = client.target(url).request(MediaType.APPLICATION_JSON).get(String.class);

        //System.out.println(get);

        JsonParser parser = Json.createParser(new StringReader(get));
        boolean isArtist = false;
        
        while (parser.hasNext()) {
            JsonParser.Event next = parser.next();
            MusicEvent evt = new MusicEvent();
            
            switch (next) {
                
                case KEY_NAME: {
                    if(parser.getString().equals("artist")) {
                        System.out.print(parser.getString() + "=");
                        isArtist = true;
                    } 
                    break;
                }
                case VALUE_STRING: {
                    if(isArtist) {
                        System.out.print(parser.getString());
                        evt.setArtist(parser.getString());
                        evt.setEventId(UUID.randomUUID().toString());
                         events.add(evt);
                        isArtist = false;
                    }
                    break;
                }
            }
        }

    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<MusicEvent> getEvents() {
        System.out.println("events size" +events.size());
        return events;
    }

    public void setEvents(List<MusicEvent> events) {
        this.events = events;
    }

    public MusicEvent getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(MusicEvent selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }
    
    
}
