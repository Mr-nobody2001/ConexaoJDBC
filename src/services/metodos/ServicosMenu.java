package services.metodos;

import dao.entitiesVO.PessoaVO;
import dao.entitiesVO.ProfissaoVO;
import entities.Pessoa;
import entities.Profissao;
import entities.exceptions.*;

import java.util.InputMismatchException;

import java.util.Arrays;

public abstract class ServicosMenu {
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

        ServicosProfissao servicosProfissao = new ServicosProfissao();

        try {
            switch (opcao) {
                case 1 -> {
                    // Testada
                    Profissao profissao;

                    profissao = servicosProfissao.criarProfissao();

                    mensagem = "Profissão incluída com sucesso!!!" + "\n\n" + "Nome: " + profissao.getNome().toUpperCase() + "\n"
                            + "Descrição: " + profissao.getDescricao().toUpperCase();
                }

                case 2 -> {
                    // Testado
                    if (profissaoVO.bancoVazio()) {
                        throw new NotDataException("não há registros para serem alterados");
                    }

                    servicosProfissao.alterarProfissao();

                    mensagem = "Profissão alterada com sucesso!!!";
                }

                case 3 -> {
                    // Testada
                    if (profissaoVO.bancoVazio()) {
                        throw new NotDataException("não há registros para serem removidos");
                    }

                    servicosProfissao.removerProfissao();

                    mensagem = "Profissão removida com sucesso!!!";

                }

                case 4 -> {
                    // Testado
                    if (profissaoVO.bancoVazio()) {
                        throw new NotDataException("não há registros para serem listados");
                    }

                    servicosProfissao.listarProfissaoExtenso();

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

        } catch (InvalidLenghtException | InvalidInputException | TargetNotFoundExecption | InputMismatchException e) {
            System.out.println("\nErro de input: " + e.getMessage());
        } catch (SqlUpdateException | SqlDeleteException e) {
            System.out.println("\nErro de banco de dados: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}