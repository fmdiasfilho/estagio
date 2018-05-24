package search;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import java.util.LinkedList;
import java.util.List;

public class Search {

    private static final String USERNAME = "2275a3a9-930a-4967-8a17-d62049caeb5c";
    private static final String PASSWORD = "zjT4Mb5afGv2";
    private static final String VERSION = "2018-03-16";

    public static void analyze(String text) {
        NaturalLanguageUnderstanding service = createConnection();

        EntitiesOptions entities = new EntitiesOptions.Builder()
                .sentiment(true)
                .build();

        Features features = new Features.Builder()
                .entities(entities)
                .build();

        AnalyzeOptions parameters;
        if(text.contains("www")){
            parameters = new AnalyzeOptions.Builder()
                .url(text)
                .features(features)
                .build();
        }else{
            parameters = new AnalyzeOptions.Builder()
                    .text(text)
                    .features(features)
                    .build();
        }

        AnalysisResults response = service
                .analyze(parameters)
                .execute();

        List<String> names = new LinkedList<>();
        response.getEntities().forEach(entity -> {
            if (entity.getType().equalsIgnoreCase("Company")) {
                names.add(entity.getText());
            }
        });

        List<String> output = new LinkedList<>();
        names.forEach(name -> {
            boolean matches = false;
            String[] split = name.split(" ");
            for (String piece : split) {
                for (String out : output) {
                    if (out.matches("(?i).*" + piece + ".*")) {
                        matches = true;
                        break;
                    }
                }
                if (!matches) {
                    output.add(name);
                }
                if(matches){
                    break;
                }
            }
        });

        output.forEach(out -> {
            System.out.println(out);
        });
    }

    private static NaturalLanguageUnderstanding createConnection() {
        return new NaturalLanguageUnderstanding(VERSION, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        Search.analyze("http://www.bit.pt/empresas-do-grupo-novabase-lideram-ranking-de-inovacao-em-servicos/");
    }

}
