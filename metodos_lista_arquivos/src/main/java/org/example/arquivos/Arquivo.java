package org.example.arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Arquivo {
    private String nomearquivo;
    private RandomAccessFile arquivo;
    int tamanho = 1024;
    private int comp, mov;

    public Arquivo() {
    }

    public Arquivo(int tamanho) {
        this.tamanho = tamanho;
    }

    public Arquivo(String nomearquivo) {
        try {
            this.nomearquivo = nomearquivo;
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException ignored) {
        }
    }

    public RandomAccessFile getArquivo() {
        return arquivo;
    }

    public void truncate(long pos) {
        try {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException ignored) {
        }
    }

    public boolean eof() {
        boolean retorno = false;
        try {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException ignored) {
        }
        return (retorno);
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek((long) pos * Registro.length());
        } catch (IOException ignored) {
        }
    }

    public int filesize() {
        try {
            if (arquivo.length() > 0)
                return (int) (arquivo.length() / Registro.length());
            return 0;
        } catch (IOException ignored) {
        }
        return -1;
    }

    public void initComp() {
        this.comp = 0;
    }

    public void initMov() {
        this.mov = 0;
    }

    public int getComp() {
        return comp;
    }

    public int getMov() {
        return mov;
    }

    public void insercaoDireta() {
        int i = 1, pos, tl = filesize();
        Registro reg = new Registro();
        Registro regAnt = new Registro();

        while (i < tl) {
            pos = i;
            seekArq(pos - 1);
            regAnt.leDoArq(arquivo);
            reg.leDoArq(arquivo);
            comp++;
            while (pos > 0 && reg.getNumero() < regAnt.getNumero()) {
                seekArq(pos);
                regAnt.gravaNoArq(arquivo);
                mov++;
                pos--;
                if (pos > 0) {
                    seekArq(pos - 1);
                    regAnt.leDoArq(arquivo);
                }
                comp++;
            }
            seekArq(pos);
            reg.gravaNoArq(arquivo);
            mov++;
            seekArq(++i);
            reg.leDoArq(arquivo);
        }
    }

    public void inserirRegNoFinal(Registro reg) {
        seekArq(filesize());
        reg.gravaNoArq(arquivo);
        mov++;
    }

    public void copiaArquivo(RandomAccessFile arquivoOrigem) throws IOException {
        arquivo = new Arquivo("copia.dat").getArquivo();
        truncate(0);
        arquivoOrigem.seek(0);
        while (arquivoOrigem.getFilePointer() != arquivoOrigem.length()) {
            arquivo.writeInt(arquivoOrigem.readInt());
            for (int i = 0; i < 1022; i++)
                arquivo.writeChar(arquivoOrigem.readChar());
        }
    }

    public void geraArquivoRandomico() {
        arquivo = new Arquivo("random.dat").getArquivo();
        Registro reg = new Registro();
        truncate(0);

        int[] valores = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            valores[i] = i + 1;
        }

        Random rand = new Random();
        for (int i = tamanho - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            int temp = valores[i];
            valores[i] = valores[j];
            valores[j] = temp;
        }

        for (int i = 0; i < tamanho; i++) {
            reg.setNumero(valores[i]);
            inserirRegNoFinal(reg);
        }
    }

    public void geraArquivoOrdenado() {
        arquivo = new Arquivo("ordenado.dat").getArquivo();
        if (filesize() > 0)
            truncate(0);
        for (int i = 0; i < tamanho; i++)
            inserirRegNoFinal(new Registro(i + 1));
    }

    public void geraArquivoReverso() {
        arquivo = new Arquivo("reverso.dat").getArquivo();
        if (filesize() > 0)
            truncate(0);
        for (int i = tamanho; i > 0; i--)
            inserirRegNoFinal(new Registro(i));
    }
}
