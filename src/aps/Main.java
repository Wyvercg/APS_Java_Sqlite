
package aps;

public class Main {
    
    //uma vez passado pela tela de login, e logar de forme bem sucedida, o usuario e o id do usuario sao armazenados aqui nestos dois objetos abaixo.
    public static String Usuario;
    public static String IdUsuario;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
        
        
    }
    
}