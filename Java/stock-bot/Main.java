import java.util.List;


public class Main {
    public static void main(String[] args) {
        int averageLength = 2;

        PriceFetcher fetcher = new PriceFetcher("historical_data/HistoricalData_1740846227402.csv");
        List<Float> values = fetcher.getStockPrices();

        MovingAverageCalculator calculator = new MovingAverageCalculator(values, averageLength);
        RSI rsi = new RSI(30, values);

        Simulator simulator = new Simulator(values, rsi.RSICalculator(), calculator.calculateSMA(), 1, 10000, 1, 10, 1);

        simulator.calculator();


    }
}
