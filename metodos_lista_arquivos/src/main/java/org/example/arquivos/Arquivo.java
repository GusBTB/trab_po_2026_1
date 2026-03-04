package org.example.arquivos;

import java.io.RandomAccessFile;

public class Arquivo {
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;
    public Arquivo(String nomearquivo) {}

    public void copiaArquivo(RandomAccessFile arquivoOrigem){}
    public RandomAccessFile getFile() { return null;}
    public void truncate(long pos) {}
    public boolean eof() {return false;}
    public void seekArq(int pos) {}
    public void filesize() {}
    public void initComp() {}
    public void initMov() {}
    public int getComp() {return 0;}
    public int getMov() {return 0;}
    public void insercaoDireta() {}
    //demais metodos de ordenacao
    public void geraArquivoOrdenado() {}
    public void geraArquivoReverso() {}
    public void geraArquivoRandomico() {}
}
