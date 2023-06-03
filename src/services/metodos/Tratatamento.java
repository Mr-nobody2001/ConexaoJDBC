package services.metodos;

import java.util.Scanner;

public abstract class Tratatamento {
    public static String baseTratamento() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        String input;

        input = scanner.next();

        while (!input.matches("^[0-9]+$")) {
            System.out.print("\n" + "Input inválido. Insira outro valor: ");
            input = scanner.next();
        }

        return input;
    }

    public static int tratarErroTipoInputNumericoInteiro() {
        // Testado
        int retorno;
        String input;

        retorno = -1;

        do {
            try {
                input = Tratatamento.baseTratamento();
                retorno = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.print("Valor inválido. Insira um valor menor!!!" + "\n");
            }
        } while (retorno == -1);

        return retorno;
    }

    public static long tratarErroTipoInputNumericoLong() {
        // Testado
        long retorno;
        String input;

        retorno = -1;

        do {
            try {
                input = Tratatamento.baseTratamento();
                retorno = Long.parseLong(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.print("Valor inválido. Insira um valor menor!!!" + "\n");
            }
        } while (retorno == -1);

        return retorno;
    }
}
