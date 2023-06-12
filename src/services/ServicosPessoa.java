package services;

import entities.dao.ProfissaoDAO;
import entities.dao.TelefoneDAO;
import entities.Pessoa;
import entities.Profissao;
import entities.Telefone;
import entities.dao.PessoaDAO;
import entities.dao.impl.DAOFactory;
import entities.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicosPessoa {
    private static final PessoaDAO PESSOA_DAO = DAOFactory.criarPessoaDao();
    private static final ServicosPessoa SERVICOS_PESSOA = new ServicosPessoa();
    private static final ServicosTelefone SERVICOS_TELEFONE = new ServicosTelefone();

    public Pessoa criarPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        TelefoneDAO telefoneDao = DAOFactory.criarTelefoneDao();

        Pessoa novaPessoa;
        Profissao profissao;

        String nomePessoa;

        System.out.print("\n" + "Insira o nome da pessoa: ");
        nomePessoa = scanner.nextLine();

        if (nomePessoa.length() == 0 || nomePessoa.length() > 50) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        profissao = SERVICOS_PESSOA.obterProfissao();

        novaPessoa = new Pessoa(nomePessoa, profissao);

        SERVICOS_TELEFONE.criarTelefone(novaPessoa);

        novaPessoa.setId(PESSOA_DAO.inserir(novaPessoa));

        telefoneDao.inserir(novaPessoa.getTelefones(), novaPessoa.getId());

        return novaPessoa;
    }

    public void alterarPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        TelefoneDAO telefoneDao = DAOFactory.criarTelefoneDao();

        Pessoa pessoa;

        int opcao;
        String alteracao;

        pessoa = SERVICOS_PESSOA.obterPessoa();

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Alterar o nome da pessoa");
        System.out.println("2 - Alterar a profissão da pessoa");
        System.out.println("3 - Alterar o número de telefone da pessoa");

        System.out.print("\n" + "Opção: ");
        opcao = scanner.nextInt();

        if (opcao < 1 || opcao > 3) {
            throw new InvalidInputException("Opcão inválida");
        }

        switch (opcao) {
            case 1 -> {
                System.out.print("\n" + "Insira o novo nome: ");

                do {
                    alteracao = scanner.nextLine();
                } while (alteracao.equals(""));

                if (alteracao.length() > 50) {
                    throw new InvalidLenghtException("o número de caracteres do nome é inválido");
                }

                pessoa.setNome(alteracao);

                int retorno = PESSOA_DAO.alterar(pessoa);

                if (retorno == 0) {
                    throw new SqlUpdateException("não foi possível realizer a alteração dos dados");
                }
            }

            case 2 -> {
                System.out.print("\n" + "Insira uma nova profissão: ");

                pessoa.setProfissao(SERVICOS_PESSOA.obterProfissao());

                int retorno = PESSOA_DAO.alterar(pessoa);

                if (retorno == 0) {
                    throw new SqlUpdateException("não foi possível realizer a alteração dos dados");
                }
            }

            case 3 -> {
                System.out.println("\n" + "Escolha uma opção");
                System.out.println("1 - Remover um número de telefone");
                System.out.println("2 - Adicionar um número de telefone");
                System.out.println("3 - Alterar um número de telefone já existente");

                System.out.print("\n" + "Opção: ");
                opcao = scanner.nextInt();

                if (opcao < 1 || opcao > 3) {
                    throw new InvalidInputException("Opcão inválida");
                }

                switch (opcao) {
                    case 1 -> SERVICOS_TELEFONE.removerTelefones(pessoa.getId());

                    case 2 -> {
                        int quantidadeNumerosAnterior = pessoa.getTelefones().size();

                        SERVICOS_TELEFONE.criarTelefone(pessoa);

                        int quantidadeNumerosAtual = pessoa.getTelefones().size();

                        if (quantidadeNumerosAtual == quantidadeNumerosAnterior) {
                            throw new InvalidInputException("são permitidos apenas valores positivos");
                        }

                        telefoneDao.inserir(pessoa.getTelefones(), pessoa.getId());
                    }

                    case 3 -> SERVICOS_TELEFONE.alterarTelefones(pessoa.getId());

                }
            }
        }
    }

    public void removerPessoa() {
        // Testado
        TelefoneDAO telefoneDao = DAOFactory.criarTelefoneDao();

        Pessoa pessoa = obterPessoa();

        telefoneDao.remover(pessoa.getId());

        int retorno = PESSOA_DAO.remover(pessoa.getId());

        if (retorno == 0) {
            throw new SqlDeleteException("não foi possível fazer a exclusão dos dados");
        }
    }

    public void listarPessoaExtenso() {
        // Testado
        List<Pessoa> listPessoa;
        List<Telefone> listTelefone;

        TelefoneDAO telefoneDao = DAOFactory.criarTelefoneDao();

        String nomePessoa;
        String nomeProfissao;
        String numeros;

        listPessoa = PESSOA_DAO.listarPessoa();

        System.out.println("\n" + "Pessoas: ");
        System.out.println("------------------------------" + "\n");
        for (Pessoa temp : listPessoa) {
            numeros = "";

            nomePessoa = temp.getNome();

            nomeProfissao = temp.getProfissao().getNome();

            listTelefone = telefoneDao.listarTelefone(temp.getId());

            for (Telefone temp2 : listTelefone) {
                numeros += "(" + temp2.getNumero().substring(0, 2) + ")" + temp2.getNumero().substring(2, 7) +
                        "-" + temp2.getNumero().substring(7) + " ";
            }

            System.out.printf("Nome:%s%nProfissão:%s%nTelefone(s):%s%n%n",
                    Formatar.formatarNome(nomePessoa), Formatar.formatarNome(nomeProfissao), numeros);
        }
    }

    private Pessoa listarPessoaResumida(List<Pessoa> listaPessoas) {
        Scanner scanner = new Scanner(System.in);

        long idPessoa;

        System.out.println("\n" + "Pessoas: ");
        System.out.println("------------------------------" + "\n");

        for (Pessoa temp : listaPessoas) {
            System.out.printf("Id:%d %s%n", temp.getId(), Formatar.formatarNome(temp.getNome()));
        }

        System.out.print("\n" + "Insira o id da pessoa escolhida: ");
        idPessoa = scanner.nextLong();

        if (idPessoa <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        Pessoa alvo = null;

        for (Pessoa listaPessoa : listaPessoas) {
            if (listaPessoa.getId() == idPessoa) {
                alvo = listaPessoa;
            }
        }

        if (alvo == null) {
            throw new TargetNotFoundExecption("não houve nenhuma correspondência");
        }

        return alvo;
    }

    private Pessoa obterPessoaNome() {
        List<Pessoa> listaPessoas;

        Scanner scanner = new Scanner(System.in);

        Pessoa pessoa;

        String nomePessoa;

        System.out.print("\n" + "Insira o nome da pessoa procurada: ");
        nomePessoa = scanner.nextLine();

        if (nomePessoa.length() == 0 || nomePessoa.length() > 50) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        listaPessoas = new ArrayList<>(PESSOA_DAO.pesquisarPessoa(nomePessoa));

        if (listaPessoas.size() == 0) {
            throw new TargetNotFoundExecption("não houve nenhuma correspondência");
        }

        if (listaPessoas.size() > 1) {
            pessoa = listarPessoaResumida(listaPessoas);
        } else {
            pessoa = listaPessoas.get(0);
        }

        return pessoa;
    }

    private Profissao obterProfissao() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        ServicosProfissao servicosProfissao = new ServicosProfissao();
        ProfissaoDAO profissaoDAO = DAOFactory.criarProfissaoDao();
        Profissao profissao;

        long idProfissao;

        System.out.print("\n" + "Insira o id da profissão" + "\n");
        servicosProfissao.listarProfissaoResumida();
        System.out.print("\n" + "Id: ");
        idProfissao = scanner.nextLong();

        if (idProfissao <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        profissao = profissaoDAO.pesquisarProfissaoId(idProfissao);

        if (profissao == null) {
            throw new TargetNotFoundExecption("não houve nenhuma correspondência");
        }

        return profissao;
    }

    public Pessoa obterPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        Pessoa pessoa;

        int opcao;

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Procurar pessoa pelo nome");
        System.out.println("2 - Procurar pessoa pelo id");

        System.out.print("\n" + "Opção: ");
        opcao = scanner.nextInt();

        if (opcao != 1 && opcao != 2) {
            throw new InvalidInputException("opcão inválida");
        }

        switch (opcao) {
            case 1 -> pessoa = obterPessoaNome();

            case 2 -> pessoa = SERVICOS_PESSOA.listarPessoaResumida(PESSOA_DAO.listarPessoa());

            default -> pessoa = null;
        }

        return pessoa;
    }
}
