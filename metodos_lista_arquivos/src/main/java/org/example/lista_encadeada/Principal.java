package org.example.lista_encadeada;

public class Principal {
    public static void main(String[] args) {
        gustavo();
    }

    public static void gustavo() {
        Lista lista = new Lista();
        lista.popular(30);
        lista.exibir("(Aleatório)");

        // lista.ordenarPorSelecaoDireta();
        // lista.exibir("(Ordenado por seleção direta)");

        // lista.ordenarPorGnome();
        // lista.exibir("(Ordenado por gnome)");
    }

    public static void fernando() {
        // ...
    }
}
