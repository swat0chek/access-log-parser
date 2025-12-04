import java.util.Scanner;


//Задача 0.2
public class Main {
    public static void main(String[] args) {
//Вводим переменные
        System.out.println("Введите первое число");
        int numberOne = new Scanner(System.in).nextInt();

        System.out.println("Введите второе число");
        int numberTwo = new Scanner(System.in).nextInt();
//Дополнительная операция для преобразования в double
        double oneD = numberOne, twoD = numberTwo;
//Вычисление и вывод
        System.out.println("Сумма чисел " + (numberOne + numberTwo));
        System.out.println("Разность чисел " + (numberOne - numberTwo));
        System.out.println("Произведение чисел " + (numberOne * numberTwo));
        System.out.println("Частное чисел " + (oneD / twoD));
    }
}

