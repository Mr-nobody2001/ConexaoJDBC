package services;

import dao.entitiesVO.TelefoneVO;
import entities.Pessoa;
import entities.Telefone;
import entities.exceptions.InvalidInputException;
import entities.exceptions.NotDataException;
import entities.exceptions.SqlDeleteException;
import entities.exceptions.SqlUpdateException;

import java.util.List;
import java.util.Scanner;

public class ServicosTelefone {
    private static final TelefoneVO telefoneVO = new TelefoneVO();

    public void criarTelefone(Pessoa pessoa) {
        Scanner scanner = new Scanner(System.in);

        int quantidadeNumeros;

        System.out.print("\n" + "Insira a quantidade de números de telefone que serão cadastrados: ");
        quantidadeNumeros = scanner.nextInt();

        if (quantidadeNumeros < 0 || quantidadeNumeros > 10) {
            throw new InvalidInputException("quantidade de números de telefone inválida. A quantidade máxima é 10");
        }

        if (quantidadeNumeros > 0) {
            for (int i = 0; i < quantidadeNumeros; i++) {
                System.out.print("\n" + "Insira o telefone " + (i + 1) + " (com DDD) : ");
                pessoa.getTelefones().add(new Telefone(verificarFormatoTelefone()));
            }
        }
    }

    public void alterarTelefones(long idPessoa) {
        Scanner scanner = new Scanner(System.in);

        long idTelefone;
        String alteracao;

        listarTelefones(idPessoa);

        System.out.print("\n" + "Insira o id do telefone que será modificado: ");
        idTelefone = scanner.nextLong();

        if (idTelefone <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        System.out.print("\n" + "Insira um novo número de telefone: ");
        alteracao = verificarFormatoTelefone();

        int retorno = telefoneVO.alterarDB(idTelefone, alteracao);

        if (retorno == 0) {
            throw new SqlUpdateException("não foi possível fazer a alteração dos dados");
        }
    }

    public void removerTelefones(long idPessoa) {
        Scanner scanner = new Scanner(System.in);

        TelefoneVO telefoneVO = new TelefoneVO();

        long idTelefone;

        listarTelefones(idPessoa);

        System.out.print("\n" + "Insira o id do telefone que será removido: ");
        idTelefone = scanner.nextLong();

        if (idTelefone <= 0) {
            throw new InvalidInputException("valor de id inválido");
        }

        int retorno = telefoneVO.removerDbEspeficico(idPessoa, idTelefone);

        if (retorno == 0) {
            throw new SqlDeleteException("não foi possível fazer a exclusão dos dados");
        }
    }

    public void listarTelefones(long idPessoa) {
        List<Telefone> listaTelefones;

        TelefoneVO telefoneVO = new TelefoneVO();

        if (telefoneVO.bancoVazio(idPessoa)) {
            throw new NotDataException("não há registros para serem alterados");
        }

        listaTelefones = telefoneVO.obterTelefone(idPessoa);

        System.out.println("\n" + "Telefones: ");
        System.out.println("------------------------------" + "\n");

        for (Telefone temp : listaTelefones) {
            System.out.printf("Id:%d %s%n", temp.getId(), temp.getNumero());
        }
    }

    public String verificarFormatoTelefone() {
        Scanner scanner = new Scanner(System.in);

        String numeroTelefone;

        numeroTelefone = scanner.next();

        while (numeroTelefone.length() != 11) {
            System.out.print("Número de telefone inválido. Insira novamente: ");
            numeroTelefone = scanner.next();
        }

        return numeroTelefone;
    }
}
