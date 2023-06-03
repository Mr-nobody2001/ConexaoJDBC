package services.metodos;

import dao.entitiesVO.PessoaVO;
import dao.entitiesVO.ProfissaoVO;
import dao.entitiesVO.TelefoneVO;
import entities.Pessoa;
import entities.Profissao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class MetodosPessoa {

    private static final PessoaVO pessoaVO = new PessoaVO();

    public static Pessoa inserirPessoa() {
        // Testado
        Pessoa pessoaRetorno;

        pessoaRetorno = criarObjetoPessoa();

        pessoaVO.inserirBD(pessoaRetorno);

        return pessoaRetorno;
    }

    private static Pessoa criarObjetoPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        Profissao profissao;
        Profissao profissaoPessoa;

        String nomePessoa;
        String[] numerosTelefone;

        System.out.print("\n" + "Insira o nome da pessoa: ");
        nomePessoa = scanner.nextLine();

        while (nomePessoa.length() == 0 || nomePessoa.length() > 50) {
            System.out.print("\n" + "Número de caracteres inválido. Insira outro nome: ");
            nomePessoa = scanner.next();
        }

        profissao = MetodosPessoa.pesquisarProfissao();

        profissaoPessoa = new Profissao();

        profissaoPessoa.setId(profissao.getId());
        profissaoPessoa.setNome(profissao.getNome());
        profissaoPessoa.setDescricao(profissao.getDescricao());

        numerosTelefone = MetodosPessoa.obterTelefones();

        return new Pessoa(nomePessoa, profissaoPessoa, numerosTelefone);
    }

    private static String[] obterTelefones() {
        int quantidadeNumerosTelefone;
        String[] numerosTelefone;
        boolean teste;

        numerosTelefone = null;

        System.out.print("\n" + "Insira a quantidade de números de telefone que serão cadastrados: ");

        do {
            teste = false;

            quantidadeNumerosTelefone = Tratatamento.tratarErroTipoInputNumericoInteiro();

            if (quantidadeNumerosTelefone > 20) {
                System.out.println("\n" + "Número máximo de números de telefone excedido. Insira um valor menor!!!");
                teste = true;
            }
        } while (teste);

        if (quantidadeNumerosTelefone > 0) {
            numerosTelefone = new String[quantidadeNumerosTelefone];

            for (int i = 0; i < quantidadeNumerosTelefone; i++) {
                System.out.print("\n" + "Insira o telefone " + (i + 1) + " (com DDD) : ");
                numerosTelefone[i] = verificarFormatoTelefone();
            }
        }

        return numerosTelefone;
    }

    private static String verificarFormatoTelefone() {
        Scanner scanner = new Scanner(System.in);

        String numeroTelefone;

        numeroTelefone = scanner.next();

        while (numeroTelefone.length() != 11) {
            System.out.print("Número de telefone inválido. Insira novamente: ");
            numeroTelefone = scanner.next();
        }

        /*"(" + numeroTelefone.substring(0, 2) + ")" + numeroTelefone.substring(2, 7) + "-" +
                numeroTelefone.substring(7)*/

        return numeroTelefone;
    }

    public static boolean removerPessoa() {
        // Testado
        long idPessoa;
        boolean retorno;

        idPessoa = pesquisarPessoa();

        retorno = pessoaVO.removerDB(idPessoa) == 1;

        return retorno;
    }

    private static Profissao pesquisarProfissao() {
        // Testado
        List<Profissao> list;
        ServicosProfissao servicosProfissao = new ServicosProfissao();
        ProfissaoVO profissaoVO;
        Profissao profissao;

        long idProfissao;
        boolean profissaoEncontrada;

        profissaoVO = new ProfissaoVO();
        profissao = null;

        do {
            profissaoEncontrada = false;

            System.out.print("\n" + "Insira o id da profissão" + "\n");
            servicosProfissao.listarProfissaoResumida();
            System.out.print("\n" + "Id: ");
            idProfissao = Tratatamento.tratarErroTipoInputNumericoLong();
            list = profissaoVO.obterProfissaoId(idProfissao);

            if (list.size() > 0) {
                profissao = list.get(0);
                profissaoEncontrada = true;
            } else {
                System.out.println("Profissão não encontrada!!!");
            }

        } while (!profissaoEncontrada);

        return profissao;
    }

    private static long pesquisarPessoa() {
        // Testado
        List<Pessoa> list;

        Scanner scanner;

        scanner = new Scanner(System.in);

        long idPessoa;
        String nomePessoa;
        boolean listaVazia;

        do {
            listaVazia = true;

            System.out.print("\n" + "Insira o nome da pessoa procurada: ");
            nomePessoa = scanner.nextLine();

            list = new ArrayList<>(pessoaVO.obterPessoa(nomePessoa));

            if (list.size() != 0) {
                listaVazia = false;
            } else {
                System.out.println("\n" + "Não houve nenhuma correspondência!!!");
            }
        } while (listaVazia);

        System.out.println("\n" + "Pessoas: ");
        System.out.println("------------------------------" + "\n");

        for (Pessoa temp : list) {
            System.out.printf("Id:%d %s%n", temp.getId(), temp.getNome());
        }

        System.out.print("\n" + "Insira o id da pessoa escolhida: ");
        idPessoa = Tratatamento.tratarErroTipoInputNumericoLong();

        return idPessoa;
    }

    public static boolean alterarPessoa() {
        // Testado
        Scanner scanner = new Scanner(System.in);

        TelefoneVO telefoneVO;

        int opcaoAlterar;
        long idPessoa, idTelefone;
        String alteracao;
        String[] numerosTelefone;

        telefoneVO = new TelefoneVO();

        idPessoa = MetodosPessoa.pesquisarPessoa();

        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Alterar o nome da pessoa");
        System.out.println("2 - Alterar a profissão da pessoa");
        System.out.println("3 - Alterar o número de telefone da pessoa");

        System.out.print("\n" + "Opção: ");
        opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();

        while (opcaoAlterar < 1 || opcaoAlterar > 3) {
            System.out.print("\n" + "Opcão inválida. Insira outro valor: ");
            opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();
        }

        switch (opcaoAlterar) {
            case 1 -> {
                System.out.print("\n" + "Insira o novo nome: ");

                alteracao = scanner.nextLine();

                pessoaVO.alterarDB(idPessoa, opcaoAlterar, alteracao);
            }

            case 2 -> {
                System.out.print("\n" + "Insira uma nova profissão: ");

                alteracao = Long.toString(MetodosPessoa.pesquisarProfissao().getId());

                pessoaVO.alterarDB(idPessoa, opcaoAlterar, alteracao);
            }

            case 3 -> {
                System.out.println("\n" + "Escolha uma opção");
                System.out.println("1 - Remover um número de telefone");
                System.out.println("2 - Adicionar um número de telefone");
                System.out.println("3 - Alterar um número de telefone já existente");

                System.out.print("\n" + "Opção: ");
                opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();

                while (opcaoAlterar < 1 || opcaoAlterar > 3) {
                    System.out.print("\n" + "Opcão inválida. Insira outro valor: ");
                    opcaoAlterar = Tratatamento.tratarErroTipoInputNumericoInteiro();
                }

                switch (opcaoAlterar) {
                    case 1 -> {
                        if (MetodosPessoa.listarTelefoneInterna(idPessoa)) {
                            System.out.print("\n" + "Insira o id do telefone que será removido: ");
                            idTelefone = Tratatamento.tratarErroTipoInputNumericoLong();

                            telefoneVO.removerDBExclusaoTelefone(idPessoa, idTelefone);
                        } else {
                            return false;
                        }
                    }

                    case 2 -> {
                        numerosTelefone = MetodosPessoa.obterTelefones();

                        while (numerosTelefone == null) {
                            System.out.print("\n" + "Valor inválido. Insira um valor positivo!!!" + "\n");
                            numerosTelefone = MetodosPessoa.obterTelefones();
                        }

                        telefoneVO.inserirBD(new Object[]{numerosTelefone, idPessoa});
                    }

                    case 3 -> {
                        if (MetodosPessoa.listarTelefoneInterna(idPessoa)) {
                            System.out.print("\n" + "Insira o id do telefone que será modificado: ");
                            idTelefone = Tratatamento.tratarErroTipoInputNumericoLong();

                            System.out.print("\n" + "Insira um novo número de telefone: ");
                            alteracao = MetodosPessoa.verificarFormatoTelefone();

                            telefoneVO.alterarDB(idTelefone, alteracao);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean listarTelefoneInterna(long idPessoa) {
        List<TelefoneVO> list;

        TelefoneVO telefoneVO;

        telefoneVO = new TelefoneVO();

        if (telefoneVO.bancoVazio(idPessoa)) {
            System.out.println("\n" + "Não há registros para serem alterados");
            return false;
        }

        list = telefoneVO.obterTelefone(idPessoa);

        System.out.println("\n" + "Telefones: ");
        System.out.println("------------------------------" + "\n");

        for (TelefoneVO temp : list) {
            System.out.printf("Id:%d %s%n", temp.getId(), temp.getNumero());
        }

        return true;
    }

    public static void listarPessoa() {
        // Testado
        List<Pessoa> listPessoa;
        List<TelefoneVO> listTelefone;

        TelefoneVO telefoneVO;

        String nomePessoa;
        String nomeProfissao;
        String[] numerosTelefone;

        listPessoa = pessoaVO.listarDB();

        telefoneVO = new TelefoneVO();

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
}
