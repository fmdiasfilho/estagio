package data.weatherRepository.historyRequests;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.google.gson.annotations.SerializedName;
import com.weatherlibrary.datamodel.Current;
import com.weatherlibrary.datamodel.Forecast;
import com.weatherlibrary.datamodel.Location;
import data.weatherRepository.MyCurrentClass;

import java.io.Serializable;

public class MyWeatherModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("location")
    public Location location;
    @SerializedName("current")
    public MyCurrentClass current;
    @SerializedName("forecast")
    public Forecast forecast;

    public MyWeatherModel() {
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location mLocation) {
        this.location = mLocation;
    }

    public MyCurrentClass getCurrent() {
        return this.current;
    }

    public void setCurrent(MyCurrentClass mCurrent) {
        this.current = mCurrent;
    }

    public Forecast getForecast() {
        return this.forecast;
    }

    public void setForecast(Forecast mForecast) {
        this.forecast = mForecast;
    }
}
