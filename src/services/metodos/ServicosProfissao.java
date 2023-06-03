package services.metodos;

import dao.entitiesVO.ProfissaoVO;
import entities.Profissao;
import entities.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicosProfissao {
    private static final ServicosProfissao servicosProfissao = new ServicosProfissao();
    private static final ProfissaoVO profissaoVO = new ProfissaoVO();

    public Profissao criarProfissao() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        Profissao novaProfissao;

        String nomeProfissao;
        String descricaoProfissao;

        System.out.print("\n" + "Insira o nome da profissão: ");

        nomeProfissao = scanner.nextLine();

        if (nomeProfissao.length() == 0 || nomeProfissao.length() > 30) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        System.out.print("\n" + "Insira a descrição da profissão: ");

        do {
            descricaoProfissao = scanner.nextLine();
        } while (descricaoProfissao.equals(""));

        novaProfissao = new Profissao(nomeProfissao, descricaoProfissao);

        profissaoVO.inserirBD(novaProfissao);

        return novaProfissao;
    }

    public void alterarProfissao() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        int opcao;
        long idProfissao;
        String mensagem, alteracao;

        mensagem = "";

        idProfissao = servicosProfissao.obterIdProfissao();

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Alterar o nome da profissão");
        System.out.println("2 - Alterar a descrição da profissão");

        System.out.print("\n" + "Opção: ");
        opcao = scanner.nextInt();

        if (opcao != 1 && opcao != 2) {
            throw new InvalidInputException("Opcão inválida");
        }

        switch (opcao) {
            case 1 -> mensagem = "Insira o novo nome: ";
            case 2 -> mensagem = "Insira a nova descrição: ";
        }

        System.out.print("\n" + mensagem);

        do {
            alteracao = scanner.nextLine();
        } while (alteracao.equals(""));

        if (opcao == 1 && (alteracao.length() == 0 || alteracao.length() > 30)) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        int retorno = profissaoVO.alterarDB(idProfissao, opcao, alteracao);

        if (retorno == 0) {
            throw new SqlUpdateException("não foi possível realizer a alteração dos dados");
        }
    }

    public void removerProfissao() {
        // Testado
        long idProfissao;

        idProfissao = obterIdProfissao();

        int retorno = profissaoVO.removerDB(idProfissao);

        if (retorno == 0) {
            throw new SqlDeleteException("não foi possível fazer a exclusão dos dados");
        }
    }

    public void listarProfissaoExtenso() {
        // Testado
        List<Profissao> list;

        list = profissaoVO.listarDB();

        System.out.println("\n" + "Profissões: ");
        System.out.println("------------------------------" + "\n");
        for (Profissao temp : list) {
            System.out.printf("%s %s : %s%n%n", temp.getId(), temp.getNome(), temp.getDescricao());
        }
    }

    public void listarProfissaoResumida() {
        // Testado
        List<Profissao> listaProfissoes;

        listaProfissoes = profissaoVO.listarDB();

        imprimirProfissaoResumida(listaProfissoes);
    }

    public void listarProfissaoResumida(List<Profissao> listaProfissoes) {
        // Testado
        imprimirProfissaoResumida(listaProfissoes);
    }

    private void imprimirProfissaoResumida(List<Profissao> listaProfissoes) {
        System.out.println("\n" + "Profissões: ");
        System.out.println("------------------------------" + "\n");
        for (Profissao temp : listaProfissoes) {
            System.out.printf("Id:%s %s %n", temp.getId(), temp.getNome());
        }
    }

    public long obterIdProfissao() {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        long idProfissao;

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Procurar profissão pelo nome");
        System.out.println("2 - Procurar profissão pelo id");

        System.out.print("\n" + "Opção: ");
        opcao = scanner.nextInt();

        if (opcao != 1 && opcao != 2) {
            throw new InvalidInputException("opcão inválida");
        }

        switch (opcao) {
            case 1 -> idProfissao = servicosProfissao.obterIdProfissaoNome();

            case 2 -> {
                servicosProfissao.listarProfissaoResumida();
                System.out.print("\n" + "Insira o id da profissão que será alterada: ");
                idProfissao = scanner.nextLong();
            }

            default -> idProfissao = -1;
        }

        if (idProfissao <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        return idProfissao;
    }

    private long obterIdProfissaoNome() {
        // Testado
        List<Profissao> listaProfissoes;

        Scanner scanner = new Scanner(System.in);

        long idProfissao;
        String nomeProfissao;

        System.out.print("\n" + "Insira o nome da profissão procurada: ");
        nomeProfissao = scanner.nextLine();

        if (nomeProfissao.length() == 0 || nomeProfissao.length() > 50) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        listaProfissoes = new ArrayList<>(profissaoVO.obterProfissaoNome(nomeProfissao));

        if (listaProfissoes.size() == 0) {
            throw new TargetNotFoundExecption("não houve nenhuma correspondência");
        }

        listarProfissaoResumida(listaProfissoes);

        System.out.print("\n" + "Insira o id da pessoa escolhida: ");
        idProfissao = scanner.nextLong();

        if (idProfissao <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        return idProfissao;
    }
}
