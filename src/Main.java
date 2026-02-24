import java.io.File;
import java.util.Scanner;

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
        }
    }
}
