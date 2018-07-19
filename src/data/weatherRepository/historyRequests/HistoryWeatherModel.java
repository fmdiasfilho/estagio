package data.weatherRepository.historyRequests;

import com.google.gson.annotations.SerializedName;
import com.weatherlibrary.datamodel.Forecast;
import com.weatherlibrary.datamodel.Location;

import java.io.Serializable;

/**
 * @author Pedro Feiteira, n48119
 * This class is used as GSON conversion
 */
public class HistoryWeatherModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("location")
    public Location location;
    @SerializedName("forecast")
    public Forecast forecast;

    public HistoryWeatherModel() {
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location mLocation) {
        this.location = mLocation;
    }

    public Forecast getForecast() {
        return this.forecast;
    }

    public void setForecast(Forecast mForecast) {
        this.forecast = mForecast;
    }
}

