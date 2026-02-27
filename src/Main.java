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

        while (word != "Стоп") {
            System.out.println("Пожалуйста введите путь до файла");
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
            int maxLength = Integer.MIN_VALUE;
            int minLength = Integer.MAX_VALUE;

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length > 1024) {
                        throw new LineTooLongException(
                                "Обнаружена строка длиной " + length + " символов, что превышает допустимый лимит в 1024 символа"
                        );
                    }

                    totalLines++;
                    maxLength = Math.max(maxLength, length);
                    minLength = Math.min(minLength, length);
                }


                reader.close();
                fileReader.close();

                System.out.println("\n--- Результаты анализа файла ---");
                System.out.println("Общее количество строк в файле: " + totalLines);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + (minLength == Integer.MAX_VALUE ? 0 : minLength));
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