package services.metodos;

import dao.entitiesVO.PessoaVO;
import dao.entitiesVO.ProfissaoVO;
import dao.entitiesVO.TelefoneVO;
import entities.Pessoa;
import entities.Profissao;
import entities.exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServicosPessoa {
    private static final PessoaVO pessoaVO = new PessoaVO();
    private static final ServicosPessoa servicosPessoa = new ServicosPessoa();
    private static final ServicosTelefone servicosTelefone = new ServicosTelefone();

    public Pessoa criarPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        Pessoa novaPessoa;
        Profissao profissao;

        String nomePessoa;
        String[] numerosTelefone;

        System.out.print("\n" + "Insira o nome da pessoa: ");
        nomePessoa = scanner.nextLine();

        if (nomePessoa.length() == 0 || nomePessoa.length() > 50) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        profissao = servicosPessoa.obterProfissao();

        numerosTelefone = servicosTelefone.criarTelefone();

        novaPessoa = new Pessoa(nomePessoa, profissao, numerosTelefone);

        pessoaVO.inserirBD(novaPessoa);

        return novaPessoa;
    }

    public boolean alterarPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        TelefoneVO telefoneVO = new TelefoneVO();

        int opcao;
        long idPessoa, idTelefone;
        String alteracao;
        String[] numerosTelefone;

        idPessoa = servicosPessoa.obterIdPessoa();

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

                int retorno = pessoaVO.alterarDB(idPessoa, opcao, alteracao);

                if (retorno == 0) {
                    throw new SqlUpdateException("não foi possível realizer a alteração dos dados");
                }
            }

            case 2 -> {
                System.out.print("\n" + "Insira uma nova profissão: ");

                alteracao = Long.toString(servicosPessoa.obterProfissao().getId());

                pessoaVO.alterarDB(idPessoa, opcao, alteracao);

                int retorno = pessoaVO.alterarDB(idPessoa, opcao, alteracao);

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
                    case 1 -> servicosTelefone.removerTelefones(idPessoa);

                    case 2 -> {
                        numerosTelefone = servicosTelefone.criarTelefone();

                        if (numerosTelefone == null) {
                            throw new InvalidInputException("são permitidos apenas valores positivos");
                        }

                        telefoneVO.inserirBD(new Object[]{numerosTelefone, idPessoa});
                    }

                    case 3 -> servicosTelefone.alterarTelefones(idPessoa);

                }
            }
        }

        return true;
    }

    public void removerPessoa() {
        // Testado
        long idPessoa;

        idPessoa = obterIdPessoa();

        int retorno = pessoaVO.removerDB(idPessoa);

        if (retorno == 0) {
            throw new SqlDeleteException("não foi possível fazer a exclusão dos dados");
        }
    }

    public void listarPessoa() {
        // Testado
        List<Pessoa> listPessoa;
        List<TelefoneVO> listTelefone;

        TelefoneVO telefoneVO = new TelefoneVO();

        String nomePessoa;
        String nomeProfissao;
        String[] numerosTelefone;

        listPessoa = pessoaVO.listarDB();

        System.out.println("\n" + "Pessoas: ");
        System.out.println("------------------------------" + "\n");
        for (Pessoa temp : listPessoa) {
            nomePessoa = temp.getNome();

            nomeProfissao = temp.getProfissao().getNome();

            listTelefone = telefoneVO.obterTelefone(temp.getId());

            if (listTelefone.size() > 0) {
                numerosTelefone = new String[listTelefone.size()];

                for (int i = 0; i < listTelefone.size(); i++) {
                    numerosTelefone[i] = listTelefone.get(i).getNumero();
                }
            } else {
                numerosTelefone = null;
            }

            System.out.printf("Nome:%s%nProfissão:%s%nTelefone(s):%s%n%n",
                    nomePessoa, nomeProfissao, Arrays.toString(numerosTelefone));
        }
    }

    private Profissao obterProfissao() {
        // Testado
        List<Profissao> listaProfissoes;

        Scanner scanner = new Scanner(System.in);

        ServicosProfissao servicosProfissao = new ServicosProfissao();
        ProfissaoVO profissaoVO = new ProfissaoVO();
        Profissao profissao;

        long idProfissao;

        System.out.print("\n" + "Insira o id da profissão" + "\n");
        servicosProfissao.listarProfissaoResumida();
        System.out.print("\n" + "Id: ");
        idProfissao = scanner.nextLong();

        if (idProfissao <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        listaProfissoes = profissaoVO.obterProfissaoId(idProfissao);

        if (listaProfissoes.size() == 0) {
            throw new TargetNotFoundExecption("não houve nenhuma correspondência");
        }

        profissao = listaProfissoes.get(0);

        return profissao;
    }

    public long obterIdPessoa() {
        // Testado
        List<Pessoa> listaPessoas;

        Scanner scanner = new Scanner(System.in);

        long idPessoa;
        String nomePessoa;

        System.out.print("\n" + "Insira o nome da pessoa procurada: ");
        nomePessoa = scanner.nextLine();

        if (nomePessoa.length() == 0 || nomePessoa.length() > 50) {
            throw new InvalidLenghtException("o número de caracteres do nome é inválido");
        }

        listaPessoas = new ArrayList<>(pessoaVO.obterPessoa(nomePessoa));

        if (listaPessoas.size() == 0) {
            throw new TargetNotFoundExecption("não houve nenhuma correspondência");
        }

        System.out.println("\n" + "Pessoas: ");
        System.out.println("------------------------------" + "\n");

        for (Pessoa temp : listaPessoas) {
            System.out.printf("Id:%d %s%n", temp.getId(), temp.getNome());
        }

        System.out.print("\n" + "Insira o id da pessoa escolhida: ");
        idPessoa = scanner.nextLong();

        if (idPessoa <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        return idPessoa;
    }
}
