import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        String word = "infinitely";
        int i = 0;

        while (!word.equals("Стоп")) {
            System.out.println("Пожалуйста, введите путь до файла");
            String path = new Scanner(System.in).nextLine();

            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (!fileExists) {
                System.out.println("Этот путь указан неверно");
                continue;
            }

            if (isDirectory) {
                System.out.println("Этот путь не до файла, а до папки");
                continue;
            }

            if (!isDirectory) {
                i++;
                System.out.println("Путь указан верно. Это файл номер " + i);
            }

            int totalLines = 0;
            int yandexBotCount = 0;
            int googleBotCount = 0;

            Statistics stats = new Statistics();

            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length > 1024) {
                        throw new LineTooLongException(
                                "Обнаружена строка длиной " + length + " символов, что превышает допустимый лимит в 1024 символа"
                        );
                    }

                    totalLines++;

                    try {
                        LogEntry entry = new LogEntry(line);
                        stats.addEntry(entry);

                        UserAgent userAgent = entry.getUserAgent();
                        String userAgentStr = line.substring(line.lastIndexOf('"') + 1);

                        if (userAgentStr.contains("Googlebot")) {
                            googleBotCount++;
                        } else if (userAgentStr.contains("YandexBot")) {
                            yandexBotCount++;
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Пропущена некорректная строка: " + e.getMessage());
                    }
                }

                System.out.println("\n--- Результаты анализа файла ---");
                System.out.println("Общее количество строк в файле: " + totalLines);

                if (totalLines > 0) {
                    double yandexBotRatio = (double) yandexBotCount / totalLines * 100;
                    double googleBotRatio = (double) googleBotCount / totalLines * 100;

                    System.out.printf("Доля запросов от YandexBot: %.2f%%\n", yandexBotRatio);
                    System.out.printf("Доля запросов от Googlebot: %.2f%%\n", googleBotRatio);
                    System.out.printf("Средний трафик за час: %.2f байт/час\n", stats.getTrafficRate());
                } else {
                    System.out.println("Файл пуст.");
                }
                System.out.println("-------------------------------\n");

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Ошибка ввода‑вывода: " + e.getMessage());
            } catch (LineTooLongException e) {
                System.out.println("ОШИБКА: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("Неизвестная ошибка: ");
                e.printStackTrace();
            }
        }
    }
}