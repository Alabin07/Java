import java.util.List;

public class Simulator {

    private List<Float> stockPrices;
    private List<Float> rsi;
    private List<Float> sma;

    private int buyInterval;
    private float money;
    private int startDay;
    private float stockAmount;
    private float risk;

    private float stopLossPercentage = 0.1f;    // 10% loss triggers stop-loss
    private float takeProfitPercentage = 0.2f; // 20% profit triggers take-profit
    private float tradePercentage = 0.1f;      // 10% of portfolio traded each time

    public Simulator(List<Float> stockPrices, List<Float> rsi, List<Float> sma, int buyInterval, float money, int startDay, float stockAmount, float risk) {
        this.stockPrices = stockPrices;
        this.rsi = rsi;
        this.sma = sma;
        this.buyInterval = buyInterval;
        this.money = money;
        this.startDay = Math.max(startDay, rsi.size()); // Align start with RSI size
        this.stockAmount = stockAmount;
        this.risk = risk;
    }

    public void calculator() {
        float entryPrice = -1;

        for (int i = startDay; i < stockPrices.size(); i += buyInterval) {
            float currentPrice = stockPrices.get(i);
            float currentRSI = rsi.get(i - startDay);
            float currentSMA = sma.get(i - startDay);

            if (currentRSI < 50 && currentSMA < currentPrice) {
                buy(currentPrice);
                entryPrice = currentPrice;
            }
            else if (currentRSI > 60 && currentSMA > currentPrice) {
                sell(currentPrice);
            }
            else if (entryPrice > 0) {
                checkRiskManagement(currentPrice, entryPrice);
            }
            else {
                doNothing();
            }

            printPortfolioStatus(currentPrice, currentSMA, currentRSI);
            System.out.println(i);
        }
    }

    private void buy(float price) {
        if (money <= 0 || price <= 0) return;

        float totalValue = getTotalPortfolioValue(price);
        float investAmount = totalValue * tradePercentage;

        if (investAmount > money) {
            investAmount = money;
        }

        float sharesBought = investAmount / price;
        money -= investAmount;
        stockAmount += sharesBought;

        System.out.printf("BOUGHT: %.4f shares at $%.2f each for $%.2f%n", sharesBought, price, investAmount);
    }

    private void sell(float price) {
        if (stockAmount <= 0 || price <= 0) return;

        float totalValue = getTotalPortfolioValue(price);
        float sellValue = totalValue * tradePercentage;
        float sharesToSell = sellValue / price;

        if (sharesToSell > stockAmount) {
            sharesToSell = stockAmount;
        }

        float revenue = sharesToSell * price;
        money += revenue;
        stockAmount -= sharesToSell;

        System.out.printf("SOLD: %.4f shares at $%.2f each for $%.2f%n", sharesToSell, price, revenue);
    }

    private void checkRiskManagement(float currentPrice, float entryPrice) {
        float lossThreshold = entryPrice * (1 - stopLossPercentage);
        float profitThreshold = entryPrice * (1 + takeProfitPercentage);

        if (currentPrice <= lossThreshold) {
            sellAll(currentPrice);
            System.out.println("STOP-LOSS triggered!");
        } else if (currentPrice >= profitThreshold) {
            sellAll(currentPrice);
            System.out.println("TAKE-PROFIT triggered!");
        }
    }

    private void sellAll(float price) {
        if (stockAmount > 0 && price > 0) {
            float revenue = stockAmount * price;
            money += revenue;
            stockAmount = 0;
            System.out.printf("SELL ALL: Sold everything at $%.2f for $%.2f%n", price, revenue);
        }
    }

    private void doNothing() {
        System.out.println("No trade action taken.");
    }

    private float getTotalPortfolioValue(float currentPrice) {
        return (currentPrice > 0) ? money + (stockAmount * currentPrice) : money;
    }

    private void printPortfolioStatus(float currentPrice, float currentSMA, float currentRSI) {
        float totalValue = getTotalPortfolioValue(currentPrice);
        System.out.printf("STATUS => Cash: $%.2f | Stocks: %.4f | Total Value: $%.2f | Current Price: $%.2f | SMA: %.2f | RSI: %.2f%n%n",
                money, stockAmount, totalValue, currentPrice, currentSMA, currentRSI);
    }

}
