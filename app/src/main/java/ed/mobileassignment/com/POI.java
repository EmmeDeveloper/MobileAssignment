package ed.mobileassignment.com;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.HashMap;

/**
 * Created by marco on 23/02/2018.
 */

@Parcel
public class POI {

    // Dichiaro public e non private per evitare di usare la reflection della libreria Parcels
    public String address;
    public Double lat;
    public Double lng;
    public String imagePath;
    public String name;
    public String description;
    public HashMap<String, String[]> businessHours; //Day, Hours

    public String getDescription() {
        return description;
    }

    @ParcelConstructor
    public POI (String name, String address,Double lat, Double lng, String imagePath, String description, HashMap<String, String[]> businessHours) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.imagePath = imagePath;
        this.name = name;
        this.businessHours = businessHours;
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
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


