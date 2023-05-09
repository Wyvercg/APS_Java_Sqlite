
package aps;

public class Main {
    
    public String Usuario = null;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
        
        
    }
    
}