package org.example.arquivos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {
    Arquivo arq_ord, arq_rev, arq_rand, aux_rev, aux_rand;

    public Principal() {
        arq_ord = new Arquivo(6);
        arq_rev = new Arquivo(6);
        arq_rand = new Arquivo(6);
        aux_rev = new Arquivo(6);
        aux_rand = new Arquivo(6);
    }

    public void geraTabela(boolean mostrarLog) {
        arq_ord.geraArquivoOrdenado();
        arq_rev.geraArquivoReverso();
        arq_rand.geraArquivoRandomico();

        String nomeArquivo = "tabela.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Cabeçalho
            writer.write(
                    "|¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|\n");
            writer.write(
                    "| Métodos de Ordenação  |                               Arquivo Ordenado                                |                             Arquivo em Ordem Reversa                           |                               Arquivo Randômico                               |\n");
            writer.write(
                    "| ----------------------|-------------------------------------------------------------------------------|--------------------------------------------------------------------------------|-------------------------------------------------------------------------------|\n");
            writer.write(
                    "| Nomes dos Métodos     | Comp. Prog. * | Comp. Equa. # | Mov. Prog. + | Mov. Equa. - |      Tempo      | Comp. Prog. * | Comp. Equa. # | Mov. Prog. + | Mov. Equa. - |      Tempo       | Comp. Prog. * | Comp. Equa. # | Mov. Prog. + | Mov. Equa. - |      Tempo      |\n");
            writer.write(
                    "|-----------------------|---------------|---------------|--------------|--------------|-----------------|---------------|---------------|--------------|--------------|------------------|---------------|---------------|--------------|--------------|-----------------|\n");

            // Inserção Direta
            if (mostrarLog)
                System.out.println("1: selecao direta");
            StringBuilder linhaAtual = new StringBuilder("| selecao Direta       | ");
            arq_ord.initComp();
            arq_ord.initMov();
            long tempoInicial = System.currentTimeMillis();
            arq_ord.selecaoDireta();
            long tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, arq_ord.filesize() - 1,
                    3 * (arq_ord.filesize() - 1));
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.selecaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() - 2) / 4,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() * 9 - 10) / 4);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo random não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.selecaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() - 4) / 4,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() * 3 - 4) / 4);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo random ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

        } catch (IOException e) {
        }
    }

    public static void concatenarOrdenado(StringBuilder linhaAtual, long tempo, Arquivo arq, int compEqua,
            int movEqua) {
        linhaAtual.append(arq.getComp());
        linhaAtual.append(" ".repeat(Math.max(0, 39 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(compEqua);
        linhaAtual.append(" ".repeat(Math.max(0, 55 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(arq.getMov());
        linhaAtual.append(" ".repeat(Math.max(0, 70 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(movEqua);
        linhaAtual.append(" ".repeat(Math.max(0, 85 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(tempo);
        linhaAtual.append(" ".repeat(Math.max(0, 103 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");
    }

    public static void concatenarReverso(StringBuilder linhaAtual, long tempo, Arquivo arq, int compEqua, int movEqua) {
        linhaAtual.append(arq.getComp());
        linhaAtual.append(" ".repeat(Math.max(0, 119 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(compEqua);
        linhaAtual.append(" ".repeat(Math.max(0, 135 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(arq.getMov());
        linhaAtual.append(" ".repeat(Math.max(0, 150 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(movEqua);
        linhaAtual.append(" ".repeat(Math.max(0, 165 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(tempo);
        linhaAtual.append(" ".repeat(Math.max(0, 184 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");
    }

    public static void concatenarRandomico(StringBuilder linhaAtual, long tempo, Arquivo arq, int compEqua,
            int movEqua) {
        linhaAtual.append(arq.getComp());
        linhaAtual.append(" ".repeat(Math.max(0, 200 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(compEqua);
        linhaAtual.append(" ".repeat(Math.max(0, 216 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(arq.getMov());
        linhaAtual.append(" ".repeat(Math.max(0, 231 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(movEqua);
        linhaAtual.append(" ".repeat(Math.max(0, 246 - (linhaAtual.length() - 1))));
        linhaAtual.append("| ");

        linhaAtual.append(tempo);
        linhaAtual.append(" ".repeat(Math.max(0, 264 - (linhaAtual.length() - 1))));
        linhaAtual.append("|\n");
    }

    public static void quebraLinha(BufferedWriter writer) throws IOException {
        writer.write(
                "|_______________________|_______________________________________________________________________________|________________________________________________________________________________|_______________________________________________________________________________|\n");
    }

    public static void main(String[] args) {
        Principal p = new Principal();
        p.geraTabela(true);
    }
}
