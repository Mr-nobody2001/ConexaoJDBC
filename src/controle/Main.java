package controle;

import controle.metodos.menus.MenuPrincipal;
import tratamento.Tratatamento;

public class Main {
    public static void main(String[] args) {
        // Testado
        int opcao;

        do {
            MenuPrincipal.mostrar();

            System.out.print("\n" + "Opção: ");
            opcao = Tratatamento.tratarErroTipoInputNumericoInteiro();

            // Validação
            while (opcao < 1 || opcao > 9) {
                System.out.print("\n" + "Opcão inválida. Insira outro valor: ");
                opcao = Tratatamento.tratarErroTipoInputNumericoInteiro();
            }

            MenuPrincipal.executar(opcao);

        } while (opcao != 9);
    }
}
