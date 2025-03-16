package Inventaris;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Kelas buat nyimpen data barang sama jumlah dan satuannya
class Barang {
    private String nama;
    private int jumlah;
    private String satuan;

    public Barang(String nama, int jumlah, String satuan) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.satuan = satuan;
    }

    public String getNama() {
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void tambahJumlah(int tambahan) {
        this.jumlah += tambahan;
    }

    public void kurangiJumlah(int pengurangan) {
        this.jumlah -= pengurangan;
    }

    @Override
    public String toString() {
        return nama + " - " + jumlah + " " + satuan;
    }
}

// Kelas buat ngurusin inventaris barang
class Inventaris<T extends Barang> { // Biar cuma bisa dipake buat Barang atau turunannya
    private List<T> daftarBarang = new ArrayList<>();

    public void tambahBarang(T barang) {
        // Cek dulu barangnya udah ada belum, kalau ada tambahin jumlahnya
        for (T b : daftarBarang) {
            if (b.getNama().equalsIgnoreCase(barang.getNama()) && b.getSatuan().equalsIgnoreCase(barang.getSatuan())) {
                b.tambahJumlah(barang.getJumlah());
                System.out.println("Jumlah barang diperbarui: " + barang.getNama() + " sekarang " + b.getJumlah() + " " + b.getSatuan());
                return;
            }
        }
        // Kalau belum ada, masukin barang baru
        daftarBarang.add(barang);
        System.out.println("Barang berhasil ditambahkan: " + barang);
    }

    public void tampilkanBarang() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Inventaris kosong.");
        } else {
            System.out.println("Daftar Barang di Inventaris:");
            for (T barang : daftarBarang) {
                System.out.println("- " + barang);
            }
        }
    }

    public void hapusBarang(String namaBarang, String satuan, int jumlahHapus) {
        for (T barang : daftarBarang) {
            if (barang.getNama().equalsIgnoreCase(namaBarang) && barang.getSatuan().equalsIgnoreCase(satuan)) {
                if (barang.getJumlah() > jumlahHapus) {
                    barang.kurangiJumlah(jumlahHapus);
                    System.out.println("Jumlah barang berkurang: " + namaBarang + " sekarang " + barang.getJumlah() + " " + satuan);
                } else {
                    daftarBarang.remove(barang);
                    System.out.println("Barang '" + namaBarang + " (" + satuan + ")' telah habis dan dihapus.");
                }
                return;
            }
        }
        System.out.println("Barang tidak ditemukan.");
    }
}

// Main Class buat jalanin program
public class MainInven {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventaris<Barang> inventaris = new Inventaris<>(); // Bikin objek inventaris

        while (true) {
            System.out.println("\nAPLIKASI INVENTARIS");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Lihat Barang");
            System.out.println("3. Kurangi Stok Barang");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Bersihin buffer biar gak error

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan nama barang: ");
                    String namaBarang = scanner.nextLine();
                    System.out.print("Masukkan jumlah barang: ");
                    int jumlah = scanner.nextInt();
                    scanner.nextLine(); // Bersihin buffer lagi
                    System.out.print("Masukkan satuan barang (kg, pcs, liter, dll.): ");
                    String satuan = scanner.nextLine();
                    inventaris.tambahBarang(new Barang(namaBarang, jumlah, satuan));
                    break;
                case 2:
                    inventaris.tampilkanBarang();
                    break;
                case 3:
                    System.out.print("Masukkan nama barang yang ingin dikurangi: ");
                    String hapusBarang = scanner.nextLine();
                    System.out.print("Masukkan satuan barang (kg, pcs, liter, dll.): ");
                    String hapusSatuan = scanner.nextLine();
                    System.out.print("Masukkan jumlah yang ingin dikurangi: ");
                    int jumlahHapus = scanner.nextInt();
                    scanner.nextLine(); // Bersihin buffer lagi
                    inventaris.hapusBarang(hapusBarang, hapusSatuan, jumlahHapus);
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }
}
