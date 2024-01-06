package Program;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        Login lg = new Login();
        lg.setVisible(true);
        lg.pack();
        lg.setLocationRelativeTo(null);
        lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
