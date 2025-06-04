
#!/bin/bash
echo "🚀 Stock Analytics Platform Başlatılıyor..."
mkdir -p shared-data
echo "📦 Java uygulamaları derleniyor..."
cd java-collector
javac -cp ".:gson-2.8.9.jar" *.java
echo "▶️ Data Collector başlatılıyor..."
java -cp ".:gson-2.8.9.jar" StockDataCollector &
COLLECTOR_PID=$!
echo "▶️ Performance Monitor başlatılıyor..."
java PerformanceMonitor &
MONITOR_PID=$!
cd ..
echo "▶️ R Analyzer başlatılıyor..."
cd r-analyzer
Rscript stock_analyzer.R &
ANALYZER_PID=$!
cd ..
echo "✅ Tüm servisler başlatıldı!"
echo "   - Data Collector PID: $COLLECTOR_PID"
echo "   - Performance Monitor PID: $MONITOR_PID"
echo "   - R Analyzer PID: $ANALYZER_PID"
echo "$COLLECTOR_PID" > shared-data/collector.pid
echo "$MONITOR_PID" > shared-data/monitor.pid
echo "$ANALYZER_PID" > shared-data/analyzer.pid
echo ""
echo "📊 Sonuçları görmek için shared-data/ klasörünü kontrol edin"
echo "🛑 Durdurmak için: ./stop.sh"
