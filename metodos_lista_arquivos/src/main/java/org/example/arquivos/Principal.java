package org.example.arquivos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Principal {
    Arquivo arq_ord, arq_rev, arq_rand, aux_rev, aux_rand;

    public Principal() {
        arq_ord = new Arquivo(1024);
        arq_rev = new Arquivo(1024);
        arq_rand = new Arquivo(1024);
        aux_rev = new Arquivo(1024);
        aux_rand = new Arquivo(1024);
    }

    public void geraTabela(boolean mostrarLog) {
        arq_ord.geraArquivoOrdenado();
        arq_rev.geraArquivoReverso();
        arq_rand.geraArquivoRandomico();

        String nomeArquivo = "tabela.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
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

            long tempoInicial;
            long tempoFinal;
            StringBuilder linhaAtual;

            int n = arq_ord.filesize();
            int n2 = n * n;

            // Fórmulas
            int insDirCompOrd = n - 1;
            int insDirMovOrd = n - 1;
            int insDirCompRev = (n2 + n - 2) / 2;
            int insDirMovRev = (n2 + n - 2) / 2;
            int insDirCompRand = (n2 + 3 * n - 4) / 4;
            int insDirMovRand = (n2 + 3 * n - 4) / 4;

            int insBinComp = 0;
            for (int i = 1; i < n; i++) {
                insBinComp += (int) Math.ceil(Math.log(i + 1) / Math.log(2));
            }
            int insBinMovOrd = 0;
            int insBinMovRev = (n2 + n - 2) / 2;
            int insBinMovRand = (n2 + 3 * n - 4) / 4;

            int selComp = n * (n - 1) / 2;
            int selMov = 2 * (n - 1);

            int bolhaShakeCompOrd = n - 1;
            int bolhaShakeMovOrd = 0;
            int bolhaShakeCompRev = n * (n - 1) / 2;
            int bolhaShakeMovRev = n * (n - 1);
            int bolhaShakeCompRand = n * (n - 1) / 2;
            int bolhaShakeMovRand = n * (n - 1) / 2;

            // 1) Inserção Direta
            if (mostrarLog)
                System.out.println("1: Inserção Direta");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Inserção Direta"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.insercaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, insDirCompOrd, insDirMovOrd);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.insercaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, insDirCompRev, insDirMovRev);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.insercaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, insDirCompRand,
                    insDirMovRand);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 2) Inserção Binária
            if (mostrarLog)
                System.out.println("\n\n2: Inserção Binária");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Inserção Binária"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.insercaoBinaria();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, insBinComp, insBinMovOrd);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.insercaoBinaria();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, insBinComp, insBinMovRev);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.insercaoBinaria();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, insBinComp, insBinMovRand);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 3) Seleção
            if (mostrarLog)
                System.out.println("\n\n3: Seleção");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Seleção"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.selecaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, selComp, selMov);
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
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, selComp, selMov);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.selecaoDireta();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, selComp, selMov);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 4) Bolha
            if (mostrarLog)
                System.out.println("\n\n4: Bolha");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Bolha"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.bolha();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, bolhaShakeCompOrd,
                    bolhaShakeMovOrd);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.bolha();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, bolhaShakeCompRev,
                    bolhaShakeMovRev);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.bolha();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, bolhaShakeCompRand,
                    bolhaShakeMovRand);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 5) Shake
            if (mostrarLog)
                System.out.println("\n\n5: Shake");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Shake"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.shake();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, bolhaShakeCompOrd,
                    bolhaShakeMovOrd);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.shake();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, bolhaShakeCompRev,
                    bolhaShakeMovRev);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.shake();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, bolhaShakeCompRand,
                    bolhaShakeMovRand);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 6) Shell
            if (mostrarLog)
                System.out.println("\n\n6: Shell");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Shell"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.shell();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.shell();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.shell();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 7) Heap
            if (mostrarLog)
                System.out.println("\n\n7: Heap");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Heap"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.heap();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.heap();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.heap();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 8) Quick s/ pivô
            if (mostrarLog)
                System.out.println("\n\n8: Quick s/ pivô");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Quick s/ pivô"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.quickSemPivo();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.quickSemPivo();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.quickSemPivo();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 9) Quick c/ pivô
            if (mostrarLog)
                System.out.println("\n\n9: Quick c/ pivô");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Quick c/ pivô"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.quickComPivo();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.quickComPivo();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.quickComPivo();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 10) Merge 1ª Implement
            if (mostrarLog)
                System.out.println("\n\n10: Merge 1ª Implement");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Merge 1ª Implement"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.mergeSortPI();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.mergeSortPI();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.mergeSortPI();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 11) Merge 2ª Implement
            if (mostrarLog)
                System.out.println("\n\n11: Merge 2ª Implement");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Merge 2ª Implement"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.mergeSortSI();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.mergeSortSI();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.mergeSortSI();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 12) Counting
            if (mostrarLog)
                System.out.println("\n\n12: Counting");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Counting"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.counting();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.counting();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.counting();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 13) Bucket
            if (mostrarLog)
                System.out.println("\n\n13: Bucket");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Bucket"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.bucket();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.bucket();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.bucket();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 14) Radix
            if (mostrarLog)
                System.out.println("\n\n14: Radix");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Radix"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.radix();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.radix();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.radix();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 15) Comb
            if (mostrarLog)
                System.out.println("\n\n15: Comb");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Comb"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.comb();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.comb();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.comb();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 16) Gnome
            if (mostrarLog)
                System.out.println("\n\n16: Gnome");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Gnome"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.gnome();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.gnome();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.gnome();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

            // 17) Tim
            if (mostrarLog)
                System.out.println("\n\n17: Tim");
            linhaAtual = new StringBuilder(String.format("| %-21s | ", "Tim"));

            arq_ord.initComp();
            arq_ord.initMov();
            tempoInicial = System.currentTimeMillis();
            arq_ord.tim();
            tempoFinal = System.currentTimeMillis();
            concatenarOrdenado(linhaAtual, (tempoFinal - tempoInicial) / 1000, arq_ord, 0, 0);
            if (mostrarLog)
                arq_ord.exibir("(Arquivo ordenado)");

            aux_rev.initComp();
            aux_rev.initMov();
            aux_rev.copiaArquivo(arq_rev.getArquivo());
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rev.tim();
            tempoFinal = System.currentTimeMillis();
            concatenarReverso(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rev, 0, 0);
            if (mostrarLog)
                aux_rev.exibir("(Arquivo reverso ordenado)");

            aux_rand.initComp();
            aux_rand.initMov();
            aux_rand.copiaArquivo(arq_rand.getArquivo());
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico não ordenado)");
            tempoInicial = System.currentTimeMillis();
            aux_rand.tim();
            tempoFinal = System.currentTimeMillis();
            concatenarRandomico(linhaAtual, (tempoFinal - tempoInicial) / 1000, aux_rand, 0, 0);
            if (mostrarLog)
                aux_rand.exibir("(Arquivo randômico ordenado)");

            writer.write(linhaAtual.toString());
            quebraLinha(writer);

        } catch (IOException e) {
            e.printStackTrace();
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
        p.geraTabela(false);
    }
}
