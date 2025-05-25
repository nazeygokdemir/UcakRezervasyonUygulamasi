import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

abstract class BaseEntity {
    UUID id = UUID.randomUUID();
}

class Ucak extends BaseEntity {
    String model, marka, seriNo;
    int koltukKapasitesi;

    public Ucak(String model, String marka, String seriNo, int koltukKapasitesi) {
        this.model = model;
        this.marka = marka;
        this.seriNo = seriNo;
        this.koltukKapasitesi = koltukKapasitesi;
    }

    @Override
    public String toString() {
        return model + " - " + marka + " - SN: " + seriNo + " - Kapasite: " + koltukKapasitesi;
    }
}

class Lokasyon extends BaseEntity {
    String ulke, sehir, havaalani;
    boolean aktif;

    public Lokasyon(String ulke, String sehir, String havaalani, boolean aktif) {
        this.ulke = ulke;
        this.sehir = sehir;
        this.havaalani = havaalani;
        this.aktif = aktif;
    }

    @Override
    public String toString() {
        return havaalani + " - " + sehir + ", " + ulke + (aktif ? " (Aktif)" : " (Pasif)");
    }
}

class Ucus extends BaseEntity {
    Lokasyon lokasyon;
    String saat;
    Ucak ucak;
    int rezervasyonSayisi = 0;

    public Ucus(Lokasyon lokasyon, String saat, Ucak ucak) {
        this.lokasyon = lokasyon;
        this.saat = saat;
        this.ucak = ucak;
    }

    public boolean rezervasyonYap() {
        if (rezervasyonSayisi < ucak.koltukKapasitesi) {
            rezervasyonSayisi++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Uçuş: " + lokasyon + " | Saat: " + saat + " | Uçak: " + ucak.model;
    }
}

class Rezervasyon extends BaseEntity {
    Ucus ucus;
    String ad, soyad;
    int yas;

    public Rezervasyon(Ucus ucus, String ad, String soyad, int yas) {
        this.ucus = ucus;
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
    }

    @Override
    public String toString() {
        return ad + " " + soyad + " - Yas: " + yas + " - " + ucus;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("ad", ad);
        obj.put("soyad", soyad);
        obj.put("yas", yas);
        obj.put("havaalani", ucus.lokasyon.havaalani);
        obj.put("saat", ucus.saat);
        return obj;
    }
}

public class UcakRezervasyonUygulamasi {
    static List<Ucak> ucaklar = new ArrayList<>();
    static List<Lokasyon> lokasyonlar = new ArrayList<>();
    static List<Ucus> ucuslar = new ArrayList<>();
    static List<Rezervasyon> rezervasyonlar = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        ucaklar.add(new Ucak("737", "Boeing", "SN123", 5));
        lokasyonlar.add(new Lokasyon("Türkiye", "İstanbul", "IST", true));
        ucuslar.add(new Ucus(lokasyonlar.get(0), "12:30", ucaklar.get(0)));

        while (true) {
            System.out.println("1. Uçuşları Listele\n2. Rezervasyon Yap\n3. CSV Kaydet\n4. JSON Kaydet\n5. Çıkış");
            String secim = scanner.nextLine();

            if (secim.equals("1")) {
                for (int i = 0; i < ucuslar.size(); i++) {
                    System.out.println(i + ": " + ucuslar.get(i));
                }
            } else if (secim.equals("2")) {
                System.out.print("Uçuş numarası seçin: ");
                int ucusIndex = Integer.parseInt(scanner.nextLine());
                Ucus secilenUcus = ucuslar.get(ucusIndex);

                if (secilenUcus.rezervasyonYap()) {
                    System.out.print("Ad: "); String ad = scanner.nextLine();
                    System.out.print("Soyad: "); String soyad = scanner.nextLine();
                    System.out.print("Yaş: "); int yas = Integer.parseInt(scanner.nextLine());

                    Rezervasyon r = new Rezervasyon(secilenUcus, ad, soyad, yas);
                    rezervasyonlar.add(r);
                    System.out.println("Rezervasyon başarılı: " + r);
                } else {
                    System.out.println("Koltuk kapasitesi dolu!");
                }
            } else if (secim.equals("3")) {
                kaydetCSV("rezervasyonlar.csv", rezervasyonlar);
                System.out.println("CSV olarak kaydedildi.");
            } else if (secim.equals("4")) {
                kaydetJSON("rezervasyonlar.json", rezervasyonlar);
                System.out.println("JSON olarak kaydedildi.");
            } else {
                break;
            }
        }
    }

    static void kaydetCSV(String dosyaAdi, List<Rezervasyon> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi));
        for (Rezervasyon r : list) {
            writer.write(r.ad + "," + r.soyad + "," + r.yas + "," + r.ucus.lokasyon.havaalani + "," + r.ucus.saat + "\n");
        }
        writer.close();
    }

    static void kaydetJSON(String dosyaAdi, List<Rezervasyon> list) throws IOException {
        JSONArray arr = new JSONArray();
        for (Rezervasyon r : list) {
            arr.put(r.toJSON());
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi));
        writer.write(arr.toString(2));
        writer.close();
    }
}
