import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Yonetici_Girisi extends JFrame {
    private JPanel panel2;
    private JPasswordField Y_SIFRE;
    private JTextField Y_AD;
    private JButton girisYapButton;
    private JButton geriDonButton;


    public Yonetici_Girisi() {
        add(panel2);
        setSize(1024, 768);
        //Yonetici_Girisi formu kapandiginda programın durması amaçlanmıştır.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        geriDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Ilk_Giris ilk = new Ilk_Giris();
                ilk.setVisible(true);

            }
        });
        girisYapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultGiris();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //Enter tuşuna basınca açılması istenmiştir
        Y_SIFRE.addKeyListener(new KeyAdapter() {
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

    public void ResultGiris() throws SQLException {
        Connection connection = null;
        DbHelper db = new DbHelper();
        PreparedStatement statement = null;
        PreparedStatement Nesne;
        ResultSet resultSet = null;
        String KullaniciAd = Y_AD.getText();
        String Sifre = Y_SIFRE.getText();


        try {
            connection = db.getConnection();
            String sql = "select * from k_giris.yonetici_bilgiler where Y_KullanAD=? AND Y_SIFRE=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, KullaniciAd);
            statement.setString(2, Sifre);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                setVisible(false);
                Yonetici_Islemler yonetici = new Yonetici_Islemler();
                yonetici.setVisible(true);


            } else {
                JOptionPane.showMessageDialog(null, "Kullanici adi veya Sifre yanlis girdiniz.");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1064) {
                db.ShowError(e);
                JOptionPane.showMessageDialog(null, "Boyle bir kullanici ad bulunmamaktadir.");
            } else {
                db.ShowError(e);
            }
        } finally {
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


