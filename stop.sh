
#!/bin/bash
echo "🛑 Stock Analytics Platform durduruluyor..."
if [ -f shared-data/collector.pid ]; then
    kill $(cat shared-data/collector.pid)
    rm shared-data/collector.pid
fi
if [ -f shared-data/monitor.pid ]; then
    kill $(cat shared-data/monitor.pid)
    rm shared-data/monitor.pid
fi
if [ -f shared-data/analyzer.pid ]; then
    kill $(cat shared-data/analyzer.pid)
    rm shared-data/analyzer.pid
fi
echo "✅ Tüm servisler durduruldu"
