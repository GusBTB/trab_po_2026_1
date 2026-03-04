package org.example.arquivos;

import java.io.RandomAccessFile;

public class Arquivo {
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;
    public Arquivo(String nomearquivo) {}
3
    public void copiaArquivo(RandomAccessFile arquivoOrigem){}
    public RandomAccessFile getFile() {}
    public void truncate(long pos) {}
    public boolean eof() {}
    public void seekArq(int pos) {}
    public void filesize() {}
    public void initComp() {}
    public void initMov() {}
    public int getComp() {}
    public int getMov() {}
    public void insercaoDireta() {}
    //demais metodos de ordenacao
    public void geraArquivoOrdenado() {}
    public void geraArquivoReverso() {}
    public void geraArquivoRandomico() {}
}
