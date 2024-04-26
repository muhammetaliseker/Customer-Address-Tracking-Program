import javax.swing.*;

public interface Adresler_Icin_Fonksiyonlar {
    void Il_Belirle(JComboBox Combobox);
    void Ilce_Belirle(String il,JComboBox ilce_Combo_box,JComboBox Mahalle_Combo_Box);
    void MahalleBelirle(String il, String ilce,JComboBox Mahalle_Combo_Box,JComboBox Sokak_Combo_Box);
    void SokakBelirle(String il, String ilce, String Mahalle,JComboBox Sokak_Combo_Box,JTextField Numara_TextField);
    void delete(JTable table1);

}
