
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.*;
import java.util.*;
import com.google.gson.Gson;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class DashboardApplication {

    private static final String DATA_PATH = "../shared-data/";
    private final Gson gson = new Gson();

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "<html><head><title>Stock Analytics Dashboard</title><style>body { font-family: Arial; margin: 20px; } .metric { display: inline-block; margin: 10px; padding: 20px; background: #f0f0f0; border-radius: 8px; } img { max-width: 100%; margin: 10px 0; }</style></head><body><h1>📊 Stock Analytics Dashboard</h1><div id='metrics'></div><div id='charts'></div><script>async function loadDashboard() { const stats = await fetch('/api/statistics').then(r => r.json()); document.getElementById('metrics').innerHTML = Object.entries(stats).map(([key, value]) => `<div class='metric'><h3>${key}</h3><p>${value}</p></div>`).join(''); const charts = ['price_trends', 'volume_distribution', 'volatility_analysis']; document.getElementById('charts').innerHTML = charts.map(chart => `<img src='/api/chart/${chart}' alt='${chart}'>`).join(''); } loadDashboard(); setInterval(loadDashboard, 30000);</script></body></html>";
    }

    @GetMapping("/api/statistics")
    public Map<String, Object> getStatistics() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(DATA_PATH + "stock_data.csv"));
        stats.put("Toplam Kayıt", lines.size() - 1);
        if (Files.exists(Paths.get(DATA_PATH + "analysis_results.json"))) {
            String json = Files.readString(Paths.get(DATA_PATH + "analysis_results.json"));
            Map<String, Object> analysis = gson.fromJson(json, Map.class);
            stats.put("Son Analiz", analysis.get("timestamp"));
        }
        if (Files.exists(Paths.get(DATA_PATH + "performance.log"))) {
            List<String> perfLines = Files.readAllLines(Paths.get(DATA_PATH + "performance.log"));
            if (!perfLines.isEmpty()) {
                String lastLine = perfLines.get(perfLines.size() - 1);
                stats.put("Performans", lastLine);
            }
        }
        return stats;
    }

    @GetMapping("/api/chart/{name}")
    public ResponseEntity<Resource> getChart(@PathVariable String name) throws Exception {
        Path file = Paths.get(DATA_PATH + name + ".png");
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
    }

    @GetMapping("/api/data/latest")
    public Map<String, Object> getLatestData() throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(DATA_PATH + "stock_data.csv"));
        List<String> headers = Arrays.asList(lines.get(0).split(","));
        List<Map<String, String>> data = new ArrayList<>();
        int start = Math.max(1, lines.size() - 20);
        for (int i = start; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            Map<String, String> row = new HashMap<>();
            for (int j = 0; j < headers.size(); j++) {
                row.put(headers.get(j), values[j]);
            }
            data.add(row);
        }
        result.put("data", data);
        result.put("count", data.size());
        return result;
    }
}
