import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ilk_Giris extends JFrame{

    private JPanel panel1;
    private JButton YONETICIGIRISIButton;
    private JButton MUSTERIGIRISIButton;

    public Ilk_Giris(){
        add(panel1);
        setSize(1024,768);

        // Form kapandığında program kapanması
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //YoneticiGirisi butonunun aksiyonu
        YONETICIGIRISIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Yonetici_Girisi adli form acilacak, Mevcut olan form gizlenecektir.
                Yonetici_Girisi yonetici = new Yonetici_Girisi();
                setVisible(false);
                yonetici.setVisible(true);  //Diger form acilacaktir.
            }
        });

        MUSTERIGIRISIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Kullanici_Girisi adli form acilacak, Mevcut olan form gizlenecektir.
                Kullanici_Girisi kullanici = new Kullanici_Girisi();
                setVisible(false);
                kullanici.setVisible(true);
            }
        });
    }








}
