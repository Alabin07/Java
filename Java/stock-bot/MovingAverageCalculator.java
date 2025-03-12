import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class MovingAverageCalculator {
    private List<Float> stockPrices;
    private int averageLength;


    // Constructor to initialize values
    public MovingAverageCalculator(List<Float> stockPrices, int averageLength) {
        this.stockPrices = stockPrices;
        this.averageLength = averageLength;
    }

    // Method to calculate Simple Moving Average (SMA)
    public List<Float> calculateSMA() {
        List<Float> smaValues = new ArrayList<>();

        // ensures enough data for calculation
        if (stockPrices.size() < averageLength)
        {
            return (smaValues);
        }


        // goes through List of prices and calculates SMA for every data input
        for (int day = 0; day <= stockPrices.size() - averageLength; day++) {
            float sum = 0;
            for (int i = 0; i < averageLength; i++) {
                sum += stockPrices.get(day + i);
            }
            smaValues.add(sum / averageLength);
        }

        return smaValues;
    }
}
