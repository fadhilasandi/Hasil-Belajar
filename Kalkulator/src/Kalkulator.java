import java.io.*;
import java.lang.*;
import java.lang.Math;
import java.util.Scanner;

public class Kalkulator {
    public static void main(String[] args) {
        double num1, num2;
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan Angka: ");
        num1 = sc.nextDouble();
        num2 = sc.nextDouble();
        System.out.println("Masukkan Operator (+,-,*,/): ");
        char op = sc.next().charAt(0);
        double o = 0;
        switch (op) {
            case '+':
                o = num1 + num2;
                break;
            case '-':
                o = num1 - num2;
                break;
            case '*':
                o = num1 * num2;
                break;
            case '/':
                o = num1 / num2;
                break;

            default:
                System.out.println("Anda salah memasukkan input!");
        }

        System.out.println("Hasil Akhirnya: ");
        System.out.println();

        System.out.println(num1 + " " + op + " " + num2 + " = " + o);
    }
}
