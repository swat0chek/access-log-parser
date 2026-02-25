import java.io.*;
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

                    int lastQuoteIndex = line.lastIndexOf('"');
                    if (lastQuoteIndex != -1) {
                        int secondLastQuoteIndex = line.lastIndexOf('"', lastQuoteIndex - 1);
                        if (secondLastQuoteIndex != -1) {
                            String userAgent = line.substring(secondLastQuoteIndex + 1, lastQuoteIndex);

                            int startBracket = userAgent.indexOf('(');
                            int endBracket = userAgent.indexOf(')', startBracket);

                            if (startBracket != -1 && endBracket != -1) {
                                String firstBrackets = userAgent.substring(startBracket + 1, endBracket);
                                String[] parts = firstBrackets.split(";");

                                for (String part : parts) {
                                    part = part.trim();
                                    if (part.startsWith("Googlebot")) {
                                        googleBotCount++;
                                        break;
                                    } else if (part.startsWith("YandexBot")) {
                                        yandexBotCount++;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("\n--- Результаты анализа файла ---");
                System.out.println("Общее количество строк в файле: " + totalLines);

                if (totalLines > 0) {
                    double yandexBotRatio = (double) yandexBotCount / totalLines * 100;
                    double googleBotRatio = (double) googleBotCount / totalLines * 100;

                    System.out.printf("Доля запросов от YandexBot: %.2f%%\n", yandexBotRatio);
                    System.out.printf("Доля запросов от Googlebot: %.2f%%\n", googleBotRatio);
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
