package org.example.arquivos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {
    Arquivo arq_ord, arq_rev, arq_rand, aux_rev, aux_rand;

    public Principal() {
        arq_ord = new Arquivo(10);
        arq_rev = new Arquivo(10);
        arq_rand = new Arquivo(10);
        aux_rev = new Arquivo(10);
        aux_rand = new Arquivo(10);
    }

    public void geraTabela() {
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
            System.out.println("1");
            StringBuilder linhaAtual = new StringBuilder("| Inserção Direta       | ");
            arq_ord.initComp();
            arq_ord.initMov();
            long tempoInicial = System.currentTimeMillis();
            arq_ord.insercaoDireta();
            long tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, arq_ord.filesize() - 1,
                    3 * (arq_ord.filesize() - 1));

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            tempoInicial = System.currentTimeMillis();
            aux_rev.insercaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() - 2) / 4,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() * 9 - 10) / 4);

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            tempoInicial = System.currentTimeMillis();
            aux_rand.insercaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() - 4) / 4,
                    (int) (Math.pow(arq_ord.filesize(), 2) + arq_ord.filesize() * 3 - 4) / 4);

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
        p.geraTabela();
    }
}
