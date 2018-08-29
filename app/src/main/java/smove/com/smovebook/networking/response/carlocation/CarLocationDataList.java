package smove.com.smovebook.networking.response.carlocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Manuramv on 8/29/2018.
 */

public class CarLocationDataList implements Serializable{
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("is_on_trip")
    @Expose
    private Boolean isOnTrip;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Boolean getIsOnTrip() {
        return isOnTrip;
    }

    public void setIsOnTrip(Boolean isOnTrip) {
        this.isOnTrip = isOnTrip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
