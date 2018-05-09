package data.forecast.encog;

import org.encog.ml.MLMethod;
import org.encog.ml.MLRegression;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

public class EncogPresistence {

    public static final String FILENAME = "forecastPrediction.eg";

    public void save(MLRegression method){
        EncogDirectoryPersistence.saveObject(new File(FILENAME), method);
    }

    public MLRegression loadNetwork(){
        return (MLRegression) EncogDirectoryPersistence.loadObject(new File(FILENAME));
    }

}
