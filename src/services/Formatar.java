package services;

public class Formatar {
    public static String formatarNome(String nome) {
        String nomeFormatado;
        String[] nomesBrutos;

        nomeFormatado = "";
        nomesBrutos = nome.split(" ");

        for (String temp : nomesBrutos) {
            nomeFormatado += temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase() + " ";
        }

        return nomeFormatado;
    }
}
