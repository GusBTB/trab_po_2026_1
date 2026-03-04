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

    public No getInicio() {
        return inicio;
    }

    public No getFim() {
        return fim;
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

    public void exibir(String str) {
        No atual = inicio;
        System.out.print("Lista ");
        System.out.print(str);
        System.out.print(": ");
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
        if (inicio != null && inicio != fim) {
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

    // GUSTAVO

    public void ordenarPorHeap() {
    }

    public void ordenarPorQuickComPivo() {
    }

    public void ordenarPorQuickSemPivo() {
    }

    public void ordenarPorFusaoDiretaMerge1() {
    }

    public void ordenarPorFusaoDiretaMerge2() {
    }

    public void ordenarPorSelecaoDireta() {
        if (inicio != null && inicio != fim) {
            int menor;
            No pi = inicio, pj, pMenor;
            // ir até o penúltimo elemento
            while (pi.getProx() != null) {
                menor = pi.getInfo();
                pMenor = pi;
                // ir do proximo do atual até o último
                pj = pi.getProx();
                while (pj != null) {
                    if (pj.getInfo() < menor) {
                        menor = pj.getInfo();
                        pMenor = pj;
                    }
                    pj = pj.getProx();
                }
                pMenor.setInfo(pi.getInfo());
                pi.setInfo(menor);
                pi = pi.getProx();
            }
        }
    }

    public void ordenarPorGnome() {
        /**
         * procedure gnomeSort(a[]):
         * ----pos := 1
         * ----while pos < length(a):
         * --------if (pos == 0 or a[pos] >= a[pos-1]):
         * ------------pos := pos + 1
         * --------else:
         * ------------swap a[pos] and a[pos-1]
         * ------------pos := pos - 1
         */
        int aux;
        No atual = inicio;
        while (atual != null) {
            if (atual == inicio || atual.getInfo() >= atual.getAnt().getInfo()) {
                atual = atual.getProx();
            } else {
                // troca
                aux = atual.getInfo();
                atual.setInfo(atual.getAnt().getInfo());
                atual.getAnt().setInfo(aux);
                atual = atual.getAnt();
            }
        }
    }

    public void ordenarPorCounting() {
        // achar maior elemento
        int maior = inicio.getInfo();
        No atual = inicio, aux;
        while (atual != null) {
            if (atual.getInfo() > maior) {
                maior = atual.getInfo();
            }
            atual = atual.getProx();
        }
        Lista novaLista = new Lista();
        for (int i = 0; i <= maior; i++) {
            novaLista.inserirNoFim(0);
        }
        atual = inicio;
        while (atual != null) {
            aux = novaLista.getInicio();
            for (int i = 0; i < atual.getInfo(); i++) {
                aux = aux.getProx();
            }
            aux.setInfo(aux.getInfo() + 1);
            atual = atual.getProx();
        }
        aux = novaLista.getInicio();
        int numAtual = 0;
        atual = inicio;
        while (aux != null) {
            if (aux.getInfo() > 0) {
                // ...
            }
            numAtual++;
            aux = aux.getProx();
        }
    }

    // FERNANDO
    public void ordenarPorInsercaoDireta() {
    }

    public void ordenarPorInsercaoBinaria() {
    }

    public void ordenarPorBolha() {
    }

    public void ordenarPorShake() {
    }

    public void ordenarPorShell() {
    }

    public void ordenarPorBucket() {
    }

    public void ordenarPorRadix() {
    }

    public void ordenarPorComb() {
    }

    public void ordenarPorTim() {
    }
}