import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresFonksiyonlar extends JFrame implements Adresler_Icin_Fonksiyonlar{
    //Bu klas adresle alakalı fonksiyonları depolamak için kurulmuştur. Diger classlar burdan miras alıp kullanılması amaçlanmıştır
    public void Il_Belirle(JComboBox Combobox) {
        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();
        try {
            connection = db.getConnection();
            String sql = "select * from tr_adres.iller";
            statement = connection.prepareStatement(sql);
            ResultSet resultSetIl = statement.executeQuery();

            //Resultsetin bos olup olmadıgı kontrol edilir.
            if (resultSetIl.next()) {
                do {
                    Combobox.addItem(resultSetIl.getString("il_adi"));
                } while (resultSetIl.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void Ilce_Belirle(String il,JComboBox ilce_Combo_box,JComboBox Mahalle_Combo_Box) {

        //Ust uste eklenmemesi icin butun elemanlar silinir sonra tekrar yazdırılmak istenir
        ilce_Combo_box.removeAllItems();
        ilce_Combo_box.addItem("İlce giriniz");
        //Elemanlar silindigi icin mahalle combobox otomatik aciliyor bunu engellemek amaclanmıstır
        Mahalle_Combo_Box.setEnabled(false);


        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();
        try {
            connection = db.getConnection();
            String sql = "select * from tr_adres.ilceler where il_adi=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, il);
            ResultSet resultSet = statement.executeQuery();

            //Resultsetin bos olup olmadıgı kontrol edilir.
            if (resultSet.next()) {

                do {
                    String ilce = resultSet.getString("ilce_adi").toString();
                    ilce_Combo_box.addItem(ilce);
                } while (resultSet.next());

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void MahalleBelirle(String il, String ilce,JComboBox Mahalle_Combo_Box,JComboBox Sokak_Combo_Box) {

        //Ust uste eklenmemesi icin butun elemanlar silinir sonra tekrar yazdırılmak istenir
        Mahalle_Combo_Box.removeAllItems();
        Mahalle_Combo_Box.addItem("Mahalle giriniz");
        //Elemanlar silindigi icin Sokak combobox otomatik aciliyor bunu engellemek amaclanmıstır
        Sokak_Combo_Box.setEnabled(false);

        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();
        try {
            connection = db.getConnection();
            String sql = "select * from tr_adres.mahalleler where il_adi=? and ilce_adi=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, il);
            statement.setString(2, ilce);
            ResultSet resultSet = statement.executeQuery();

            //Resultsetin bos olup olmadıgı kontrol edilir.
            if (resultSet.next()) {

                do {
                    String Mahalle = resultSet.getString("mahalle_adi").toString();
                    Mahalle_Combo_Box.addItem(Mahalle);
                } while (resultSet.next());

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void SokakBelirle(String il, String ilce, String Mahalle,JComboBox Sokak_Combo_Box,JTextField Numara_TextField) {
        //Ust uste eklenmemesi icin butun elemanlar silinir sonra tekrar yazdırılmak istenir
        Sokak_Combo_Box.removeAllItems();
        Sokak_Combo_Box.addItem("Sokak giriniz");
        //Elemanlar silindigi icin NumaraTextField otomatik aciliyor bunu engellemek amaclanmıstır
        Numara_TextField.setEnabled(false);

        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();
        try {
            connection = db.getConnection();
            String sql = "select * from tr_adres.sokaklar where il_adi=? and ilce_adi=? and mahalle_adi=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, il);
            statement.setString(2, ilce);
            statement.setString(3, Mahalle);
            ResultSet resultSet = statement.executeQuery();

            //Resultsetin bos olup olmadıgı kontrol edilir.
            if (resultSet.next()) {
                do {
                    String Sokak = resultSet.getString("sokak_adi").toString();
                    Sokak_Combo_Box.addItem(Sokak);
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void delete(JTable table1) {
        int SecilenSatir = table1.getSelectedRow();

        //Eger secilen satır yoksa -1 degeri dondurecektir.
        if (SecilenSatir != -1) {
            try {
                String isim = table1.getValueAt(SecilenSatir, 0).toString();
                String soyisim = table1.getValueAt(SecilenSatir, 1).toString();
                String k_il = table1.getValueAt(SecilenSatir, 2).toString();
                String k_ilce = table1.getValueAt(SecilenSatir,3).toString();
                String k_mahalle = table1.getValueAt(SecilenSatir,4).toString();
                String k_sokak = table1.getValueAt(SecilenSatir,5).toString();
                String k_no = table1.getValueAt(SecilenSatir,6).toString();
                String k_ickapi = table1.getValueAt(SecilenSatir,7).toString();
                String k_urun = table1.getValueAt(SecilenSatir,8).toString();

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
