package application;

import services.metodos.ServicosMenu;
import services.metodos.Tratatamento;

public class Program {
    public static void main(String[] args) {
        // Testado
        int opcao;

        do {
            ServicosMenu.mostrar();

            System.out.print("\n" + "Opção: ");
            opcao = Tratatamento.tratarErroTipoInputNumericoInteiro();

            // Validação
            while (opcao < 1 || opcao > 9) {
                System.out.print("\n" + "Opcão inválida. Insira outro valor: ");
                opcao = Tratatamento.tratarErroTipoInputNumericoInteiro();
            }

            ServicosMenu.executar(opcao);

        } while (opcao != 9);
    }
}
