package controle.metodos.menus;

import controle.metodos.pessoa.MetodosPessoa;
import controle.metodos.profissao.MetodoProfissao;
import dao.vo.PessoaVO;
import dao.vo.ProfissaoVO;
import modelo.Pessoa;
import modelo.Profissao;

import java.util.Arrays;

public abstract class MenuPrincipal {
    public static void mostrar() {
        // Testado
        System.out.println("\n" + "Escolha uma opção");
        System.out.println("1 - Incluir Profissão");
        System.out.println("2 - Alterar Profissão");
        System.out.println("3 - Excluir Profissão");
        System.out.println("4 - Listar profissões");
        System.out.println("5 - Incluir Pessoa");
        System.out.println("6 - Alterar Pessoa");
        System.out.println("7 - Excluir Pessoa");
        System.out.println("8 - Listar Pessoas / Telefones");
        System.out.println("9 - Fim");
    }

    public static void executar(int opcao) {
        PessoaVO pessoaVO = new PessoaVO();
        ProfissaoVO profissaoVO = new ProfissaoVO();
        String mensagem;

        switch (opcao) {
            case 1 -> {
                // Testada
                Profissao profissao;

                profissao = MetodoProfissao.inserirProfissao();

                mensagem = "Profissão incluída com sucesso!!!" + "\n\n" + "Nome: " + profissao.getNome().toUpperCase() + "\n"
                        + "Descrição: " + profissao.getDescricao().toUpperCase();
            }

            case 2 -> {
                // Testado
                if (profissaoVO.bancoVazio()) {
                    System.out.println("\n" + "Não há registros para serem alterados!!!");
                    return;
                }

                if (MetodoProfissao.alterarProfissao()) {
                    mensagem = "Profissão alterada com sucesso!!!";
                } else {
                    mensagem = "A profissão não pode ser alterada!!!";
                }
            }

            case 3 -> {
                // Testada
                if (profissaoVO.bancoVazio()) {
                    System.out.println("\n" + "Não há registros para serem removidos!!!");
                    return;
                }

                if (MetodoProfissao.removerProfissao()) {
                    mensagem = "Profissão removida com sucesso!!!";
                } else {
                    mensagem = "A profissão não pode ser removida!!!";
                }
            }

            case 4 -> {
                // Testado
                if (profissaoVO.bancoVazio()) {
                    System.out.println("\n" + "Não há registros para serem listados!!!");
                    return;
                }

                MetodoProfissao.listarProfissao();

                mensagem = "------------------------------";
            }

            case 5 -> {
                // Testada
                Pessoa pessoa;

                pessoa = MetodosPessoa.inserirPessoa();

                mensagem = "Pessoa incluída com sucesso!!!" + "\n\n" + "Nome: " +
                        pessoa.getNome() + "\n" + "Profissão: " + pessoa.getProfissao().getNome() + "\n" +
                        "Telefone(s): " + Arrays.toString(pessoa.getTelefone());
            }

            case 6 -> {
                if (pessoaVO.bancoVazio()) {
                    System.out.println("\n" + "Não há registros para serem alterados");
                    return;
                }

                if (MetodosPessoa.alterarPessoa()) {
                    mensagem = "Dados da pessoa alterada com sucesso!!!";
                } else {
                    mensagem = "";
                }
            }

            case 7 -> {
                // Testada
                if (pessoaVO.bancoVazio()) {
                    System.out.println("\n" + "Não há registros para serem removidos");
                    return;
                }

                if (MetodosPessoa.removerPessoa()) {
                    mensagem = "Pessoa removida com sucesso!!!";
                } else {
                    mensagem = "Essa pessoa não pode ser removida!!!";
                }
            }

            case 8 -> {
                // Testado
                if (pessoaVO.bancoVazio()) {
                    System.out.println("\n" + "Não há registros para serem listados!!!");
                    return;
                }

                MetodosPessoa.listarPessoa();

                mensagem = "------------------------------";
            }

            case 9 -> mensagem = "Encerrando programa";

            default -> mensagem = "";
        }

        System.out.println("\n" + mensagem);
    }
}
