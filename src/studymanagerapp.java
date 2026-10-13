import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class studymanagerapp {

    // ==========================================
    // KLASA FÜR AUFGABE (TASK)
    // ==========================================
    static class Task {
        String subject;
        String name;
        String priority;
        LocalDate deadline;
        long daysLeft;
        int priorityValue;
        int deadlineValue;
        int score;

        public Task(String subject, String name, String priority, LocalDate deadline, 
                    long daysLeft, int priorityValue, int deadlineValue, int score) {
            this.subject = subject;
            this.name = name;
            this.priority = priority;
            this.deadline = deadline;
            this.daysLeft = daysLeft;
            this.priorityValue = priorityValue;
            this.deadlineValue = deadlineValue;
            this.score = score;
        }
    }

    // Gespeicherte Fächer
    private static final List<String> SUBJECTS = Arrays.asList(
        "Mathe",
        "English",
        "Programmieren",
        "Datenmanagement",
        "Wirtschaftsinformatik"
    );

    private static final List<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // ==========================================
    // MAIN METHODE
    // ==========================================
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("          WILLKOMMEN IM STUDY PLANNER");
        System.out.println("==========================================");

        System.out.println("\nGespeicherte Fächer:");
        for (int i = 0; i < SUBJECTS.size(); i++) {
            System.out.println((i + 1) + ". " + SUBJECTS.get(i));
        }

        while (true) {
            System.out.println("\n------------------------------------------");
            System.out.print("Add Task? (ja/nein): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("ja") || answer.equals("j")) {
                addTask();
                showTasks();
            } else if (answer.equals("nein") || answer.equals("n")) {
                System.out.println("\nStudy Planner beendet.");
                break;
            } else {
                System.out.println("\nBitte antworte mit 'ja' oder 'nein'.");
            }
        }

        scanner.close();
    }

    // ==========================================
    // AUFGABE HINZUFÜGEN
    // ==========================================
    private static void addTask() {
        System.out.println("\n=== AUFGABE HINZUFÜGEN ===");

        // Fach auswählen
        System.out.println("\nFächer:");
        for (int i = 0; i < SUBJECTS.size(); i++) {
            System.out.println((i + 1) + ". " + SUBJECTS.get(i));
        }

        System.out.print("\nFach auswählen: ");
        String subjectInput = scanner.nextLine().trim();
        int subjectNumber;

        try {
            subjectNumber = Integer.parseInt(subjectInput);
        } catch (NumberFormatException e) {
            System.out.println("\nUngültiges Fach.");
            return;
        }

        if (subjectNumber < 1 || subjectNumber > SUBJECTS.size()) {
            System.out.println("\nUngültiges Fach.");
            return;
        }

        String subject = SUBJECTS.get(subjectNumber - 1);

        // Aufgabe
        System.out.print("Aufgabe: ");
        String taskName = scanner.nextLine().trim();

        if (taskName.isEmpty()) {
            System.out.println("\nDie Aufgabe darf nicht leer sein.");
            return;
        }

        // Priorität
        System.out.println("\nPriorität:");
        System.out.println("1. High");
        System.out.println("2. Medium");
        System.out.println("3. Low");
        System.out.print("Priorität: ");

        String priorityInput = scanner.nextLine().trim();
        String priority;

        if (priorityInput.equals("1")) {
            priority = "High";
        } else if (priorityInput.equals("2")) {
            priority = "Medium";
        } else if (priorityInput.equals("3")) {
            priority = "Low";
        } else {
            System.out.println("\nUngültige Priorität.");
            return;
        }

        // Datum
        System.out.print("Deadline (TT.MM.JJJJ): ");
        String dateInput = scanner.nextLine().trim();

        LocalDate deadline = parseDate(dateInput);
        if (deadline == null) {
            System.out.println("\nUngültiges Datum. Beispiel: 10.10.2026");
            return;
        }

        // Tage berechnen
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), deadline);

        // Prioritätswert & Deadline-Wert
        int priorityValue = getPriorityValue(priority);
        int deadlineValue = getDeadlineValue(daysLeft);

        // Score berechnen
        int score = priorityValue + deadlineValue;

        // Aufgabe speichern
        Task task = new Task(subject, taskName, priority, deadline, daysLeft, priorityValue, deadlineValue, score);
        tasks.add(task);

        System.out.println("\n✓ Aufgabe wurde hinzugefügt!");
        System.out.println("Score: " + score);
    }

    // ==========================================
    // AUFGABEN ANZEIGEN
    // ==========================================
    private static void showTasks() {
        System.out.println("\n==========================================");
        System.out.println("             STUDY PLANNER");
        System.out.println("==========================================");

        if (tasks.isEmpty()) {
            System.out.println("\nKeine Aufgaben vorhanden.");
            return;
        }

        // Nach Score sortieren (absteigend)
        tasks.sort((a, b) -> Integer.compare(b.score, a.score));

        System.out.println("\nDeine Aufgaben:\n");
        System.out.println("Nr. | Aufgabe                  | Fach                  | Priorität | Deadline   | Tage | Score");
        System.out.println("----|--------------------------|-----------------------|-----------|------------|------|------");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            String number = padRight(String.valueOf(i + 1), 3);
            String name = padRight(truncate(task.name, 24), 24);
            String subject = padRight(truncate(task.subject, 21), 21);
            String priority = padRight(task.priority, 9);
            String deadline = padRight(task.deadline.format(DATE_FORMATTER), 10);
            String days = padRight(String.valueOf(task.daysLeft), 4);
            String score = String.valueOf(task.score);

            System.out.printf("%s | %s | %s | %s | %s | %s | %s%n",
                    number, name, subject, priority, deadline, days, score);
        }

        System.out.println("\n==========================================");
        System.out.println("Höchster Score = höchste Priorität");
        System.out.println("==========================================\n");
    }

    // ==========================================
    // POMOĆNE METODE (HELPERS)
    // ==========================================
    private static int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "high":
            case "hoch":
                return 3;
            case "medium":
            case "mittel":
                return 2;
            case "low":
            case "niedrig":
                return 1;
            default:
                return 0;
        }
    }

    private static int getDeadlineValue(long daysLeft) {
        if (daysLeft <= 1) return 10;
        if (daysLeft <= 3) return 8;
        if (daysLeft <= 5) return 6;
        if (daysLeft <= 7) return 4;
        return 1;
    }

    private static LocalDate parseDate(String dateInput) {
        try {
            return LocalDate.parse(dateInput, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private static String truncate(String s, int maxLength) {
        if (s.length() <= maxLength) return s;
        return s.substring(0, maxLength); //test before launching
    }
}
