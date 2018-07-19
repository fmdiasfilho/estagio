package data.weatherRepository.historyRequests;

/**
 * @author Pedro Feiteira, n48119
 * Used to create the Apixu request
 * For more information watch -> https://www.apixu.com/api-explorer.aspx
 */
public class MyRequestBlocks {


    public MyRequestBlocks() {
    }

    public enum Days {
        One,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten;

        Days() {
        }
    }

    public enum GetBy {
        CityName,
        Zip,
        PostCode,
        PostalCode,
        Metar,
        iata,
        IPAddress;

        GetBy() {
        }
    }

    public enum MethodType {
        Current,
        Forecast,
        History;

        MethodType() {
        }
    }

    public static class MethodTypeParemeters {
        public MethodTypeParemeters() {
        }

        public static String getCurrent() {
            return "/current.json";
        }

        public static String getForecast() {
            return "/forecast.json";
        }

        public static String getHistory() {
            return "/history.json";
        }

        public static String GetParameters(MethodType methodType) throws Exception {
            String methodPara = "";
            switch (methodType.ordinal()) {
                case 1:
                    methodPara = getCurrent();
                    break;
                case 2:
                    methodPara = getForecast();
                case 3:
                    methodPara = getHistory();
                    break;
            }

            if (!methodPara.equals("") && methodPara != null) {
                return methodPara;
            } else {
                throw new Exception("Invalid Method Type");
            }
        }
    }
}
