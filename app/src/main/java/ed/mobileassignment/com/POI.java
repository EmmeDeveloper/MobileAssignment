package ed.mobileassignment.com;

import java.util.HashMap;

/**
 * Created by marco on 23/02/2018.
 */

public class POI {
    private String address;
    private Double lat;
    private Double lng;
    private String imagePath;
    private String name;
    private HashMap<String, String[]> businessHours; //Day, Hours

    public POI (String name, String address,Double lat, Double lng, String imagePath, HashMap<String, String[]> businessHours) {
        this.address = address;
        this.lat = lng;
        this.lng = lng;
        this.imagePath = imagePath;
        this.name = name;
        this.businessHours = businessHours;
    }

    public String getAddress() {
        return address;
    }

    public Double getLng() {
        return lng;
    }

    public void Double(Double lng) {
        this.lng = lng;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String[]> getBusinessHours() {
        return businessHours;
    }
}


