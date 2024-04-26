import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Yonetici_Islemler extends Kullanici_Giris_Yap{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JButton yenileButton;
    private JComboBox il_Combo_Box;
    private JComboBox ilce_Combo_box;
    private JComboBox Mahalle_Combo_Box;
    private JComboBox Sokak_Combo_Box;
    private JTextField Numara_TextField;
    private JTextField IcKapi_TextField;
    private JList urunler_list;
    private JButton urunEkleButton;
    private JButton geriDonButton;
    private JTextField KullaniciAdTextField;
    private JButton ekleButton;
    private JButton silButton;
    private JTextField isimTextfield;
    private JTextField SoyisimTextField;
    private JTextField EpostaTextField;
    private JTextField TelefonTextField;
    private JTextField SifreTextField;
    private JButton temizleButton;
    private JButton guncelleButton;

    public Yonetici_Islemler() {
    add(panel1);
    setSize(1024,768);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Il_Belirle(il_Combo_Box);
    update();
        geriDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Yonetici_Girisi giris = new Yonetici_Girisi();
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
                //sokak/cadde sekmesi secildigizaman 'No' textfield aktif hale gelecektir
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

        yenileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                ekleButton.setEnabled(false);

            }
        });
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Sildikten sonra yenilenmesi amaçlanmıştır
                delete(table1);
                update();
            }
        });
        //Girilen Degerin sadece numara ve 11 basamaklı olması amaçlanmıştır.
        TelefonTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
                //3 basamaklı olması icin islem yapılmaktadır.
                if (TelefonTextField.getText().length() > 9) {
                    e.consume();
                }
            }
        });
        urunEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Objects.equals(KullaniciAdTextField.getText(), "") || !isimTextfield.getText().toString().equals("")
                || !EpostaTextField.getText().toString().equals("") || !SifreTextField.getText().toString().equals("")
                || !SoyisimTextField.getText().toString().equals("") || !TelefonTextField.getText().toString().equals("")){
                    add();
                    update();
                    guncelleButton.setEnabled(false);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Degerleri eksiksiz giriniz");
                }
            }
        });
        //JTABLE icin listener yapmak.
        ListSelectionModel model = table1.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ekleButton.setEnabled(true);
            }
        });

        ekleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int secilenSatir = table1.getSelectedRow();
                if(secilenSatir != -1) {
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    KullaniciAdTextField.setText(model.getValueAt(secilenSatir, 0).toString());
                    SifreTextField.setText(model.getValueAt(secilenSatir, 1).toString());
                    isimTextfield.setText(model.getValueAt(secilenSatir, 2).toString());
                    SoyisimTextField.setText(model.getValueAt(secilenSatir, 3).toString());
                    String Eposta;
                    String Numara;
                    DbHelper db = new DbHelper();
                    PreparedStatement statement;
                    try {
                        Connection connection = db.getConnection();
                        String sql = "select K_EPOSTA,K_NUMARA from k_giris.kullanici_bilgiler where K_KullanAD =? and K_SIFRE =?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, model.getValueAt(secilenSatir, 0).toString());
                        statement.setString(2, model.getValueAt(secilenSatir, 1).toString());
                        ResultSet rs = statement.executeQuery();
                        if (rs.next()) {
                            Eposta = rs.getString("K_EPOSTA");
                            Numara = rs.getString("K_NUMARA");
                            EpostaTextField.setText(Eposta);
                            TelefonTextField.setText(Numara);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    KullaniciAdTextField.setEnabled(false);
                    SifreTextField.setEnabled(false);
                    isimTextfield.setEnabled(false);
                    SoyisimTextField.setEnabled(false);
                    EpostaTextField.setEnabled(false);
                    TelefonTextField.setEnabled(false);
                    tabbedPane1.setSelectedIndex(1);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Lutfen satir seciniz");
                }
                guncelleButton.setEnabled(true);
            }
        });

        //Bu tuşa basınca form temizlenir.
        temizleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                il_Combo_Box.setSelectedIndex(0);
                ilce_Combo_box.setEnabled(false);
                Numara_TextField.setText("");
                IcKapi_TextField.setText("");
                IcKapi_TextField.setEnabled(false);
                urunler_list.setEnabled(false);
                urunEkleButton.setEnabled(false);
                KullaniciAdTextField.setText("");
                KullaniciAdTextField.setEnabled(true);
                SifreTextField.setText("");
                SifreTextField.setEnabled(true);
                isimTextfield.setText("");
                isimTextfield.setEnabled(true);
                SoyisimTextField.setText("");
                SoyisimTextField.setEnabled(true);
                EpostaTextField.setText("");
                EpostaTextField.setEnabled(true);
                TelefonTextField.setText("");
                TelefonTextField.setEnabled(true);

                guncelleButton.setEnabled(false);
            }
        });
        guncelleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String k_ad = KullaniciAdTextField.getText().toString();
                String sifre = SifreTextField.getText().toString();
                String isim = isimTextfield.getText().toString();
                String soyisim = SoyisimTextField.getText().toString();
                String telefon = TelefonTextField.getText().toString();
                String eposta = EpostaTextField.getText().toString();
                String il = il_Combo_Box.getSelectedItem().toString();
                String ilce = ilce_Combo_box.getSelectedItem().toString();
                String mahalle = Mahalle_Combo_Box.getSelectedItem().toString();
                String sokak = Sokak_Combo_Box.getSelectedItem().toString();
                String no = Numara_TextField.getText().toString();
                String ickapi = IcKapi_TextField.getText().toString();
                Connection connection = null;
                DbHelper db = new DbHelper();
                PreparedStatement statement = null;

                int SecilenSatir = table1.getSelectedRow();
                //Eger secilen satır yoksa -1 degeri dondurecektir.
                if (SecilenSatir != -1) {

                    String secilen_il = table1.getValueAt(SecilenSatir, 4).toString();
                    String secilen_ilce = table1.getValueAt(SecilenSatir, 5).toString();
                    String secilen_mahalle = table1.getValueAt(SecilenSatir, 6).toString();
                    String secilen_sokak = table1.getValueAt(SecilenSatir, 7).toString();
                    String secilen_no = table1.getValueAt(SecilenSatir, 8).toString();
                    String secilen_ickapi = table1.getValueAt(SecilenSatir, 9).toString();
                    String secilen_urun = table1.getValueAt(SecilenSatir, 10).toString();

                if (k_ad == "" || sifre == "" || isim == "" || soyisim == "" || telefon == "" || eposta == ""
                        || il == "İl Giriniz" || ilce == "İlce Giriniz" || mahalle == "Mahalle Giriniz" || sokak == "Sokak Giriniz"
                        || no == "" || ickapi == "" || urunler_list.getSelectedValue().toString() == null) {
                    JOptionPane.showMessageDialog(null, "Elemanlari tam giriniz.");
                } else {
                    String urun = urunler_list.getSelectedValue().toString();
                    try {
                        connection = db.getConnection();
                        String sql = "update k_giris.kullanici_bilgiler set K_IL =?,K_ILCE=?,K_MAHALLE=?,K_SOKAK=?," +
                                "K_No=?,K_ICKAPI=?,K_URUN=? where K_KullanAD=? and K_SIFRE=? and K_IL =? and K_ILCE=? and K_MAHALLE=? and K_SOKAK=?" +
                                "and K_No=? and K_ICKAPI=? and K_URUN=?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, il);
                        statement.setString(2, ilce);
                        statement.setString(3, mahalle);
                        statement.setString(4, sokak);
                        statement.setString(5, no);
                        statement.setString(6, ickapi);
                        statement.setString(7, urun);
                        statement.setString(8, k_ad);
                        statement.setString(9, sifre);
                        //Bundan sonrakileri listeden alacagiz
                        statement.setString(10, secilen_il);
                        statement.setString(11, secilen_ilce);
                        statement.setString(12, secilen_mahalle);
                        statement.setString(13, secilen_sokak);
                        statement.setString(14, secilen_no);
                        statement.setString(15, secilen_ickapi);
                        statement.setString(16, secilen_urun);

                        statement.executeUpdate();
                        update();
                        JOptionPane.showMessageDialog(null, "Urun Basariyla Guncellenmistir.");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }



            }
        });
    }
    public void add(){
        String k_ad = KullaniciAdTextField.getText().toString();
        String sifre = SifreTextField.getText().toString();
        String isim = isimTextfield.getText().toString();
        String soyisim = SoyisimTextField.getText().toString();
        String telefon = TelefonTextField.getText().toString();
        String eposta = EpostaTextField.getText().toString();
        String il = il_Combo_Box.getSelectedItem().toString();
        String ilce = ilce_Combo_box.getSelectedItem().toString();
        String mahalle = Mahalle_Combo_Box.getSelectedItem().toString();
        String sokak = Sokak_Combo_Box.getSelectedItem().toString();
        String no = Numara_TextField.getText().toString();
        String ickapi = IcKapi_TextField.getText().toString();
        String urun = urunler_list.getSelectedValue().toString();

        Connection connection = null;
        Connection connection2 = null;
        Connection connection3 = null;
        DbHelper db = new DbHelper();
        PreparedStatement statement = null;
        PreparedStatement statementGiris = null;
        PreparedStatement statementKontrol = null;

        //Kullanici adi sistemde varsa eklenmemesi saglanacaktir
        try{
            connection2 = db.getConnection();
            String sql2 = "select * from k_giris.kullanici_girisi where K_KullanAD=?";
            statementKontrol = connection2.prepareStatement(sql2);
            statementKontrol.setString(1,k_ad);
            ResultSet resultSet = statementKontrol.executeQuery();
            //Kontrol amaçlı değişkenler tanımlanmıştır.
            if(resultSet.next()) {


                String KontrolK_AD = resultSet.getString("K_KullanAD");
                String KontrolSifre = resultSet.getString("K_SIFRE");
                String KontrolEposta = resultSet.getString("K_EPOSTA");
                String KontrolNumara = resultSet.getString("K_NUMARA");
                String KontrolIsim = resultSet.getString("K_AD");
                String KontrolSoyisim = resultSet.getString("K_SOYAD");


                if (k_ad.equals(KontrolK_AD) & sifre.equals(KontrolSifre)
                        & isim.equals(KontrolIsim) & soyisim.equals(KontrolSoyisim) & eposta.equals(KontrolEposta)
                        & telefon.equals(KontrolNumara)) {
                    try {
                        connection = db.getConnection();
                        String sql = "insert into k_giris.kullanici_bilgiler (K_KullanAD,K_SIFRE,K_EPOSTA,K_NUMARA,K_AD,K_SOYAD,K_IL,K_ILCE,K_MAHALLE,K_SOKAK,K_NO,K_ICKAPI,K_URUN) " +
                                "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, k_ad);
                        statement.setString(2, sifre);
                        statement.setString(3, eposta);
                        statement.setString(4, telefon);
                        statement.setString(5, isim);
                        statement.setString(6, soyisim);
                        statement.setString(7, il);
                        statement.setString(8, ilce);
                        statement.setString(9, mahalle);
                        statement.setString(10, sokak);
                        statement.setString(11, no);
                        statement.setString(12, ickapi);
                        statement.setString(13, urun);
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Urun Basariyla Eklenmistir.");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else if (k_ad.equals(KontrolK_AD)) {
                    String errorMessage = "Sistemdeki Kullanici adiyla girdiginiz belgeler eşleşmiyor\nEslesmeyenler: ";

                    boolean mismatchFound = false;
                    if (!sifre.equals(KontrolSifre)) {
                        errorMessage += "Şifre, ";
                        mismatchFound = true;
                    }
                    if (!isim.equals(KontrolIsim)) {
                        errorMessage += "İsim, ";
                        mismatchFound = true;
                    }
                    if (!soyisim.equals(KontrolSoyisim)) {
                        errorMessage += "Soyisim, ";
                        mismatchFound = true;
                    }
                    if (!eposta.equals(KontrolEposta)) {
                        errorMessage += "E-posta, ";
                        mismatchFound = true;
                    }
                    if (!telefon.equals(KontrolNumara)) {
                        errorMessage += "Numara, ";
                        mismatchFound = true;
                    }
                    if (mismatchFound) {
                        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
                        errorMessage += " bilgileri eşleşmiyor.\nTekrar deneyiniz";
                        JOptionPane.showMessageDialog(null, errorMessage);
                    }
                }
            }
            else{
                connection = db.getConnection();
                String sql = "insert into k_giris.kullanici_bilgiler (K_KullanAD,K_SIFRE,K_EPOSTA,K_NUMARA,K_AD,K_SOYAD,K_IL,K_ILCE,K_MAHALLE,K_SOKAK,K_NO,K_ICKAPI,K_URUN) " +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1,k_ad);
                statement.setString(2,sifre);
                statement.setString(3,eposta);
                statement.setString(4,telefon);
                statement.setString(5,isim);
                statement.setString(6,soyisim);
                statement.setString(7,il);
                statement.setString(8,ilce);
                statement.setString(9,mahalle);
                statement.setString(10,sokak);
                statement.setString(11,no);
                statement.setString(12,ickapi);
                statement.setString(13,urun);
                statement.executeUpdate();
                //--------------------------------------------------------------------------//
                connection3 = db.getConnection();
                String sqlGiris = "insert into k_giris.kullanici_girisi (K_KullanAD,K_SIFRE,K_EPOSTA,K_NUMARA,K_AD,K_SOYAD) values (?,?,?,?,?,?)";
                statementGiris = connection3.prepareStatement(sqlGiris);
                statementGiris.setString(1,k_ad);
                statementGiris.setString(2,sifre);
                statementGiris.setString(3,eposta);
                statementGiris.setString(4,telefon);
                statementGiris.setString(5,isim);
                statementGiris.setString(6,soyisim);
                statementGiris.executeUpdate();
                JOptionPane.showMessageDialog(null, "Kullanici Basariyla Eklenmistir.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update() {
        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();
        try {
            connection = db.getConnection();
            String sql = "select k_giris.kullanici_bilgiler.K_KullanAD,k_giris.kullanici_bilgiler.K_SIFRE,k_giris.kullanici_bilgiler.K_AD,k_giris.kullanici_bilgiler.K_SOYAD," +
                    "k_giris.kullanici_bilgiler.K_IL,k_giris.kullanici_bilgiler.K_ILCE," +
                    "k_giris.kullanici_bilgiler.K_MAHALLE,k_giris.kullanici_bilgiler.K_SOKAK,k_giris.kullanici_bilgiler.K_No,k_giris.kullanici_bilgiler.K_ICKAPI," +
                    "k_giris.kullanici_bilgiler.K_URUN from k_giris.kullanici_bilgiler";

            statement = connection.prepareStatement(sql);
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
    public void delete(JTable table1){
            int SecilenSatir = table1.getSelectedRow();
            //Eger secilen satır yoksa -1 degeri dondurecektir.
            if (SecilenSatir != -1) {
                try {
                    String isim = table1.getValueAt(SecilenSatir, 2).toString();
                    String soyisim = table1.getValueAt(SecilenSatir, 3).toString();
                    String k_il = table1.getValueAt(SecilenSatir, 4).toString();
                    String k_ilce = table1.getValueAt(SecilenSatir,5).toString();
                    String k_mahalle = table1.getValueAt(SecilenSatir,6).toString();
                    String k_sokak = table1.getValueAt(SecilenSatir,7).toString();
                    String k_no = table1.getValueAt(SecilenSatir,8).toString();
                    String k_ickapi = table1.getValueAt(SecilenSatir,9).toString();
                    String k_urun = table1.getValueAt(SecilenSatir,10).toString();

                    Connection connection = null;
                    PreparedStatement statement = null;
                    DbHelper db = new DbHelper();
                    PreparedStatement statementGiris = null;

                    try {
                        connection = db.getConnection();
                        String sql = "DELETE FROM k_giris.kullanici_bilgiler WHERE K_IL = ? and K_ILCE=? and K_MAHALLE=? and K_SOKAK=?" +
                                "and K_No =? and K_ICKAPI=? and K_URUN =? and K_AD =? and K_SOYAD =?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1,k_il);
                        statement.setString(2,k_ilce);
                        statement.setString(3,k_mahalle);
                        statement.setString(4,k_sokak);
                        statement.setString(5,k_no);
                        statement.setString(6,k_ickapi);
                        statement.setString(7,k_urun);
                        statement.setString(8,isim);
                        statement.setString(9,soyisim);
                        int affectedRows = statement.executeUpdate();

                        String sqlGiris = "delete from k_giris.kullanici_girisi where K_AD=? and K_SOYAD=?";
                        statementGiris = connection.prepareStatement(sqlGiris);
                        statementGiris.setString(1,isim);
                        statementGiris.setString(2,soyisim);
                        statementGiris.executeUpdate();

                        if (affectedRows > 0) {
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            model.removeRow(SecilenSatir);
                        } else {
                            //Eger silinemezse mesaj gonderilecektir.
                            JOptionPane.showMessageDialog(null, "Silinemedi");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (statement != null) {
                                statement.close();
                            }
                            if (connection != null) {
                                connection.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Lutfen Sileceginiz urunu seciniz.");
            }
        }


    }

