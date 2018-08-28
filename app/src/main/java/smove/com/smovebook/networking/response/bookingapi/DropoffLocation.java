package smove.com.smovebook.networking.response.bookingapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Manuramv on 8/28/2018.
 */

class DropoffLocation {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;

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
}
