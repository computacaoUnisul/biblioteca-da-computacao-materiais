package ReconhecerLetras;

public class Algoritmo {

    String caminho = null;
    int resultado;
    int[] pesos = new int[25];


    public Algoritmo(){
        for(int i : pesos){
            pesos[i] = 0;
        }
    }

    public String treinarAlgoritmo(String caminho, int resultado) {

        String returnLog = null;
        Main main = new Main();

        this.caminho = caminho;
        this.resultado = resultado;

        ReadFiles ler = new ReadFiles(caminho);
        int[] letra = ler.getLetters();

        for (int i = 0; i < letra.length; i++) {
            pesos[i] = pesos[i] + (letra[i]*resultado);

//            System.out.println("contador=" + i);
//            System.out.println("Peso" + pesos[i]);
        }

        if(!ler.isDebug()){
            if(resultado == 1) {
                returnLog = "TREINAMENTO DE X REALIZADO \n";
            } else if(resultado == -1){
                returnLog = "TREINAMENTO DE O REALIZADO \n";
            } else {
                returnLog = "FALHA NO RESULTADO!";
            }
        } else {
            returnLog = "Falha! isDebug() é falso!";
        }

        return returnLog;
    }

    public String testaAlgoritmo(String caminho){

        String returnLog = null;
        Main main = new Main();

        ReadFiles le = new ReadFiles(caminho);
        int[] letra = le.getLetters();

        int s = 0;
        for (int i = 0; i < letra.length; i++) {
            s = s + (pesos[i]*letra[i]);
        }
        System.out.println("VALOR DE S = " + s);

        int y = 0;

        if(s > 0){
            y = 1;
            returnLog = "Resultado = " + y + "\n" + " O arquivo corresponde a letra X";
        }else if (s < 0){
            y = -1;
            returnLog = "Resultado = " + y + "\n" + " O arquivo corresponde a letra O";
        }else{
            returnLog = "Resultado = " + y + "\n" + " ARQUIVO INVÁLIDO!";
        }

        return returnLog;
    }

}
