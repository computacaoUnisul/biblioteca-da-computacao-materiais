package ReconhecerLetras;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JDialog {

    private JPanel contentPane;
    private JButton buttonAbrirX;
    private JButton buttonAbrirO;
    private JButton buttonTestar;
    private JTextArea txtLog;
    private JButton abrirX;
    Algoritmo algoritmo = new Algoritmo();

    public Main() {
        setContentPane(contentPane);
        setModal(true);

        // actionListeners
        buttonAbrirX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser abrir = new JFileChooser();
                String caminhoArquivo = null;
                int retorno = abrir.showOpenDialog(null);
                if (retorno == JFileChooser.APPROVE_OPTION)
                    caminhoArquivo = abrir.getSelectedFile().getAbsolutePath();

                if(caminhoArquivo != null){
                    gravaLog(getLog() + " " + algoritmo.treinarAlgoritmo(caminhoArquivo, 1) + "\n");
                    buttonAbrirX.setEnabled(false);
                }

            }
        });
        buttonAbrirO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser abrir = new JFileChooser();
                String caminhoArquivo = null;
                int retorno = abrir.showOpenDialog(null);
                if (retorno == JFileChooser.APPROVE_OPTION)
                    caminhoArquivo = abrir.getSelectedFile().getAbsolutePath();

                if(caminhoArquivo != null){
                    gravaLog(getLog() + " " + algoritmo.treinarAlgoritmo(caminhoArquivo, -1) + "\n");
                    buttonAbrirO.setEnabled(false);
                }

            }
        });

        buttonTestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser abrir = new JFileChooser();
                String caminhoArquivo = null;
                int retorno = abrir.showOpenDialog(null);
                if (retorno == JFileChooser.APPROVE_OPTION)
                    caminhoArquivo = abrir.getSelectedFile().getAbsolutePath();

                if(caminhoArquivo != null){
                    limpaLog();
                    gravaLog(getLog() + " " + algoritmo.testaAlgoritmo(caminhoArquivo) + "\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    // operações com textArea de log
    public void gravaLog(String txtLog) {
        this.txtLog.setText(txtLog);
    }

    public void limpaLog(){
        this.txtLog.setText("");
    }

    public String getLog(){
        return this.txtLog.getText();
    }

}