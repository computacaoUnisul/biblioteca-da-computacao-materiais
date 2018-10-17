package ReconhecerLetras;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFiles {

    String file = null;
    int[] letters = new int[25];
    boolean debug = false;

    public ReadFiles(String file){

        Main main = new Main();
        this.file = file;

        try{
            FileReader fr = new FileReader(file);

            BufferedReader read = new BufferedReader(fr);
            String line = read.readLine();

            int pointer = 0;
            String aux = null;

            while(line != null){
                for(int i = 0; i < 5; i++){
                    // quantidade de caracteres do .txt
                    aux = String.valueOf(line.charAt(i));

                    // checa peso e guarda na posição do vetor
                    letters[pointer] = checaPeso(aux);

//                    System.out.println(letters[pointer]);
                    pointer++;
                }

                // passa para próxima linha do .txt
                line = read.readLine();
            } fr.close();

        } catch (FileNotFoundException e) {
            main.gravaLog(main.getLog() + "Arquivo não encontrado \n" + e.getMessage() + "\n");
            debug=true;
        } catch (IOException e) {
            main.gravaLog(main.getLog() + "Erro de leitura: %s.\n" + e.getMessage() + "\n");
            debug=true;
        }

        if(!debug) {
            main.gravaLog(main.getLog() + " ABRIR TXT: " + file + " \n OK" + "\n");
        }
    }


    public Integer checaPeso(String valor){
        if(valor.equalsIgnoreCase(".")){
            return -1;
        } else if(valor.equalsIgnoreCase("#")){
            return 1;
        } else {
            return 0;
        }
    }

    //    GETTERS and SETTERS
    public int[] getLetters() {
        return letters;
    }

    public void setLetters(int[] letters) {
        this.letters = letters;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
