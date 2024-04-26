import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;

//AdresFonksiyonlar class'ından miras alır bu sayede adresle alakalı fonksiyonları hazır şekilde kullanırız
public class Kullanici_Giris_Yap extends AdresFonksiyonlar {
    private JButton geriDon1Button;
    private JPanel panel1;
    private JComboBox il_Combo_Box;
    private JComboBox ilce_Combo_box;
    private JComboBox Mahalle_Combo_Box;
    private JComboBox Sokak_Combo_Box;
    private JTextField Numara_TextField;
    private JTextField IcKapi_TextField;
    private JList urunler_list;
    private JButton urunEkleButton;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JButton TabloYenileButton;
    private JButton silButton;

    public Kullanici_Giris_Yap(){}
    public Kullanici_Giris_Yap(Kullanici_Eksik kullaniciEksik) {
        add(panel1);
        setSize(1024, 768);
        //Kullanici_Giris_Yap formu kapandiginda programın durması amaçlanmıştır.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Il_Belirle(il_Combo_Box);

        geriDon1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Kullanici_Girisi giris = new Kullanici_Girisi();
                giris.setVisible(true);
            }
        });
        il_Combo_Box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //il sekmesi secildigi zaman ilceler sekmesi acilacaktir.

                String SecilenIl = il_Combo_Box.getSelectedItem().toString();
                Ilce_Belirle(SecilenIl,ilce_Combo_box,Mahalle_Combo_Box);
                ilce_Combo_box.setEnabled(true);
            }
        });
        ilce_Combo_box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                //SecilenIl ve SecilenIlce degerleri null olmadıgı sürece cekilmesi istenmistir.
                if (il_Combo_Box.getSelectedItem() != null && ilce_Combo_box.getSelectedItem() != null) {
                    String SecilenIl = il_Combo_Box.getSelectedItem().toString();
                    String SecilenIlce = ilce_Combo_box.getSelectedItem().toString();
                    MahalleBelirle(SecilenIl, SecilenIlce,Mahalle_Combo_Box,Sokak_Combo_Box);
                    //ilceler sekmesi secildigi zaman mahalle sekmesi acilacaktir
                    Mahalle_Combo_Box.setEnabled(true);
                }
            }
        });
        Mahalle_Combo_Box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //Mahalle sekmesi secildigi zaman sokak/cadde sekmesi acilacaktir.
                if (il_Combo_Box.getSelectedItem() != null && ilce_Combo_box.getSelectedItem() != null && Mahalle_Combo_Box.getSelectedItem() != null) {
                    String SecilenIl = il_Combo_Box.getSelectedItem().toString();
                    String SecilenIlce = ilce_Combo_box.getSelectedItem().toString();
                    String SecilenMahalle = Mahalle_Combo_Box.getSelectedItem().toString();
                    SokakBelirle(SecilenIl, SecilenIlce, SecilenMahalle,Sokak_Combo_Box,Numara_TextField);
                    Sokak_Combo_Box.setEnabled(true);
                }
            }
        });
        Sokak_Combo_Box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //sokak/cadde sekmesi secildigizaman 'No' ve 'ic kapi' textfield aktif hale gelecektir
                Numara_TextField.setEnabled(true);
            }
        });
        Numara_TextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                IcKapi_TextField.setEnabled(true);
                //Sadece 4 elemanlı girilmesi istenmektedir.
                if (Numara_TextField.getText().length() > 3) {
                    e.consume();
                }
            }
        });
        IcKapi_TextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                //Girilen degerin max 3 basamaklı ve sadece rakam girilmesi istenmektedir.
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
                //3 basamaklı olması icin islem yapılmaktadır.
                if (IcKapi_TextField.getText().length() > 2) {
                    e.consume();
                }
                //Urunlerin oldugu List aktif olacaktir
                urunler_list.setEnabled(true);
            }
        });


        urunler_list.addFocusListener(new FocusAdapter() {
        });
        urunler_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                urunEkleButton.setEnabled(true);
            }
        });
        urunEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kullanici_Eksik kullaniciEksik = getKullaniciEksik();
                if (kullaniciEksik != null) {
                    Kullanici kullanici = new Kullanici(
                            kullaniciEksik.getK_Ad(),
                            kullaniciEksik.getK_Sifre(),
                            kullaniciEksik.getK_Eposta(),
                            kullaniciEksik.getK_Numara(),
                            kullaniciEksik.getK_AdGercek(),
                            kullaniciEksik.getK_Soyad(),
                            il_Combo_Box.getSelectedItem().toString(),
                            ilce_Combo_box.getSelectedItem().toString(),
                            Mahalle_Combo_Box.getSelectedItem().toString(),
                            Sokak_Combo_Box.getSelectedItem().toString(),
                            Numara_TextField.getText().toString(),
                            IcKapi_TextField.getText().toString(),
                            urunler_list.getSelectedValue().toString());
                    kullanici.Kullanici_To_Database(kullanici);
                    //Ekledikten sonra tablo yenilenmek istenmiştir
                } else {
                    // handle the case where kullaniciEksik is null
                    System.err.println("kullaniciEksik is null");
                }

                update();


            }
        });
        this.kullaniciEksik = kullaniciEksik;

        TabloYenileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete(table1);
                update();
            }
        });
        //Program açılır açılmaz tablo yenilenmesi sağlanmıştır.
        update();
    }

    //Nesne oluşturma işlemleri.
    private Kullanici_Eksik kullaniciEksik;

    public Kullanici_Eksik getKullaniciEksik() {
        return this.kullaniciEksik;
    }
    public void update() {
        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();
        try {
            connection = db.getConnection();
            String sql = "select k_giris.kullanici_bilgiler.K_AD,k_giris.kullanici_bilgiler.K_SOYAD,k_giris.kullanici_bilgiler.K_IL,k_giris.kullanici_bilgiler.K_ILCE," +
                    "k_giris.kullanici_bilgiler.K_MAHALLE,k_giris.kullanici_bilgiler.K_SOKAK,k_giris.kullanici_bilgiler.K_No,k_giris.kullanici_bilgiler.K_ICKAPI," +
                    "k_giris.kullanici_bilgiler.K_URUN from k_giris.kullanici_bilgiler where K_KullanAD =? and K_SIFRE =?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, kullaniciEksik.getK_Ad());
            statement.setString(2, kullaniciEksik.getK_Sifre());
            ResultSet resultSet = statement.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}





