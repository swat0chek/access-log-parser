import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String word = "infinitely";
        int i = 0;
        while (word != "Стоп") {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (fileExists == false) {
                System.out.println("Этот путь не до файла или неверно указан путь");
                continue;
            }

            if (isDirectory == true) {
                System.out.println("Этот путь не до файла или неверно указан путь");
                continue;
            }

            if (isDirectory == false) {
                i++;
                System.out.println("Путь указан верно. Это файл номер " + i);
            }
        }
    }
}
