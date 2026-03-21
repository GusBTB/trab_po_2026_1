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

    //FERNANDO
    public void heap(){
        Registro aux = new Registro();
        Registro valor = new Registro();
        int TL=filesize(),TL2=TL/2, pai, f1, f2, maiorf, valorf1,valorf2,valormf,valorpai;

        while(TL2>1)
        {
            pai=TL2/2-1;
            while(pai>0)
            {
                f1=2*pai+1;
                f2=f1+1;
                maiorf=f1;
                seekArq(f1);
                valor.leDoArq(arquivo);
                valorf1=valor.getNumero();
                seekArq(f2);
                valor.leDoArq(arquivo);
                valorf2=valor.getNumero();
                if(f2<TL && valorf2>valorf1)
                    maiorf=valorf2;
                seekArq(maiorf);
                valor.leDoArq(arquivo);
                valormf=valor.getNumero();
                seekArq(pai);
                valor.leDoArq(arquivo);
                valorpai=valor.getNumero();
                if(valormf>valorpai)
                {
                    seekArq(maiorf);
                    aux.leDoArq(arquivo);
                    seekArq(pai);
                    aux.gravaNoArq(arquivo);
                    seekArq(maiorf);
                    valor.gravaNoArq(arquivo);
                }
                pai--;
            }
            TL2--;
            seekArq(0);
            aux.leDoArq(arquivo);
            seekArq(TL2);
            valor.leDoArq(arquivo);
            seekArq(0);
            valor.gravaNoArq(arquivo);
            seekArq(TL2);
            aux.gravaNoArq(arquivo);
        }
    }

    public void quickComPivo(){ }

    public void quickSemPivo(){ }

    public void topDownMerge(){ }

    public void topDownSplitMerge(){ }

    public void fusaoDiretaMerge1(){ }

    public void fusaoDiretaMerge2(){ }

    public void selecaoDireta(){
        Registro regMenor = new Registro();
        Registro regi = new Registro();
        Registro regj = new Registro();

        int menor, posMenor, TL=filesize();

        for (int i=0;i<TL-1;i++)
        {
            seekArq(i);
            regi.leDoArq(arquivo);
            menor=regi.getNumero();
            posMenor=i;
            for (int j=i+1;j<TL;j++)
            {
                regj.leDoArq(arquivo);
                if (regj.getNumero()<menor)
                {
                    menor=regj.getNumero();
                    posMenor=j;
                }
            }
            seekArq(posMenor);
            regMenor.leDoArq(arquivo);
            seekArq(i);
            regMenor.gravaNoArq(arquivo);
            seekArq(posMenor);
            regi.gravaNoArq(arquivo);
        }
    }

    public void gnome(){ }

    public void couting(){ }
    // GUSTAVO

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

    public void insercaoBinaria() {
    }

    public void bolha() {
    }

    public void shake() {
    }

    public void shell() {
    }

    public void bucket() {
    }

    public void radix() {
    }

    public void comb() {
    }

    public void tim() {
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

    public void exibir(String flag) {
        System.out.println("\n" + flag);
        Registro aux = new Registro();
        seekArq(0);
        while (!eof()) {
            aux.leDoArq(arquivo);
            System.out.print(aux.getNumero() + " ");
        }
    }
}
