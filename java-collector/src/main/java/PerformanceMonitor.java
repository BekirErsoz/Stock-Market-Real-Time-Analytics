
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PerformanceMonitor {

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new MonitorTask(), 0, 10000);
    }

    static class MonitorTask extends TimerTask {
        @Override
        public void run() {
            try {
                File csvFile = new File("../shared-data/stock_data.csv");
                long csvSize = csvFile.length() / 1024;
                long lineCount = Files.lines(csvFile.toPath()).count() - 1;
                File jsonFile = new File("../shared-data/analysis_results.json");
                long lastModified = jsonFile.lastModified();

                System.out.println("\n=== PERFORMANS RAPORU ===");
                System.out.println("CSV Boyutu: " + csvSize + " KB");
                System.out.println("Toplam Kayıt: " + lineCount);
                System.out.println("Son Analiz: " + new Date(lastModified));
                System.out.println("Bellek Kullanımı: " + getMemoryUsage() + " MB");
                System.out.println("========================\n");

                savePerformanceLog(csvSize, lineCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private long getMemoryUsage() {
            Runtime runtime = Runtime.getRuntime();
            return (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        }

        private void savePerformanceLog(long csvSize, long recordCount) throws IOException {
            String log = String.format("%s,CSV_SIZE,%d,RECORDS,%d,MEMORY,%d%n",
                new Date(), csvSize, recordCount, getMemoryUsage());
            Files.write(
                Paths.get("../shared-data/performance.log"),
                log.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        }
    }
}
