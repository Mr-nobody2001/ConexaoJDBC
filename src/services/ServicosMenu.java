package services;

import entities.dao.PessoaDAO;
import entities.dao.ProfissaoDAO;
import entities.dao.impl.DAOFactory;
import entities.Pessoa;
import entities.Profissao;
import entities.Telefone;
import entities.exceptions.*;

import java.util.InputMismatchException;

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
        ServicosProfissao servicosProfissao = new ServicosProfissao();
        ServicosPessoa servicosPessoa = new ServicosPessoa();
        PessoaDAO pessoaDao = DAOFactory.criarPessoaDao();
        ProfissaoDAO profissaoDAO = DAOFactory.criarProfissaoDao();

        String mensagem;

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
                    if (profissaoDAO.bancoVazio()) {
                        throw new NotDataException("não há registros para serem alterados");
                    }

                    servicosProfissao.alterarProfissao();

                    mensagem = "Profissão alterada com sucesso!!!";
                }

                case 3 -> {
                    // Testada
                    if (profissaoDAO.bancoVazio()) {
                        throw new NotDataException("não há registros para serem removidos");
                    }

                    servicosProfissao.removerProfissao();

                    mensagem = "Profissão removida com sucesso!!!";

                }

                case 4 -> {
                    // Testado
                    if (profissaoDAO.bancoVazio()) {
                        throw new NotDataException("não há registros para serem listados");
                    }

                    servicosProfissao.listarProfissaoExtenso();

                    mensagem = "------------------------------";
                }

                case 5 -> {
                    // Testada
                    Pessoa pessoa;

                    pessoa = servicosPessoa.criarPessoa();

                    String numeros = "";

                    for (Telefone temp : pessoa.getTelefones()) {
                        numeros += "(" + temp.getNumero().substring(0, 2) + ")" + temp.getNumero().substring(2, 7) +
                                "-" + temp.getNumero().substring(7) + " ";
                    }

                    mensagem = "Pessoa incluída com sucesso!!!" + "\n\n" + "Nome: " +
                            pessoa.getNome() + "\n" + "Profissão: " + pessoa.getProfissao().getNome() + "\n" +
                            "Telefone(s): " + numeros;
                }

                case 6 -> {
                    if (pessoaDao.bancoVazio()) {
                        throw new NotDataException("não há registros para serem alterados");
                    }

                    servicosPessoa.alterarPessoa();

                    mensagem = "Dados da pessoa alterada com sucesso!!!";
                }

                case 7 -> {
                    // Testada
                    if (pessoaDao.bancoVazio()) {
                        throw new NotDataException("não há registros para serem removidos");
                    }

                    servicosPessoa.removerPessoa();

                    mensagem = "Pessoa removida com sucesso!!!";
                }

                case 8 -> {
                    // Testado
                    if (pessoaDao.bancoVazio()) {
                        throw new NotDataException("não há registros para serem listados");
                    }

                    servicosPessoa.listarPessoaExtenso();

                    mensagem = "------------------------------";
                }

                case 9 -> mensagem = "Encerrando programa";

                default -> mensagem = "";
            }

            System.out.println("\n" + mensagem);

        } catch (InvalidLenghtException | InvalidInputException | TargetNotFoundExecption | InputMismatchException e) {
            System.out.println("\nErro de input: " + e.getMessage());
        } catch (SqlInsertException | SqlUpdateException | SqlDeleteException | NotDataException | IntegrityConstraintException e) {
            System.out.println("\nErro de banco de dados: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
