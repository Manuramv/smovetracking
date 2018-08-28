package smove.com.smovebook.networking.response.carlocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Manuramv on 8/29/2018.
 */

public class GetCarLocationResponse {
    @SerializedName("data")
    @Expose
    private List<CarLocationDataList> data = null;

    public List<CarLocationDataList> getData() {
        return data;
    }

    public void setData(List<CarLocationDataList> data) {
        this.data = data;
    }
}

