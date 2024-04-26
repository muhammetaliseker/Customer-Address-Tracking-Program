public class Kullanici_Eksik {
    private String K_Ad;
    private String K_Sifre;
    private String K_Eposta;
    private String K_Numara;
    private String K_AdGercek;
    private String K_Soyad;

    public Kullanici_Eksik(String K_Ad,String K_Sifre,String K_Eposta,String K_Numara,String K_AdGercek,String K_Soyad){
        this.K_Ad = K_Ad;
        this.K_Sifre = K_Sifre;
        this.K_Eposta = K_Eposta;
        this.K_Numara = K_Numara;
        this.K_AdGercek = K_AdGercek;
        this.K_Soyad = K_Soyad;
    }

    public String getK_Ad() {
        return K_Ad;
    }

    public String getK_AdGercek() {
        return K_AdGercek;
    }

    public String getK_Eposta() {
        return K_Eposta;
    }

    public String getK_Numara() {
        return K_Numara;
    }

    public String getK_Sifre() {
        return K_Sifre;
    }

    public String getK_Soyad() {
        return K_Soyad;
    }
}
