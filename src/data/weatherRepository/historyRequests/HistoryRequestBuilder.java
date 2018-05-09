package data.weatherRepository.historyRequests;

import com.weatherlibraryjava.RequestBlocks;
import com.weatherlibraryjava.RequestBuilder;

import java.util.Date;

public class HistoryRequestBuilder extends RequestBuilder {

    public static String PrepareHistoryRequest(MyRequestBlocks.MethodType methodType, String key, RequestBlocks.GetBy getBy, String value, String date) throws Exception {
        return MyRequestBlocks.MethodTypeParemeters.GetParameters(methodType) + "?key=" + key + "&" + RequestBlocks.ReqestFor.PrepareQueryParameter(getBy, value) + "&dt=" + date;
    }

}
