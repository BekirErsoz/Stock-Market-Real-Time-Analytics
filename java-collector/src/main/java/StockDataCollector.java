
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;

public class StockDataCollector {

    private static final String OUTPUT_PATH = "../shared-data/stock_data.csv";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new CollectorTask(), 0, 5000); // Her 5 saniyede bir çalış
    }

    static class CollectorTask extends TimerTask {
        @Override
        public void run() {
            try {
                StockData data = generateStockData();
                saveToCSV(data);
                saveToJSON(data);
                System.out.println("Veri toplandı: " + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class StockData {
        String symbol;
        double price;
        long volume;
        String timestamp;
        double change;

        @Override
        public String toString() {
            return String.format("%s: %.2f (%.2f%%) Volume: %d", 
                symbol, price, change, volume);
        }
    }

    private static StockData generateStockData() {
        Random rand = new Random();
        StockData data = new StockData();
        String[] symbols = {"AAPL", "GOOGL", "MSFT", "AMZN", "TSLA"};
        data.symbol = symbols[rand.nextInt(symbols.length)];
        data.price = 100 + rand.nextDouble() * 200;
        data.volume = rand.nextInt(1000000);
        data.change = -5 + rand.nextDouble() * 10;
        data.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return data;
    }

    private static void saveToCSV(StockData data) throws IOException {
        File file = new File(OUTPUT_PATH);
        boolean isNewFile = !file.exists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.println("timestamp,symbol,price,volume,change");
            }
            writer.printf("%s,%s,%.2f,%d,%.2f%n", data.timestamp, data.symbol, data.price, data.volume, data.change);
        }
    }

    private static void saveToJSON(StockData data) throws IOException {
        String json = gson.toJson(data);
        try (PrintWriter writer = new PrintWriter(new FileWriter("../shared-data/latest_stock.json"))) {
            writer.println(json);
        }
    }
}
