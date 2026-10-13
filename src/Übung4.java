import java.util.Scanner;

public class Übung4 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Anzahl der Personen ist(Ganzzahl): ");
        scanner.nextInt();

        System.out.print("Den Preis pro Gericht(Dezimalzahl) ist: ");
        scanner.nextDouble();

       System.out.print("Den Namen des Kellners(Text) ist: ");
       scanner.next();

       System.out.printf("%-12s", "Tisch 12");
       System.out.printf("%.2f EUR%n", 4 * 18.50);

       scanner.close();





    }

}
