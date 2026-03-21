package org.example.lista_encadeada;

import java.util.Random;

public class Lista {
    private No inicio;
    private No fim;
    private int qte_el;

    public Lista() {
        this.inicio = null;
        this.fim = null;
        this.qte_el = 0;
    }

    public No getInicio() {
        return inicio;
    }

    public No getFim() {
        return fim;
    }

    public void inserirNoFim(int valor) {
        No novoNo = new No(valor);

        if (inicio == null) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.setProx(novoNo);
            novoNo.setAnt(fim);
            fim = novoNo;
        }
        qte_el++;
    }

    public void exibir() {
        No atual = inicio;
        System.out.print("Lista: ");
        while (atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getProx();
        }
        System.out.println();
    }

    public void exibir(String str) {
        No atual = inicio;
        System.out.print("Lista ");
        System.out.print(str);
        System.out.print(": ");
        while (atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getProx();
        }
        System.out.println();
    }

    public void exibirOrdemReversa() {
        No atual = fim;
        System.out.print("Lista (reverso): ");
        while (atual != null) {
            System.out.print(atual.getInfo() + " ");
            atual = atual.getAnt();
        }
        System.out.println();
    }

    public void popular(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            inserirNoFim(random.nextInt(101));
        }
    }

    public void aleatorizar() {
        if (inicio != null && inicio != fim) {
            Random random = new Random();
            for (int i = qte_el - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                No noI = buscarNoPorIndice(i);
                No noJ = buscarNoPorIndice(j);
                int temp = noI.getInfo();
                noI.setInfo(noJ.getInfo());
                noJ.setInfo(temp);
            }
        }
    }

    private No buscarNoPorIndice(int indice) {
        No atual = inicio;
        for (int i = 0; i < indice && atual != null; i++) {
            atual = atual.getProx();
        }
        return atual;
    }

    // GUSTAVO

    public void ordenarPorHeap() {
        int aux1, TL2 = qte_el, pai, F1, F2, maiorF;
        int[] onde_estou_na_lista = { 0 };
        No ultimo_no = fim, auxFilho, auxPai;
        No[] onde_estou_no = { inicio };
        while (ultimo_no != inicio) {
            for (pai = TL2 / 2 - 1; pai >= 0; pai--) {
                F1 = 2 * pai + 1;
                F2 = F1 + 1;
                // recuperar maior filho
                maiorF = F1;
                if (F2 < TL2 && recuperarNaPos(F2, this, onde_estou_na_lista, onde_estou_no)
                        .getInfo() > recuperarNaPos(F1, this, onde_estou_na_lista, onde_estou_no).getInfo()) {
                    maiorF = F2;
                }
                // se filho maior é maior que pai, trocar
                auxFilho = recuperarNaPos(maiorF, this, onde_estou_na_lista, onde_estou_no);
                auxPai = recuperarNaPos(pai, this, onde_estou_na_lista, onde_estou_no);
                if (auxFilho.getInfo() > auxPai.getInfo()) {
                    maiorF = auxFilho.getInfo();
                    auxFilho.setInfo(auxPai.getInfo());
                    auxPai.setInfo(maiorF);
                }
            }
            // trocar primeiro com ultimo no
            aux1 = ultimo_no.getInfo();
            ultimo_no.setInfo(inicio.getInfo());
            inicio.setInfo(aux1);
            // decrementar TL2 => mover ponteiro
            ultimo_no = ultimo_no.getAnt();
            TL2--;
        }
    }

    public void ordenarPorQuickComPivo() {
    }

    public void ordenarPorQuickSemPivo() {
    }

    public No recuperarNaPos(int pos) {
        No aux = inicio;
        int i = 0;
        while (aux != null && i != pos) {
            aux = aux.getProx();
            i++;
        }
        return aux;
    }

    public No recuperarNaPos(int pos, Lista lista) {
        No aux = lista.getInicio();
        int i = 0;
        while (aux != null && i != pos) {
            aux = aux.getProx();
            i++;
        }
        return aux;
    }

    public No recuperarNaPos(int pos, Lista lista, int[] onde_estou_pos, No[] onde_estou_no) {
        int n = onde_estou_pos[0] - pos, i = 0;
        if (n > 0) {
            // andar pra trás
            for (i = 0; i < n; i++) {
                onde_estou_no[0] = onde_estou_no[0].getAnt();
                onde_estou_pos[0]--;
            }
        } else {
            // andar pra frente
            n *= -1;
            for (i = 0; i < n; i++) {
                onde_estou_no[0] = onde_estou_no[0].getProx();
                onde_estou_pos[0]++;
            }
        }
        return onde_estou_no[0];
    }

    public void topDownMerge(Lista listaA, Lista listaB, int ini, int meio, int fim) {
        int i = ini, j = meio, k;
        No auxi, auxj, auxk;
        int[] posI = { 0 }, posJ = { 0 }, posK = { 0 };
        No[] noI = { listaA.getInicio() };
        No[] noJ = { listaA.getInicio() };
        No[] noK = { listaB.getInicio() };

        for (k = ini; k < fim; k++) {
            auxi = recuperarNaPos(i, listaA, posI, noI);
            auxj = recuperarNaPos(j, listaA, posJ, noJ);
            auxk = recuperarNaPos(k, listaB, posK, noK);

            if (i < meio && (j >= fim || auxi.getInfo() <= auxj.getInfo())) {
                auxk.setInfo(auxi.getInfo());
                i++;
            } else {
                auxk.setInfo(auxj.getInfo());
                j++;
            }
        }
    }

    public void topDownSplitMerge(Lista listaA, Lista listaB, int ini, int fim) {
        if (fim - ini > 1) {
            int meio = (ini + fim) / 2;
            System.out.println("Chegou no TDSM com inicio " + ini + " fim " + fim + " e meio " + meio);
            topDownSplitMerge(listaB, listaA, ini, meio);
            topDownSplitMerge(listaB, listaA, meio, fim);
            topDownMerge(listaB, listaA, ini, meio, fim);
        }
    }

    // top down
    public void ordenarPorFusaoDiretaMerge1() {
        No aux = inicio;
        Lista lista_aux = new Lista();
        // copia
        while (aux != null) {
            lista_aux.inserirNoFim(aux.getInfo());
            aux = aux.getProx();
        }
        lista_aux.exibir("(Lisa aux copiada)");

        topDownSplitMerge(this, lista_aux, 0, qte_el);

        lista_aux.exibir("(Lisa aux na teoria populada e ordenada)");

    }

    // bottom up
    public void bottomUpMerge(Lista listaA, Lista listaB, int left, int right, int end) {
        int i = left, j = right, k;
        No auxi, auxj, auxk;
        int[] posI = { 0 }, posJ = { 0 }, posK = { 0 };
        No[] noI = { listaA.getInicio() };
        No[] noJ = { listaA.getInicio() };
        No[] noK = { listaB.getInicio() };

        for (k = left; k < end; k++) {
            auxi = recuperarNaPos(i, listaA, posI, noI);
            auxj = recuperarNaPos(j, listaA, posJ, noJ);
            auxk = recuperarNaPos(k, listaB, posK, noK);

            if (i < right && (j >= end || auxi.getInfo() <= auxj.getInfo())) {
                auxk.setInfo(auxi.getInfo());
                i++;
            } else {
                auxk.setInfo(auxj.getInfo());
                j++;
            }
        }
    }

    // bottom up
    public void ordenarPorFusaoDiretaMerge2() {
        if (inicio != null && inicio.getProx() != null) {
            int n = qte_el;
            Lista lista_aux = new Lista();

            No atual = inicio;
            while (atual != null) {
                lista_aux.inserirNoFim(atual.getInfo());
                atual = atual.getProx();
            }

            for (int width = 1; width < n; width *= 2) {
                for (int i = 0; i < n; i = i + 2 * width) {
                    int left = i;
                    int right = Math.min(i + width, n);
                    int end = Math.min(i + 2 * width, n);
                    bottomUpMerge(this, lista_aux, left, right, end);
                }

                int[] posAux = { 0 }, posEssaLista = { 0 };
                No[] noListaAux = { lista_aux.getInicio() };
                No[] nosDessaLista = { this.getInicio() };

                for (int k = 0; k < n; k++) {
                    No nAux = recuperarNaPos(k, lista_aux, posAux, noListaAux);
                    No noDessaLista = recuperarNaPos(k, this, posEssaLista, nosDessaLista);
                    noDessaLista.setInfo(nAux.getInfo());
                }
            }
        }
    }

    public void ordenarPorSelecaoDireta() {
        if (inicio != null && inicio != fim) {
            int menor;
            No pi = inicio, pj, pMenor;
            // ir até o penúltimo elemento
            while (pi.getProx() != null) {
                menor = pi.getInfo();
                pMenor = pi;
                // ir do proximo do atual até o último
                pj = pi.getProx();
                while (pj != null) {
                    if (pj.getInfo() < menor) {
                        menor = pj.getInfo();
                        pMenor = pj;
                    }
                    pj = pj.getProx();
                }
                pMenor.setInfo(pi.getInfo());
                pi.setInfo(menor);
                pi = pi.getProx();
            }
        }
    }

    public void ordenarPorGnome() {
        int aux;
        No atual = inicio;
        while (atual != null) {
            if (atual == inicio || atual.getInfo() >= atual.getAnt().getInfo()) {
                atual = atual.getProx();
            } else {
                // troca
                aux = atual.getInfo();
                atual.setInfo(atual.getAnt().getInfo());
                atual.getAnt().setInfo(aux);
                atual = atual.getAnt();
            }
        }
    }

    public void ordenarPorCounting() {
        // achar maior elemento "k"
        int maior = inicio.getInfo();
        No atual = inicio, aux;
        while (atual != null) {
            if (atual.getInfo() > maior) {
                maior = atual.getInfo();
            }
            atual = atual.getProx();
        }
        // criar nova lista com tamanho k + 1 populada com zeros
        Lista novaLista = new Lista();
        for (int i = 0; i <= maior; i++) {
            novaLista.inserirNoFim(0);
        }
        // colocar em cada posição a quantidade de valores referente a cada posição
        atual = inicio;
        while (atual != null) {
            aux = novaLista.getInicio();
            for (int i = 0; i < atual.getInfo(); i++) {
                aux = aux.getProx();
            }
            aux.setInfo(aux.getInfo() + 1);
            atual = atual.getProx();
        }
        // repopular lista original com valores ordenados
        aux = novaLista.getInicio();
        int numAtual = 0;
        atual = inicio;
        while (aux != null) {
            if (aux.getInfo() > 0) {
                // num atual é o valor a ser colocado
                for (int i = 0; i < aux.getInfo(); i++) {
                    atual.setInfo(numAtual);
                    atual = atual.getProx();
                }
            }
            numAtual++;
            aux = aux.getProx();
        }
    }

    // FERNANDO
    public void ordenarPorInsercaoDireta() {
        No pi = inicio.getProx(), ppos;
        int aux;
        while (pi != null) {
            aux = pi.getInfo();
            ppos = pi;
            while (ppos != inicio && aux < ppos.getAnt().getInfo()) {
                ppos.setInfo(ppos.getAnt().getInfo());
                ppos = ppos.getAnt();
            }
            ppos.setInfo(aux);
            pi = pi.getProx();
        }
    }

    public int busca_binaria(int chave, int qtd) {
        int i, ini = 0, fim = qtd - 1, meio = fim / 2;
        No list = inicio;
        while (ini <= fim) {
            for (i = 0; i < meio; i++)
                list = list.getProx();
            if (chave < list.getInfo())
                fim = meio - 1;
            else
                ini = meio + 1;
            meio = (ini + fim) / 2;
            list = inicio;
        }
        if (chave > list.getInfo())
            return meio + 1;
        return meio;
    }

    public void ordenarPorInsercaoBinaria() {
        int aux, pos, ordenado = 1;
        No list = inicio.getProx();
        while (list != null) {
            aux = list.getInfo();
            pos = busca_binaria(aux, ordenado);
            if (pos < ordenado) {
                No l2 = list;
                for (int i = ordenado; i > pos; i--) {
                    l2.setInfo(l2.getAnt().getInfo());
                    l2 = l2.getAnt();
                }
                l2.setInfo(aux);
            }
            list = list.getProx();
            ordenado++;
        }
    }

    public void ordenarPorBolha() {
        int aux;
        No pi, pfim = fim;
        boolean flag = true;

        while (pfim != inicio && flag) {
            pi = inicio;
            flag = false;
            while (pi != pfim) {
                if (pi.getInfo() > pi.getProx().getInfo()) {
                    aux = pi.getInfo();
                    pi.setInfo(pi.getProx().getInfo());
                    pi.getProx().setInfo(aux);
                    flag = true;
                }
                pi = pi.getProx();
            }
            pfim = pfim.getAnt();
        }
    }

    public void ordenarPorShake() {
         int aux;
         boolean flag =true;
         No po,pinicio=inicio,pfim=fim;
         while (pinicio!=pfim && flag)
         {
             flag = false;
             po=pinicio;
             while(po!=pfim)
             {
                 if (po.getInfo()>po.getProx().getInfo())
                 {
                     aux=po.getInfo();
                     po.setInfo(po.getProx().getInfo());
                     po.getProx().setInfo(aux);
                     flag=true;
                 }
                 po=po.getProx();
             }
             pfim=pfim.getAnt();
             if (flag)
             {
                 flag=false;
                 po=pfim;
                 while(po!=pinicio)
                 {
                     if(po.getInfo()<po.getAnt().getInfo())
                     {
                         aux=po.getInfo();
                         po.setInfo(po.getAnt().getInfo());
                         po.getAnt().setInfo(aux);
                         flag=true;
                     }
                     po=po.getAnt();
                 }
                 pinicio=pinicio.getProx();
             }
         }
    }

    public void ordenarPorShell() {
        int dist=1, aux, pos,i,aux2,aux3;
        No pi;
        while (dist<qte_el)
            dist=3*dist+1;
        dist = dist/3;
        while (dist>0)
        {
            i=dist;
            while(i<qte_el)
            {
                pi=inicio;
                for(int j=0;j<i;j++)
                    pi=pi.getProx();
                aux=pi.getInfo();
                pos=i;
                aux2 = pos - dist;
                pi=inicio;
                for(int j=0;j<aux2;j++)
                    pi=pi.getProx();

                while(pos>=dist && aux<pi.getInfo())
                {
                    aux3=pi.getInfo();
                    pi=inicio;
                    for(int j=0;j<pos;j++)
                        pi=pi.getProx();
                    pi.setInfo(aux3);
                    pos=pos-dist;
                    aux2 = pos - dist;
                    pi=inicio;
                    for(int j=0;j<aux2;j++)
                        pi=pi.getProx();

                }
                pi=inicio;
                for(int j=0;j<pos;j++)
                    pi=pi.getProx();
                pi.setInfo(aux);
                i++;
            }
            dist=dist/3;
        }

    }

    public void ordenarPorBucket() {

    }

    public void ordenarPorRadix() {
    }

    public void ordenarPorComb() {
    }

    public void ordenarPorTim() {
    }
}