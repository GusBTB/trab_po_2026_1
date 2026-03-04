package org.example;

import org.example.lista_encadeada.Lista;

public class Main {
    public static void main(String[] args) {
        Lista lista = new Lista();
        lista.popular(30);
        lista.exibir("(Aleatório)");

        // lista.ordenarPorSelecaoDireta();
        // lista.exibir("(Ordenado por seleção direta)");

        // lista.ordenarPorGnome();
        // lista.exibir("(Ordenado por gnome)");
    }
}