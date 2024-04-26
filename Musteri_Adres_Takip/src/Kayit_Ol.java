import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Kayit_Ol extends JFrame {
    private JPanel panel3;
    private JPanel panel2;
    private JTextField K_AD;
    private JPasswordField passwordField1;
    private JButton geriDonButton;
    private JButton Kayit_Ol_Button;
    private JPasswordField passwordField2;
    private JTextField Eposta_TextField;
    private JFormattedTextField NumaraTextField;
    private JTextField Isim_Textfield;
    private JTextField Soyisim_Textfield;


    public Kayit_Ol(){
        add(panel3);
        setSize(1024,768);

        //Kayit_Ol formu kapandiginda programın durması amaçlanmıştır.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        geriDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Kullanici_Girisi ilk = new Kullanici_Girisi();
                ilk.setVisible(true);
            }
        });
        //Numara kısmına sadece sayı girilmesi saglanmistir.
        NumaraTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if(!Character.isDigit(c)){
                    e.consume();
                }
                //Numara 10 basamaklı olacagı icin 10dan fazlası silinecektir
                if(NumaraTextField.getText().length() > 9){
                    e.consume();
                }




            }
        });

        NumaraTextField.setBounds(5,5,20,20);

        //Girilen yazılar bitmeden diger yazılara gidilmesi engellenmiştir.
        K_AD.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                passwordField1.setEnabled(true);
            }
        });
        passwordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                passwordField2.setEnabled(true);
            }
        });
        passwordField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                Eposta_TextField.setEnabled(true);
            }
        });
        Eposta_TextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                NumaraTextField.setEnabled(true);
            }
        });
        NumaraTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                Isim_Textfield.setEnabled(true);
            }
        });
        Isim_Textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                Soyisim_Textfield.setEnabled(true);
            }
        });
        Soyisim_Textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                Kayit_Ol_Button.setEnabled(true);
            }
        });
        Kayit_Ol_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InsertKullaniciKayit();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    //Kayıt olup veriye aktarmak.
    public void InsertKullaniciKayit() throws SQLException{
        Connection connection = null;
        DbHelper db = new DbHelper();
        PreparedStatement statement = null;
        PreparedStatement selectStatement = null;
        if(passwordField1.getText().equals(passwordField2.getText())) {
            try {
                connection = db.getConnection();


                //Kullanici adinin var olup olmadigi kontrol edilir.
                String selectSql = "SELECT * FROM k_giris.kullanici_girisi WHERE K_KullanAD = ?";
                selectStatement = connection.prepareStatement(selectSql);
                selectStatement.setString(1, K_AD.getText());
                if (selectStatement.executeQuery().next()) {
                    JOptionPane.showMessageDialog(null, "Bu kullanıcı adı zaten var. Lütfen farklı bir kullanıcı adı seçin.");
                    return;
                }

                String sql = "insert into k_giris.kullanici_girisi (K_KullanAD,K_SIFRE,K_EPOSTA,K_NUMARA,K_AD,K_SOYAD)" + "values (?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, K_AD.getText().toString());
                statement.setString(2, passwordField1.getText().toString());
                statement.setString(3, Eposta_TextField.getText().toString());
                statement.setString(4, NumaraTextField.getText().toString());
                statement.setString(5, Isim_Textfield.getText().toString());
                statement.setString(6, Soyisim_Textfield.getText().toString());
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Kayıt basariyla eklenmistir.");
            } catch (SQLException exception) {
                db.ShowError(exception);
            } finally {

                //Verilen degiskenler "null" olmadığı sürece kapanması sağlanmıştır.
                if (selectStatement != null) {
                    selectStatement.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Sifreleri dogru giriniz");
        }
        }







}
