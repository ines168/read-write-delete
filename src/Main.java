import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite putanju do originalne datoteke:");
        //"C:\Users\inesc\Documents\ALGEBRA\VJEZBE\20.ZADATAK\original.pdf"
        try {
            String pathOriginal = sc.nextLine();
            File inputFile = new File(pathOriginal);
            if (!inputFile.exists()) {
                throw new FileNotFoundException("Nismo mogli pronaći datoteku koju želite kopirati.");
            }
            if (!inputFile.canRead()) {
                throw new SecurityException("Nemate prava čitati ovu datoteku.");
            }
            System.out.println("Unesite naziv kopirane datoteke:");
            String pathKopija = sc.nextLine();
            File outputFile = new File(pathKopija);
            if (outputFile.exists()) {
                System.out.println("\nOva datoteka već postoji. Jeste li sigurni daju želite overwritati (D/N)?");
                String brisanje = sc.nextLine().toLowerCase();
                if (brisanje.equals("d")) {
                    brisanjeDatoteke(outputFile, pathKopija);
                } else {
                    System.out.println("Izlazim iz programa...");
                    return;
                }
            }

            try (FileInputStream ulaz = new FileInputStream(inputFile); FileOutputStream izlaz = new FileOutputStream(outputFile)) {
                //byte[] bytes = new byte[(int) inputFile.length()];
                byte[] bytes = new byte[8];
                while (ulaz.read(bytes) != -1) {
                    izlaz.write(bytes);
                }
                System.out.println("\nDatoteka " + pathOriginal + " je uspješno kopirana!");
            }
            if (outputFile.exists()) {
                System.out.println("Želite li izbrisati kopiju datoteke (D/N)?");
                String brisanje = sc.nextLine().toLowerCase();
                if (brisanje.equals("d")) {
                    brisanjeDatoteke(outputFile, pathKopija);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nismo mogli pronaći datoteku koju želite kopirati.");
        } catch (FailedToDelete e) {
            System.err.println("Greška! " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom čitanja ili pisanja datoteke: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Došlo je do neočekivane greške: " + e.getMessage());
        }
    }

    public static void brisanjeDatoteke(File file, String filePath) throws FailedToDelete {
        if (file.delete()) {
            System.out.println("\nDatoteka " + filePath + " je izbrisana.");
        } else {
            throw new FailedToDelete("Nismo mogli obrisati kopiju vaše datoteke.");
        }
    }
}
