package org.example.lista_encadeada;

import java.util.Random;

public class Lista {
    private No inicio;
    private No fim;
    private int qte_el;

    public Lista() {
        this.inicio = null;
        this.fim = null;
        this.qte_el = 0;
    }

    public void inserirNoFim(int valor) {
        No novoNo = new No(valor);

        if (inicio == null) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.setProx(novoNo);
            novoNo.setAnt(fim);
            fim = novoNo;
        }
        qte_el++;
    }

    public void exibir() {
        No atual = inicio;
        System.out.print("Lista: ");
        while (atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getProx();
        }
        System.out.println();
    }

    public void exibirOrdemReversa() {
        No atual = fim;
        System.out.print("Lista (reverso): ");
        while (atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getAnt();
        }
        System.out.println();
    }

    public void popular(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            inserirNoFim(random.nextInt(101));
        }
    }

    public void aleatorizar() {
        if(inicio != null && inicio != fim) {
            Random random = new Random();
            for (int i = qte_el - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                No noI = buscarNoPorIndice(i);
                No noJ = buscarNoPorIndice(j);
                int temp = noI.getInfo();
                noI.setInfo(noJ.getInfo());
                noJ.setInfo(temp);
            }
        }
    }

    private No buscarNoPorIndice(int indice) {
        No atual = inicio;
        for (int i = 0; i < indice && atual != null; i++) {
            atual = atual.getProx();
        }
        return atual;
    }

    public void ordenarPorInsercaoDireta() {}
    public void ordenarPorInsercaoBinaria() {}
    public void ordenarPorSelecaoDireta() {}
    public void ordenarPorBolha() {}
    public void ordenarPorShake() {}
    public void ordenarPorShell() {}
    public void ordenarPorHeap() {}
    public void ordenarPorQuickComPivo() {}
    public void ordenarPorQuickSemPivo() {}
    public void ordenarPorFusaoDireta1() {} // Implementação 1 (Merge)
    public void ordenarPorFusaoDireta2() {} // Implementação 2 (Merge)
    public void ordenarPorCounting() {}
    public void ordenarPorBucket() {}
    public void ordenarPorRadix() {}
    public void ordenarPorComb() {}
    public void ordenarPorGnome() {}
    public void ordenarPorTim() {}
}