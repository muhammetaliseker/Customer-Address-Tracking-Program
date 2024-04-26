import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Kullanici extends Kullanici_Eksik{

    private String kAdi;
    private String kSifre;

    private String Il;

    private String Ilce;
    private String Mahalle;

    private String Sokak;
    private String No;
    private String IcKapi;
    private String Urun;

    public Kullanici(String K_Ad,String K_Sifre,String K_Eposta,String K_Numara,String K_AdGercek,String K_Soyad,
                     String Il,String Ilce,
                     String Mahalle, String Sokak,String No,
                     String Ickapi,String Urun){

        super(K_Ad,K_Sifre,K_Eposta,K_Numara,K_AdGercek,K_Soyad);

        this.Il = Il;
        this.Ilce = Ilce;
        this.Mahalle = Mahalle;
        this.Sokak = Sokak;
        this.No = No;
        this.IcKapi = Ickapi;
        this.Urun = Urun;}

    public void Kullanici_To_Database(Kullanici kullanici) {
        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper db = new DbHelper();

        try {
            connection = db.getConnection();
            String sql = "insert into k_giris.kullanici_bilgiler (K_KullanAD,K_SIFRE,K_EPOSTA,K_NUMARA,K_AD,K_SOYAD,K_IL,K_ILCE,K_MAHALLE,K_SOKAK,K_NO,K_ICKAPI,K_URUN) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,kullanici.getK_Ad());
            statement.setString(2,kullanici.getK_Sifre());
            statement.setString(3,kullanici.getK_Eposta());
            statement.setString(4,kullanici.getK_Numara());
            statement.setString(5,kullanici.getK_AdGercek());
            statement.setString(6,kullanici.getK_Soyad());
            statement.setString(7,kullanici.getIl());
            statement.setString(8,kullanici.getIlce());
            statement.setString(9,kullanici.getMahalle());
            statement.setString(10,kullanici.getSokak());
            statement.setString(11,kullanici.getNo());
            statement.setString(12,kullanici.getIcKapi());
            statement.setString(13,kullanici.getUrun());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Urun basariyla satin alinmistir.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public String getK_Ad() {
        return super.getK_Ad();
    }

    @Override
    public String getK_AdGercek() {
        return super.getK_AdGercek();
    }

    @Override
    public String getK_Eposta() {
        return super.getK_Eposta();
    }

    @Override
    public String getK_Numara() {
        return super.getK_Numara();
    }

    @Override
    public String getK_Sifre() {
        return super.getK_Sifre();
    }

    @Override
    public String getK_Soyad() {
        return super.getK_Soyad();
    }


    public String getIcKapi() {
        return IcKapi;
    }

    public String getIl() {
        return Il;
    }

    public String getIlce() {
        return Ilce;
    }

    public String getkAdi() {
        return kAdi;
    }

    public String getkSifre() {
        return kSifre;
    }

    public String getMahalle() {
        return Mahalle;
    }

    public String getNo() {
        return No;
    }

    public String getSokak() {
        return Sokak;
    }

    public String getUrun() {
        return Urun;
    }

}

