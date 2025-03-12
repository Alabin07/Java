import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PriceFetcher {

    private String csvFilePath;
    private int day;


    public PriceFetcher(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<Float> getStockPrices() {
        List<Float> stockPrices = new ArrayList<>();
        String line;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                if (data.length >= 3) {
                    String priceString = data[1].replace("$", "").trim();
                    stockPrices.add(Float.parseFloat(priceString));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockPrices;
    }
}
