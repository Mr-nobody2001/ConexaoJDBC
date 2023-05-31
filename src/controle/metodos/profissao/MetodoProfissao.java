package controle.metodos.profissao;

import dao.vo.PessoaVO;
import dao.vo.ProfissaoVO;
import modelo.Profissao;
import tratamento.Tratatamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class MetodoProfissao {
    private static final ProfissaoVO profissaoVO = new ProfissaoVO();

    public static Profissao inserirProfissao() {
        // Testado
        Profissao profissao;

        profissao = criarObjetoProfissao();

        profissaoVO.inserirBD(profissao);

        return profissao;
    }

    private static Profissao criarObjetoProfissao() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        Profissao profissaoRetorno;

        String nomeProfissao;
        String descricaoProfissao;

        System.out.print("\n" + "Insira o nome da profissão: ");
        nomeProfissao = scanner.nextLine();

        while (nomeProfissao.length() == 0 || nomeProfissao.length() > 30) {
            System.out.print("\n" + "Número de caracteres inválido. Insira outro nome: ");
            nomeProfissao = scanner.next();
        }

        System.out.print("\n" + "Insira a descrição da profissão: ");

        do {
            descricaoProfissao = scanner.nextLine();
        } while (descricaoProfissao.equals(""));

        profissaoRetorno = new Profissao(nomeProfissao, descricaoProfissao);

        return profissaoRetorno;
    }

    public static boolean alterarProfissao() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        int opcaoAlterar;
        long idProfissao;
        String mensagem, alteracao;
        boolean retorno;

        mensagem = "";

        idProfissao = MetodoProfissao.obterEscolhaTipoProcura();

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Alterar o nome da profissão");
        System.out.println("2 - Alterar a descrição da profissão");

        System.out.print("\n" + "Opção: ");
        opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();

        while (opcaoAlterar != 1 && opcaoAlterar != 2) {
            System.out.print("\n" + "Opcão inválida. Insira outro valor: ");
            opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();
        }

        switch (opcaoAlterar) {
            case 1 -> mensagem = "Insira o novo nome: ";
            case 2 -> mensagem = "Insira a nova descrição: ";
        }

        System.out.print("\n" + mensagem);
        alteracao = scanner.nextLine();

        retorno = profissaoVO.alterarDB(idProfissao, opcaoAlterar, alteracao) == 1;

        return retorno;
    }

    private static long pesquisarProfissao() {
        // Testado
        List<ProfissaoVO> list;

        Scanner scanner;

        scanner = new Scanner(System.in);

        long idProfissao;
        String nomeProfissao;
        boolean listaVazia;

        do {
            listaVazia = true;

            System.out.print("\n" + "Insira o nome da profissão procurada: ");
            nomeProfissao = scanner.nextLine();

            list = new ArrayList<>(profissaoVO.obterProfissaoNome(nomeProfissao));

            if (list.size() != 0) {
                listaVazia = false;
            } else {
                System.out.println("\n" + "Não houve nenhuma correspondência!!!");
            }
        } while (listaVazia);

        System.out.println("\n" + "Profissões: ");
        System.out.println("------------------------------" + "\n");

        for (ProfissaoVO temp : list) {
            System.out.printf("Id:%d %s%n", temp.getId(), temp.getNome());
        }

        System.out.print("\n" + "Insira o id da pessoa escolhida: ");
        idProfissao = Tratatamento.tratarErroTipoInputNumericoLong();

        return idProfissao;
    }

    public static boolean removerProfissao() {
        // Testado
        long idProfissao;
        boolean retorno;

        idProfissao = obterEscolhaTipoProcura();

        retorno = profissaoVO.removerDB(idProfissao) == 1;

        return retorno;
    }

    public static long obterEscolhaTipoProcura() {
        int opcaoAlterar;
        long idProfissao;

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Procurar profissão pelo nome");
        System.out.println("2 - Procurar profissão pelo id");

        System.out.print("\n" + "Opção: ");
        opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();

        switch (opcaoAlterar) {
            case 1 -> {
                idProfissao = MetodoProfissao.pesquisarProfissao();
            }

            case 2 -> {
                MetodoProfissao.listarProfissaoInterna();
                System.out.print("\n" + "Insira o id da profissão que será alterada: ");
                idProfissao = Tratatamento.tratarErroTipoInputNumericoLong();
            }

            default -> idProfissao = -1;
        }

        return idProfissao;
    }

    public static void listarProfissao() {
        // Testado
        List<ProfissaoVO> list;

        list = profissaoVO.listarDB();

        System.out.println("\n" + "Profissões: ");
        System.out.println("------------------------------" + "\n");
        for (ProfissaoVO temp : list) {
            System.out.printf("%s %s : %s%n%n", temp.getId(), temp.getNome(), temp.getDescricao());
        }
    }

    public static void listarProfissaoInterna() {
        // Testado
        List<ProfissaoVO> list;

        list = profissaoVO.listarDB();

        System.out.println("\n" + "Profissões: ");
        System.out.println("------------------------------" + "\n");
        for (ProfissaoVO temp : list) {
            System.out.printf("Id:%s %s %n", temp.getId(), temp.getNome());
        }
    }
}
