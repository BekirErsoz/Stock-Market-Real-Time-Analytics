
# 📊 Stock Market Real-Time Analytics Platform

Java ve R kullanan gerçek zamanlı borsa veri analiz platformu.

## 🎯 Özellikler

- **Gerçek Zamanlı Veri Toplama** (Java)
  - Her 5 saniyede simüle edilmiş borsa verisi
  - CSV ve JSON formatında kayıt
  - Performans monitörü

- **İstatistiksel Analiz** (R)
  - Ortalama, medyan, standart sapma hesaplamaları
  - Volatilite analizi
  - ARIMA ile fiyat tahmini
  - Otomatik görselleştirme

- **Web Dashboard** (Spring Boot)
  - Canlı metrikler
  - Grafik görüntüleme
  - REST API

## 🛠️ Teknolojiler

- **Java 11+**: Veri toplama ve performans izleme
- **R 4.0+**: İstatistiksel analiz ve görselleştirme
- **Spring Boot**: Web dashboard
- **Gson**: JSON işleme
- **ggplot2**: Gelişmiş grafikler

## 📦 Kurulum

1. **Gereksinimler**:
   ```bash
   # Java
   sudo apt install openjdk-11-jdk

   # R ve kütüphaneler
   sudo apt install r-base
   R -e "install.packages(c('tidyverse', 'forecast', 'jsonlite', 'ggplot2'))"

   # Gson JAR indir
   wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.9/gson-2.8.9.jar -P java-collector/
   ```
