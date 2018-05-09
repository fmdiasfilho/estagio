package data.weatherRepository.historyRequests;

import com.weatherlibraryjava.RequestBlocks;

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

            public static String getHistory(){ return "/history.json";}

            public static String GetParameters(MethodType methodType) throws Exception {
                String methodPara = "";
                switch(methodType.ordinal()){
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

        public static class ReqestFor {
            public ReqestFor() {
            }

            public static String City(String cityName) {
                return "q=" + cityName;
            }

            public static String Zip(String zip) {
                return "q=" + zip;
            }

            public static String PostCode(String postcode) {
                return "q=" + postcode;
            }

            public static String PostalCode(String postalCode) {
                return "q=" + postalCode;
            }

            public static String LatLong(String latitude, String longitude) {
                return "q=" + latitude + "," + longitude;
            }

            public static String Metar(String metar) {
                return "q=metar:" + metar;
            }

            public static String iata(String iata) {
                return "q=iata:" + iata;
            }

            public static String AutoIP() {
                return "q=auto:ip";
            }

            public static String IPAddress(String IP) {
                return "q=" + IP;
            }

            public static String PrepareQueryParameter(com.weatherlibraryjava.RequestBlocks.GetBy getby, String value) {
                String queryParameter = "";
                switch(getby.ordinal()) {
                    case 1:
                        queryParameter = City(value);
                        break;
                    case 2:
                        queryParameter = Zip(value);
                        break;
                    case 3:
                        queryParameter = PostCode(value);
                        break;
                    case 4:
                        queryParameter = PostalCode(value);
                        break;
                    case 5:
                        queryParameter = Metar(value);
                        break;
                    case 6:
                        queryParameter = iata(value);
                        break;
                    case 7:
                        queryParameter = IPAddress(value);
                }

                return queryParameter;
            }

            public static String PrepareDays(com.weatherlibraryjava.RequestBlocks.Days days) {
                return "days=" + days;
            }
        }
    }
