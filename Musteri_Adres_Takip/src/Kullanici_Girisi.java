import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class Kullanici_Girisi extends JFrame{
    private JPanel panel1;
    private JPanel panel2;
    private JTextField K_AD;
    private JPasswordField K_Sifre;
    private JButton girisYapButton;
    private JButton geriDonButton;
    private JButton Kayit_Ol_Button;

    public Kullanici_Girisi(){
        add(panel1);
        setSize(1024,768);
        //Kullanici_Girisi formu kapandiginda programın durması amaçlanmıştır.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        geriDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Ilk_Giris ilk = new Ilk_Giris();
                ilk.setVisible(true);
            }
        });
        //Butona basıldığında kayıt olma formu açılacaktır.
        Kayit_Ol_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Kayit_Ol kayit = new Kayit_Ol();
                kayit.setVisible(true);
            }
        });

        girisYapButton.addActionListener(new ActionListener() {
            //Butona basıldığında eger kullanici adi ve sifre dogruysa giris yapma formu açılacaktır.
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultGiris();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        K_Sifre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                try {
                    ResultGiris();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                }
            }
        });
    }

    public void ResultGiris() throws SQLException{
        Connection connection = null;
        DbHelper db = new DbHelper();
        PreparedStatement statement = null;
        PreparedStatement Nesne;
        ResultSet resultSet = null;
        String KullaniciAd = K_AD.getText();
        String Sifre = K_Sifre.getText();


        try{
            connection = db.getConnection();
            String sql = "select * from k_giris.kullanici_girisi where K_KullanAD=? AND K_SIFRE=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,KullaniciAd);
            statement.setString(2,Sifre);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                setVisible(false);

                //Nesne oluşturmak icin degiskenleri cekmek
                String EPosta = rs.getString("K_EPOSTA");
                String Numara = rs.getString("K_NUMARA");
                String Ad = rs.getString("K_AD");
                String Soyad = rs.getString("K_SOYAD");

                //Nesne oluşturuldu
                Kullanici_Eksik kullaniciEksik = new Kullanici_Eksik(KullaniciAd, Sifre, EPosta, Numara, Ad, Soyad);
                Kullanici_Giris_Yap giris = new Kullanici_Giris_Yap(kullaniciEksik);
                giris.setVisible(true);


            }
            else{
                JOptionPane.showMessageDialog(null,"Kullanici adi veya Sifre yanlis girdiniz.");
            }
        } catch (SQLException e) {
            if(e.getErrorCode()==1064){
                db.ShowError(e);
                JOptionPane.showMessageDialog(null,"Boyle bir kullanici ad bulunmamaktadir.");
            }
            else{
                db.ShowError(e);
            }
        }
        finally {
            //Verilen degiskenler "null" olmadığı sürece kapanması sağlanmıştır.

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }






    }



}
