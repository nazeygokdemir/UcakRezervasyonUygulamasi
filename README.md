# Uçak Bilet Rezervasyon Uygulaması

Bu proje Java programlama dili kullanılarak hazırlanmıştır. Konsol (terminal) üzerinden çalışan basit bir uçak bileti rezervasyon sistemidir. Nesneye dayalı programlama (OOP) yapısına uygun olarak sınıflar halinde modellendi.

## Proje Özellikleri

- Uçak bilgileri, Lokasyon bilgileri, Uçuş ve Rezervasyon modelleri yer almakta.
- Kullanıcı uçuşları listeleyip birini seçebiliyor.
- Seçilen uçuşa koltuk kapasitesine göre rezervasyon yapılabiliyor.
- Yapılan rezervasyonlar hem `.csv` hem de `.json` dosyalarına kaydediliyor.

## Kullanılan Yapılar

- Java sınıfları (`class`, `abstract class`)
- `Scanner` sınıfı ile kullanıcıdan giriş alma
- Dosyaya yazma (CSV ve JSON formatları)
- `UUID` ile her nesneye benzersiz ID atama

## Sınıf Yapıları

- `Ucak`: Uçak modeli, marka, seri no, koltuk kapasitesi
- `Lokasyon`: Ülke, şehir, havaalanı bilgisi
- `Ucus`: Uçak ve lokasyon ile ilişkilendirilmiş saatli uçuş
- `Rezervasyon`: Uçuşa bağlı ad, soyad ve yaş bilgisi
- `BaseEntity`: Ortak ID yapısını sağlayan soyut sınıf

## Programın Çalıştırılması

1. Java yüklü olduğundan emin olun (`java -version`).
2. Terminalden dosyanın bulunduğu klasöre gelin.
3. Aşağıdaki komutları çalıştırın:

```bash
javac UcakRezervasyonUygulamasi.java
java UcakRezervasyonUygulamasi
