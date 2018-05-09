package data.forecast;

import data.forecast.encog.EncogPresistence;
import org.encog.Encog;
import org.encog.ml.MLMethod;
import org.encog.ml.MLRegression;
import org.encog.ml.MLResettable;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.temporal.TemporalDataDescription;
import org.encog.ml.data.temporal.TemporalMLDataSet;
import org.encog.ml.data.temporal.TemporalPoint;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.factory.MLTrainFactory;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.RequiredImprovementStrategy;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import org.encog.util.csv.ReadCSV;
import org.encog.util.simple.EncogUtility;

import java.io.File;

public class ForecastPrediction {

    //TODO
    private static final File MYDIR = new File("D:\\NB24217\\Documents\\GitHub\\estagio");

    /**
     * This is the amount of data to use to predict.
     */
    private static final int INPUT_WINDOW_SIZE = 12;

    /**
     * This is the amount of data to actually predict.
     */
    private static final int PREDICT_WINDOW_SIZE = 1;

    /**
     * Used to normalize the temperature from a range of -60 -> 60
     * to 0-1.
     */
    private static NormalizedField normTemperature = new NormalizedField(
            NormalizationAction.Normalize, "temperature", 60, -60, 1, 0);

    /**
     * Used to normalize the dev from a range of 0-100
     * to 0-1.
     */
    private static NormalizedField normDEV = new NormalizedField(
            NormalizationAction.Normalize, "dev", 100, 0, 1, 0);

    private EncogPresistence presistence;

    public ForecastPrediction(){
        presistence = new EncogPresistence();
    }

    public static File getData(){
        return new File(MYDIR, "data.csv");
    }

    public static TemporalMLDataSet initDataSet(){
        TemporalMLDataSet dataSet = new TemporalMLDataSet(INPUT_WINDOW_SIZE, PREDICT_WINDOW_SIZE);

        TemporalDataDescription temperatureDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, true);
        dataSet.addDescription(temperatureDesc);

        TemporalDataDescription deviationDesc = new TemporalDataDescription(TemporalDataDescription.Type.RAW,true, false);
        dataSet.addDescription(deviationDesc);

        return dataSet;
    }

    public MLRegression trainModel(MLDataSet trainingData, String methodName, String methodArquitecture, String trainerName, String trainerArgs){
        MLMethodFactory methodFactory = new MLMethodFactory();
        MLMethod method = methodFactory.create(methodName, methodArquitecture, trainingData.getInputSize(), trainingData.getIdealSize());

        MLTrainFactory trainFactory = new MLTrainFactory();
        MLTrain train = trainFactory.create(method,trainingData,trainerName, trainerArgs);

        // reset if improve is less than 1% over 5 cycles
        if( method instanceof MLResettable && !(train instanceof ManhattanPropagation) ) {
            train.addStrategy(new RequiredImprovementStrategy(5000));
        }

        //Train the model
        EncogUtility.trainToError(train, 0.002);

        return (MLRegression) train.getMethod();

    }

    public static TemporalMLDataSet createTraining(File rawFile){
        TemporalMLDataSet trainingData = initDataSet();
        ReadCSV csv = new ReadCSV(rawFile.toString(),true, ' ');
        while (csv.next()){
            //Process csv

            /*
            TemporalPoint point = new TemporalPoint(trainingData
                    .getDescriptions().size());
            point.setSequence(sequenceNumber);
            point.setData(0, normTemperature.normalize(sunSpotNum) );
            point.setData(1, normDEV.normalize(dev) );
            trainingData.getPoints().add(point);
            */
        }
        csv.close();

        trainingData.generate();
        return trainingData;
    }

    //TODO -> need CSV with the informations about forecast
    public TemporalMLDataSet predict(File rawFile, MLRegression model){

        presistence.save(model);
        return null;
    }

    public void main(String[] args) {

        File rawFile = getData();

        TemporalMLDataSet trainingData = createTraining(rawFile);

        MLRegression model = presistence.loadNetwork() != null ? presistence.loadNetwork() : trainModel(
                trainingData,
                MLMethodFactory.TYPE_FEEDFORWARD,
                "?:B->SIGMOID->25:B->SIGMOID->?",
                MLTrainFactory.TYPE_RPROP,
                "");
        // Now predict
        predict(rawFile,model);

        Encog.getInstance().shutdown();

    }



}
