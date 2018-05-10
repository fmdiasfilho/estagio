package data.weatherRepository;

import com.google.gson.Gson;
import com.weatherlibraryjava.RequestBlocks;
import com.weatherlibraryjava.RequestBuilder;
import data.weatherRepository.historyRequests.HistoryRequestBuilder;
import data.weatherRepository.historyRequests.MyRequestBlocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyRepository {

    private String APIURL = "http://api.apixu.com/v1";
    private String weatherModel;
    String url = "";
    Gson gson = new Gson();

    public MyRepository() {
    }

    public String GetWeatherData(String key, RequestBlocks.GetBy getBy, String value, RequestBlocks.Days ForecastOfDays) throws Exception {
        this.url = this.APIURL + RequestBuilder.PrepareRequest(RequestBlocks.MethodType.Forecast, key, getBy, value, ForecastOfDays);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    public String GetWeatherDataByLatLong(String key, String latitude, String longitude, RequestBlocks.Days ForecastOfDays) throws Exception {
        this.url = this.APIURL + RequestBuilder.PrepareRequestByLatLong(RequestBlocks.MethodType.Forecast, key, latitude, longitude, ForecastOfDays);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    public String GetWeatherDataByAutoIP(String key, RequestBlocks.Days ForecastOfDays) throws Exception {
        this.url = this.APIURL + RequestBuilder.PrepareRequestByAutoIP(RequestBlocks.MethodType.Forecast, key, ForecastOfDays);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    public String GetWeatherData(String key, RequestBlocks.GetBy getBy, String value) throws Exception {
        this.url = this.APIURL + RequestBuilder.PrepareRequest(RequestBlocks.MethodType.Current, key, getBy, value);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    public String GetWeatherDataByLatLong(String key, String latitude, String longitude) throws Exception {
        this.url = this.APIURL + RequestBuilder.PrepareRequestByLatLong(RequestBlocks.MethodType.Current, key, latitude, longitude);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    public String GetWeatherDataByAutoIP(String key) throws Exception {
        this.url = this.APIURL + RequestBuilder.PrepareRequestByAutoIP(RequestBlocks.MethodType.Current, key);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    public String GetWeatherDataByHistory(String key, RequestBlocks.GetBy getBy, String value, String date) throws Exception {
        this.url = this.APIURL + HistoryRequestBuilder.PrepareHistoryRequest(MyRequestBlocks.MethodType.History, key, getBy, value, date);
        System.out.println("url==========>" + this.url);
        String tryString = this.GetData(this.url);
        return tryString;
    }

    private String GetData(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "user");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
