package org.example;

import org.example.lista_encadeada.Lista;

public class Main {
    public static void main(String[] args) {
        Lista lista = new Lista();
        lista.popular(30);
        lista.exibir();
        lista.aleatorizar();
        lista.exibir();
    }
}