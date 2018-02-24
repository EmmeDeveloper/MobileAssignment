package ed.mobileassignment.com;

import java.util.HashMap;

/**
 * Created by marco on 23/02/2018.
    Utilizzo il builder pattern sui POI per fare i controlli di base, così da evitare di toccare i getter e i setter della classe base
 */

public class POIBuilder {

    private String address;
    private Double lat;
    private Double lng;
    private String imagePath;
    private String name;
    private String description;
    private HashMap<String, String[]> businessHours; //Day, Hours

    public POI build() {
        return new POI(name, address, lat ,lng, imagePath, description, businessHours);
    }

    public  POIBuilder name (String name) {
        if (name.isEmpty()) name = "Undefined";
        this.name = name;
        return this;
    }

    public  POIBuilder address (String address) {
        if (address.isEmpty()) address = "Undefined";
        this.address = address;
        return this;
    }
    public  POIBuilder lat (Double lat) {
        this.lat = lat;
        return this;
    }

    public POIBuilder description (String desc) {
        this.description = desc;
        return this;
    }

    public  POIBuilder lng (Double lng) {
        this.lng = lng;
        return this;
    }
    public  POIBuilder imagePath (String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
    public  POIBuilder businessHours (HashMap<String, String[]> businessHours) {
        for(HashMap.Entry<String, String[]> entry : businessHours.entrySet()) {
            String day = entry.getKey();
            String[] turni = entry.getValue();
            if (turni[0].isEmpty()) turni[0] = "Closed";
            if (turni[1].isEmpty()) turni[1] = "Closed";
        }
        this.businessHours = businessHours;
        return this;
    }

}
