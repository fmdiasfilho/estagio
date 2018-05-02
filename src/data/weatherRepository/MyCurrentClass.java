package data.weatherRepository;

import com.google.gson.annotations.SerializedName;
import com.weatherlibrary.datamodel.Condition;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MyCurrentClass implements Serializable {

        private static final long serialVersionUID = 1L;
        @SerializedName("last_updated_epoch")
        public int last_updated_epoch;
        @SerializedName("wind_degree")
        public int wind_degree;
        @SerializedName("humidity")
        public int humidity;
        @SerializedName("cloud")
        public int cloud;
        @SerializedName("last_updated")
        public String last_updated;
        @SerializedName("wind_dir")
        public String wind_dir;
        @SerializedName("temp_c")
        public double temp_c;
        @SerializedName("temp_f")
        public double temp_f;
        @SerializedName("wind_mph")
        public double wind_mph;
        @SerializedName("wind_kph")
        public double wind_kph;
        @SerializedName("pressure_mb")
        public double pressure_mb;
        @SerializedName("pressure_in")
        public double pressure_in;
        @SerializedName("precip_mm")
        public double precip_mm;
        @SerializedName("precip_in")
        public double precip_in;
        @SerializedName("feelslike_c")
        public double feelslike_c;
        @SerializedName("feelslike_f")
        public double feelslike_f;
        Condition condition = new Condition();

        public MyCurrentClass() {
        }

        public int getLastUpdateEpoch() {
            return this.last_updated_epoch;
        }

        public void setLastUpdateEpoch(int mLastUpdateEpoch) {
            this.last_updated_epoch = mLastUpdateEpoch;
        }

        public String getLastUpdated() {
            return this.last_updated;
        }

        public void setLastUpdated(String mLastUpdated) {
            this.last_updated = mLastUpdated;
        }

        public double getTempC() {
            return this.temp_c;
        }

        public void setTempC(Double mTempC) {
            this.temp_c = mTempC;
        }

        public double getTempF() {
            return this.temp_f;
        }

        public void setTempF(Double mTempF) {
            this.temp_f = mTempF;
        }

        public Condition getCondition() {
            return this.condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public double getWindMph() {
            return this.wind_mph;
        }

        public void setWindMph(double mWindMph) {
            this.wind_mph = mWindMph;
        }

        public double getWindKph() {
            return this.wind_kph;
        }

        public void setWindKph(double mWindKph) {
            this.wind_kph = mWindKph;
        }

        public int getWindDegree() {
            return this.wind_degree;
        }

        public void setWindDegree(int mWindDegree) {
            this.wind_degree = mWindDegree;
        }

        public String getWindDir() {
            return this.wind_dir;
        }

        public void setWindDir(String mWindDir) {
            this.wind_dir = mWindDir;
        }

        public double getPressureMb() {
            return this.pressure_mb;
        }

        public void setPressureMb(double mPressureMb) {
            this.pressure_mb = mPressureMb;
        }

        public double getPressureIn() {
            return this.pressure_in;
        }

        public void setPressureIn(double mPressureIn) {
            this.pressure_in = mPressureIn;
        }

        public double getPrecipMm() {
            return this.precip_mm;
        }

        public void setPrecipMm(double mPrecipMm) {
            this.precip_mm = mPrecipMm;
        }

        public double getPrecipIn() {
            return this.precip_in;
        }

        public void setPrecipIn(double mPrecipIn) {
            this.precip_in = mPrecipIn;
        }

        public int getHumidity() {
            return this.humidity;
        }

        public void setHumidity(int mHumidity) {
            this.humidity = mHumidity;
        }

        public int getCloud() {
            return this.cloud;
        }

        public void setCloud(int mCloud) {
            this.cloud = mCloud;
        }

        public double getFeelslikeC() {
            return this.feelslike_c;
        }

        public void setFeelslikeC(double mFeelslikeC) {
            this.feelslike_c = mFeelslikeC;
        }

        public double getFeelslikeF() {
            return this.feelslike_f;
        }

        public void setFeelslikeF(double mFeelslikeF) {
            this.feelslike_f = mFeelslikeF;
        }
    }
