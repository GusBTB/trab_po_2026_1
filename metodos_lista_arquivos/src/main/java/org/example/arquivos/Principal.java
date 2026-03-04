package org.example.arquivos;

public class Principal {
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
 ...
    public void geraTabela()
    {
        arqOrd.geraArquivoOrdenado();
        arqRev.geraArquivoReverso();
        arqRand.geraArquivoRandomico();
        //... Insercao Direta ...
        //Arquivo Ordenado
        arqOrd.initComp();
        arqOrd.initMov();
        tini=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        arqOrd.isercaoDireta();
        tfim=System.currentTimeMillis(); //método para pegar a hora atual em milisegundos
        compO=arqOrd.getComp();
        movO=arqOrd.getMov();
        ttotalO=tfim-tini;
        //Arquivo Reverso
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev
        //para auxRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tini=System.currentTimeMillis();
        auxRev.isercaoDireta();
        tfim=System.currentTimeMillis();
        ttotalRev=tfim-tini;
        compRev=auxRev.getComp();
        movRev= auxRev.getMov();
        4
        //Arquivo Randomico
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand
        //para auxRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tini=System.currentTimeMillis();
        auxRand.isercaoDireta();
        tfim=System.currentTimeMillis();
        ttotalRand=tfim-tini;
        compRand=auxRand.getComp();
        movRand=auxRand.getMov();
        //grava na tabela informacoes os dados extraídos das execucoes do método
        //Insercao Direta
        gravaLinhaTabela(compO,
                calculaCompInsDir(filesize()),
                movO,
                calculaMovInsDir(filesize()),
                ttotalO, //tempo execução no arquivo Ordenado já convertido
                //para segundos
 ...
 )
        //... fim Insercao Direta
        //e assim continua para os outros métodos de ordenacao!!!

}
