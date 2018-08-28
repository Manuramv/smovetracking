package smove.com.smovebook.networking.response.bookingapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Manuramv on 8/28/2018.
 */

class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("available_cars")
    @Expose
    private Integer availableCars;
    @SerializedName("dropoff_locations")
    @Expose
    private List<DropoffLocation> dropoffLocations = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Integer getAvailableCars() {
        return availableCars;
    }

    public void setAvailableCars(Integer availableCars) {
        this.availableCars = availableCars;
    }

    public List<DropoffLocation> getDropoffLocations() {
        return dropoffLocations;
    }

    public void setDropoffLocations(List<DropoffLocation> dropoffLocations) {
        this.dropoffLocations = dropoffLocations;
    }
}
