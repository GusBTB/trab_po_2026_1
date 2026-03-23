package org.example.arquivos;

import java.io.File;
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

    private void close() throws IOException {
        arquivo.close();
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

    //particiona o arquivo para o merge sort
    public void particao (Arquivo arquivo1, Arquivo arquivo2,int tl) {
        int i, meio = tl / 2;
        Registro registro = new Registro();

        seekArq(0);

        for(i = 0; i < meio; i++) {
            registro.leDoArq(arquivo);
            arquivo1.inserirNoFinal(registro);
        }

        for(i = meio; i < tl; i++) {
            registro.leDoArq(arquivo);
            arquivo2.inserirNoFinal(registro);
        }
    }

    public void heap(){
        Registro regAtual = new Registro();
        Registro regProx = new Registro();
        int TL=filesize(), pai, fe, fd, maiorf;

        while(TL>1)
        {
            for(pai=TL/2-1;pai>=0;pai--)
            {
                fe=2*pai+1;
                fd=fe+1;
                maiorf=fe;
                if (fd<TL) {
                    seekArq(fe);
                    regAtual.leDoArq(arquivo);
                    regProx.leDoArq(arquivo);

                    comp++;
                    if(regProx.getNumero()>regAtual.getNumero())
                        maiorf=fd;
                }

                seekArq(pai);
                regAtual.leDoArq(arquivo);

                seekArq(maiorf);
                regProx.leDoArq(arquivo);

                comp++;

                if(regProx.getNumero()>regAtual.getNumero())
                {
                    seekArq(maiorf);
                    regAtual.gravaNoArq(arquivo);

                    seekArq(pai);
                    regProx.gravaNoArq(arquivo);
                    mov=+2;
                }

            }
            seekArq(0);
            regAtual.leDoArq(arquivo);

            seekArq(TL-1);
            regProx.leDoArq(arquivo);

            seekArq(0);
            regProx.gravaNoArq(arquivo);

            seekArq(TL-1);
            regAtual.gravaNoArq(arquivo);

            TL--;
        }
    }

    public void quickComPivo(){
        quickcp(0,filesize()-1);
    }

    private void quickcp(int ini, int fim)
    {
        int i = ini, j=fim, pivo;
        Registro regI = new Registro();
        Registro regJ = new Registro();

        seekArq((ini+fim)/2);
        regI.leDoArq(arquivo);
        pivo=regI.getNumero();

        while(i<=j)
        {
            seekArq(i);
            regI.leDoArq(arquivo);
            if(i<fim)
            {
                comp++;
                while(regI.getNumero()<pivo)
                {
                    i++;
                    seekArq(i);
                    regI.leDoArq(arquivo);
                }
            }
            seekArq(j);
            regJ.leDoArq(arquivo);
            if(j>ini)
            {
                comp++;
                while(regJ.getNumero()>pivo)
                {
                    j--;
                    seekArq(j);
                    regJ.leDoArq(arquivo);
                }
            }
            if(i<=j)
            {
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);

                mov += 2;

                i++;
                j--;
            }
        }
        if(ini<j)
        {
            quickcp(ini, j);
        }
        if (i < fim) {
            quickcp(i, fim);
        }
    }

    public void quickSemPivo(){
        quickSP(0,filesize()-1);
    }

    private void quickSP(int ini, int fim)
    {
        int i = ini, j=fim;
        boolean flag = true;
        Registro regI = new Registro();
        Registro regJ = new Registro();

        while (i<j)
        {
            seekArq(i);
            regI.leDoArq(arquivo);

            seekArq(j);
            regJ.leDoArq(arquivo);

            if(flag)
            {
                comp++;
                while(i<j && regI.getNumero()<=regJ.getNumero())
                {
                    i++;
                    seekArq(i);
                    regI.leDoArq(arquivo);
                }
                if(i<j)
                {
                    comp++;
                }
            }
            else {
                comp++;
                while (i<j && regJ.getNumero() >= regI.getNumero())
                {
                    j--;
                    seekArq(j);
                    regJ.leDoArq(arquivo);
                }
                if(i<j)
                    comp++;
            }
            if(i != j)
            {
                seekArq(i);
                regJ.gravaNoArq(arquivo);

                seekArq(j);
                regI.gravaNoArq(arquivo);

                mov+=2;
            }
            flag=!flag;
        }
        if (ini <i-1)
            quickSP(ini, i-1);
        if(j+1<fim)
            quickSP(j+1, fim);
    }

    public void fusaoDiretaMerge2(Arquivo arq, int inicio1, int fim1, int inicio2, int fim2)
    {
        int i=inicio1, j=inicio2, k=0;
        Registro registroI = new Registro();
        Registro registroJ = new Registro();

        arq.seekArq(k);
        seekArq(i);
        registroI.leDoArq(arquivo);
        seekArq(j);
        registroJ.leDoArq(arquivo);

        while(i<=fim1 && j<=fim2)
        {
            comp++;
            if(registroI.getNumero() < registroJ.getNumero())
            {
                registroI.gravaNoArq(arq.arquivo); mov++;
                seekArq(++i);
                registroI.leDoArq(arquivo);
            }
            else {
                registroJ.gravaNoArq(arq.arquivo); mov++;
                seekArq(++j);
                registroJ.leDoArq(arquivo);
            }
            k++;
        }
        seekArq(i);
        registroI.leDoArq(arquivo);
        while(i<=fim1)
        {
            registroI.gravaNoArq(arq.arquivo);mov++;
            registroI.leDoArq(arquivo);
            i++;
            k++;
        }
        seekArq(j);
        registroJ.leDoArq(arquivo);
        while (j<=fim2)
        {
            registroJ.gravaNoArq(arq.arquivo); mov++;
            registroJ.leDoArq(arquivo);
            j++;
            k++;
        }
        seekArq(inicio1);
        arq.seekArq(0);
        for (i=0;i<k;i++)
        {
            registroI.leDoArq(arq.arquivo);
            registroI.gravaNoArq(arquivo); mov++;
        }

    }

    public void mergeSortSIRec(Arquivo arq, int esquerda, int direita){
        if (esquerda<direita)
        {
            int meio = (esquerda+direita)/2;
            mergeSortSIRec(arq,esquerda,meio);
            mergeSortSIRec(arq,meio+1,direita);
            fusaoDiretaMerge2(arq, esquerda,meio,meio+1,direita);
        }
    }

    public void mergeSortSI()throws IOException{
        int tl=filesize();
        Arquivo arquivo1 = new Arquivo("merge1.dat");
        mergeSortSIRec(arquivo1,0,tl-1);
        arquivo1.close();
        File arquivo1Delete = new File("merge1.dat");
        arquivo1Delete.delete();
    }

    public void mergeSortPI() throws IOException {
        int sequencia = 1, tl=filesize();
        Arquivo arquivo1 = new Arquivo("merge1.dat");
        Arquivo arquivo2 = new Arquivo("merge2.dat");

        while(sequencia<tl)
        {
            arquivo1.truncate(0);
            arquivo2.truncate(0);
            particao(arquivo1,arquivo2,tl);
            fusaoDiretaMerge1(arquivo1,arquivo2,tl,sequencia);
            sequencia = sequencia * 2;
        }
        arquivo1.close();
        arquivo2.close();
        File arquivo1Delete = new File("merge1.dat");
        arquivo1Delete.delete();
        File arquivo2Delete = new File("merge2.dat");
        arquivo2Delete.delete();

    }


    public void fusaoDiretaMerge1(Arquivo arquivo1, Arquivo arquivo2, int tl, int sequencia)
    {
        int i,j,k,sum = sequencia;
        Registro registroI = new Registro();
        Registro registroJ = new Registro();

        truncate(0);
        for (i=j=k=0;k<tl;)
        {
            arquivo1.seekArq(i);
            registroI.leDoArq(arquivo1.arquivo);
            arquivo2.seekArq(j);
            registroJ.leDoArq(arquivo2.arquivo);

            while(i<sequencia && j<sequencia)
            {
                comp++;
                if(registroI.getNumero()<registroJ.getNumero())
                {
                    inserirNoFinal(registroI);
                    i++;
                    registroI.leDoArq(arquivo1.arquivo);
                }
                else
                {
                    inserirNoFinal(registroJ);
                    j++;
                    registroJ.leDoArq(arquivo2.arquivo);
                }
                k++;
            }
            arquivo1.seekArq(i);
            while(i<sequencia)
            {
                registroI.leDoArq(arquivo1.arquivo);
                inserirNoFinal(registroI);
                i++;
                k++;
            }
            arquivo2.seekArq(j);
            while(j<sequencia)
            {
                registroJ.leDoArq(arquivo2.arquivo);
                inserirNoFinal(registroJ);
                j++;
                k++;
            }
            sequencia+=sum;
        }

    }



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
                comp++;
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

                mov+=2;
        }
    }

    public void gnome(){
        int i=1,TL=filesize();
        Registro regAtual = new Registro();
        Registro regAnt = new Registro();

        while(i<TL)
        {
            seekArq(i);
            regAtual.leDoArq(arquivo);

            seekArq(i-1);
            regAnt.leDoArq(arquivo);

            comp++;
            if(regAtual.getNumero()>=regAnt.getNumero())
                i++;
            else
            {
                seekArq(i-1);
                regAtual.gravaNoArq(arquivo);
                regAnt.gravaNoArq(arquivo);
                mov+=2;
                i--;
                if(i<1)
                    i=1;
            }
        }
    }

    public void couting() throws IOException {
        int TL=filesize(), maior= maiorCodigo(), pos;
        int []vet = new int[maior+1];
        Registro reg = new Registro();
        Arquivo arqAux = new Arquivo("couting.dat");

        seekArq(0);
        for (int i=0;i<TL;i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            vet[reg.getNumero()]++;
        }

        for (int i=1; i< vet.length;i++)
        {
            vet[i]+=vet[i-1];
        }

        copiarArquivo(arqAux);

        for (int i=TL-1;i>=0;i--)
        {
            arqAux.seekArq(i);
            reg.leDoArq(arqAux.getArquivo());

            pos=vet[reg.getNumero()]-1;

            seekArq(pos);
            reg.gravaNoArq(arquivo);
            mov++;

            vet[reg.getNumero()]--;
        }
        deletarArquivo("couting.dat");
    }



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
        int tl = filesize();
        Registro regI = new Registro();
        Registro regI1 = new Registro();
        boolean flag = true;
        while (tl > 1 && flag) {
            flag = false;
            for (int i = 0; i < tl - 1; i++) {
                // se vet[i] > vet[i + 1] troca e bota flag true
                // posicionar em i e ler
                // let i + 1, não precisa reposicionar pq ponteiro
                // ja vai ter andado
                seekArq(i);
                regI.leDoArq(arquivo);
                regI1.leDoArq(arquivo);
                comp++;
                if (regI.getNumero() > regI1.getNumero()) {
                    seekArq(i);
                    regI1.gravaNoArq(arquivo);
                    regI.gravaNoArq(arquivo);
                    flag = true;
                    mov += 2;
                }
            }
            tl--;
        }
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

    private int maiorCodigo()
    {
        int TL=filesize();
        int maior=0;
        Registro reg = new Registro();

        seekArq(0);
        for(int i=0;i<TL;i++)
        {
            reg.leDoArq(arquivo);
            comp ++;
            if(reg.getNumero()>maior)
                maior=reg.getNumero();

        }
        return maior;
    }

    public void copiarArquivo(Arquivo arqDestino) throws IOException{
        arqDestino.truncate(0);

        Registro reg=new Registro();
        for (int i = 0; i < filesize(); i++) {
            seekArq(i);
            reg.leDoArq(this.arquivo);

            arqDestino.seekArq(i);
            reg.gravaNoArq(arqDestino.getArquivo());
        }
    }

    public void deletarArquivo(String nomeArquivo) {
        File arq = new File(nomeArquivo);
        arq.delete();
    }

    private void inserirNoFinal(Registro registro) {
        seekArq(filesize());
        registro.gravaNoArq(arquivo);
        mov++;
    }
}
