package application;

import db.BaseDAO;
import services.ServicosMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        // Testado
        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        do {
            try {
                ServicosMenu.mostrar();

                System.out.print("\n" + "Opção: ");
                opcao = scanner.nextInt();

                // Validação
                while (opcao < 1 || opcao > 9) {
                    System.out.print("\n" + "Opcão inválida. Insira outro valor: ");
                    opcao = scanner.nextInt();
                }

                ServicosMenu.executar(opcao);
            } catch (InputMismatchException e) {
                System.out.println("\nErro de input: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("\nErro inesperado: " + e.getMessage());
                e.printStackTrace();
            }

            scanner.nextLine();
        } while (opcao != 9);

        scanner.close();
        BaseDAO.fecharConnection();
    }
}
