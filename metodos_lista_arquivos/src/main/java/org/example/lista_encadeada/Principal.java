package org.example.lista_encadeada;

public class Principal {
    public static void main(String[] args) {
        gustavo();
    }

    public static void gustavo() {
        Lista lista = new Lista();
        lista.popular(10);
        lista.exibir("(Aleatório)");

        // lista.ordenarPorSelecaoDireta();
        // lista.exibir("(Ordenado por seleção direta)");

        // lista.ordenarPorGnome();
        // lista.exibir("(Ordenado por gnome)");

        // lista.ordenarPorCounting();
        // lista.exibir("(Ordenado por counting)");

        // lista.ordenarPorFusaoDiretaMerge1();
        // lista.exibir("(Ordenado por Fusao direta Merge 1)");

        lista.ordenarPorHeap();
        lista.exibir("(Ordenado por Heap)");
    }

    public static void fernando() {
        Lista lista = new Lista();
        lista.popular(9);
        lista.exibir("(Aleatório)");

        //lista.ordenarPorBolha();
        //lista.exibir("(Ordenado por Bolha)");

        //lista.ordenarPorInsercaoDireta();
        //lista.exibir("(Ordenado por Inserção Direta)");

        lista.ordenarPorInsercaoBinaria();
        lista.exibir("(Ordenado por Inserção Binária)");
    }
}
