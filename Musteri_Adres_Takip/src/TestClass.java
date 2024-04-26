import javax.swing.*;

public class TestClass {
    public static void main(String[] args){

        //Thread oluşturmak
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Ilk_Giris ilk = new Ilk_Giris();
                ilk.setVisible(true);
            }
        });

    }
}
