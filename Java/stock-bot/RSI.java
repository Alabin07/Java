import java.util.List;
import java.util.ArrayList;

public class RSI {

    private int period;
    private List<Float> stockPrices;

    public RSI(int period, List<Float> stockPrices) {
        this.period = period;
        this.stockPrices = stockPrices;
    }

    public List<Float> RSICalculator() {

        List<Float> rsiValues = new ArrayList<>();

        if (stockPrices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data to calculate RSI");
        }

        List<Float> gains = new ArrayList<>();
        List<Float> losses = new ArrayList<>();

        // Calculate gains and losses
        for (int i = 1; i < stockPrices.size(); i++) {
            float change = stockPrices.get(i) - stockPrices.get(i - 1);
            if (change > 0) {
                gains.add(change);
                losses.add(0f);
            } else {
                gains.add(0f);
                losses.add(Math.abs(change));
            }
        }

        // Calculate initial average gain and loss
        float avgGain = 0, avgLoss = 0;
        for (int i = 0; i < period; i++) {
            avgGain += gains.get(i);
            avgLoss += losses.get(i);
        }
        avgGain /= period;
        avgLoss /= period;

        // Calculate RSI for values
        for (int i = period; i < gains.size(); i++) {
            avgGain = ((avgGain * (period - 1)) + gains.get(i)) / period;
            avgLoss = ((avgLoss * (period - 1)) + losses.get(i)) / period;

            float rs = avgLoss == 0 ? 100 : avgGain / avgLoss;
            float rsi = 100 - (100 / (1 + rs));

            rsiValues.add(rsi);
        }
        return (rsiValues);
    }
}
