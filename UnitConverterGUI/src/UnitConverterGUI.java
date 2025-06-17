import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class UnitConverterGUI extends JFrame {
    private JComboBox<String> ComboBoxkategori;
    private JComboBox<String> ComboBoxdariUnit;
    private JComboBox<String> ComboBoxkeUnit;
    private JTextField inputField;
    private JTextField outputField;
    private JButton Buttonubah;
    private JButton Buttonhapus;
    private DecimalFormat df = new DecimalFormat("#.##########");

    //Kategori konversi dan unitnya dengan faktor konversi ke unit dasar
    private Map<String, Map<String, Double>> konversi;

    public UnitConverterGUI() {
        konversi = new HashMap<>();
        initializeConversions();
        setupGUI();
    }

    private void initializeConversions() {

        //konversi panjang (satuan dasar : meter)
        Map<String, Double> panjang = new HashMap<>();
        panjang.put("Millimeter", 0.001);
        panjang.put("Centimeter", 0.01);
        panjang.put("Meter", 1.0);
        panjang.put("Kilometer", 1000.0);
        panjang.put("Inci", 0.0254);
        panjang.put("Kaki", 0.3048);
        panjang.put("Yard", 0.9144);
        panjang.put("Mil", 1609.34);
        konversi.put("Panjang", panjang);

        //Konversi Berat (satuan dasar : kilogram)
        Map<String, Double> berat = new HashMap<>();
        berat.put("Miligram", 0.000001);
        berat.put("Gram", 0.001);
        berat.put("Kilogram", 1.0);
        berat.put("Ons", 0.0283495);
        berat.put("Pon", 0.453592);
        berat.put("Ton", 1000.0);
        konversi.put("Berat", berat);

        //konversi temperatur (dibutuhkan penanganan khusus)
        Map<String, Double> suhu = new HashMap<>();
        suhu.put("Celsius", 1.0);
        suhu.put("Fahrenheit", 1.0);
        suhu.put("Kelvin", 1.0);
        konversi.put("Suhu", suhu);

        //konversi volume (satuan dasar: liter)
        Map<String, Double> volume = new HashMap<>();
        volume.put("Mililiter", 0.001);
        volume.put("Liter", 1.0);
        volume.put("Galon (Amerika)", 3.78541);
        volume.put("Galon (Inggris Raya)", 4.54609);
        volume.put("Quart (Amerika)", 0.946353);
        volume.put("Pint (Amerika)", 0.473176);
        volume.put("Botol (Amerika)", 0.236588);
        volume.put("Ons Cairan (Amerika)", 0.0295735);
        konversi.put("Volume", volume);

        //konversi Area (satuan dasar: meter persegi)
        Map<String, Double> area = new HashMap<>();
        area.put("Milimeter persegi", 0.000001);
        area.put("Centimeter Persegi", 0.0001);
        area.put("Meter Persegi", 1.0);
        area.put("Kilometer Persegi", 1000000.0);
        area.put("Inci Persegi", 0.00064516);
        area.put("Kaki Persegi", 0.092903);
        area.put("Square Yard", 0.836127);
        area.put("acre", 4046.86);
        area.put("Hektar", 10000.0);
        konversi.put("Area", area);
    }

    private void setupGUI() {
        setTitle("Konversi Unit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel Utama
        JPanel Panelutama = new JPanel(new GridBagLayout());
        Panelutama.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        //Judul
        JLabel Labeljudul = new JLabel("Konversi Unit");
        Labeljudul.setFont(new Font("Arial", Font.BOLD, 24));
        Labeljudul.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        Panelutama.add(Labeljudul, gbc);

        //Pemilihan kategori
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1;
        Panelutama.add(new JLabel("Kategori: "), gbc);

        ComboBoxkategori = new JComboBox<>(konversi.keySet().toArray(new String[0]));
        ComboBoxkategori.addActionListener(e -> updateUnitComboBoxes());
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Panelutama.add(ComboBoxkategori, gbc);

        //Dari Unit
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0; gbc.gridy = 2;
        Panelutama.add(new JLabel("Dari: "), gbc);

        ComboBoxdariUnit = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Panelutama.add(ComboBoxdariUnit, gbc);

        //Ke Unit
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2; gbc.gridy = 2;
        Panelutama.add(new JLabel("Ke:"), gbc);

        ComboBoxkeUnit = new JComboBox<>();
        gbc.gridx = 3; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Panelutama.add(ComboBoxkeUnit, gbc);

        //Input Field
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0; gbc.gridy = 3;
        Panelutama.add(new JLabel("Input: "), gbc);

        inputField = new JTextField(15);
        inputField.addActionListener(e -> performConversion());
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Panelutama.add(inputField, gbc);

        //Output Field
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridx = 4;
        Panelutama.add(new JLabel("Hasil: "), gbc);

        outputField = new JTextField(15);
        outputField.setEditable(false);
        outputField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Panelutama.add(outputField, gbc);

        //Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout());
        Buttonubah = new JButton("Konversi: ");
        Buttonubah.addActionListener(e -> performConversion());
        Buttonubah.setPreferredSize(new Dimension(100, 30));

        Buttonhapus = new JButton("Hapus");
        Buttonhapus.addActionListener(e -> clearFields());
        Buttonhapus.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(Buttonubah);
        buttonPanel.add(Buttonhapus);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 5, 5, 5);
        Panelutama.add(buttonPanel, gbc);

        add(Panelutama, BorderLayout.CENTER);

        //Inisialisasi dengan kategori pertama
        updateUnitComboBoxes();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void updateUnitComboBoxes() {
        String selectedCategory = (String) ComboBoxkategori.getSelectedItem();
        if (selectedCategory != null) {
            Map<String, Double> units = konversi.get(selectedCategory);
            String[] unitNames = units.keySet().toArray(new String[0]);

            ComboBoxdariUnit.removeAllItems();
            ComboBoxkeUnit.removeAllItems();

            for (String unit : unitNames) {
                ComboBoxdariUnit.addItem(unit);
                ComboBoxkeUnit.addItem(unit);
            }

            //Atur pilihan default jika memungkinkan
            if (unitNames.length > 1) {
                ComboBoxkeUnit.setSelectedIndex(1);
            }
        }
        clearFields();
    }

    private void performConversion() {
        try {
            String inputText = inputField.getText().trim();
            if (inputText.isEmpty()) {
                outputField.setText("");
                return;
            }

            double inputValue = Double.parseDouble(inputText);
            String kategori = (String) ComboBoxkategori.getSelectedItem();
            String dariUnit = (String) ComboBoxdariUnit.getSelectedItem();
            String keUnit = (String) ComboBoxkeUnit.getSelectedItem();

            double result;

            if ("Suhu".equals(kategori)) {
                result = convertTemperature(inputValue, dariUnit, keUnit);
            } else {
                Map<String, Double> units = konversi.get(kategori);
                double fromFactor = units.get(dariUnit);
                double tofactor = units.get(keUnit);

                //ubah ke unit dasar, lalu ke unit tujuan
                result = (inputValue * fromFactor) / tofactor;
            }

            outputField.setText(df.format(result));

        } catch (NumberFormatException e) {
            outputField.setText("Input salah");
        } catch (Exception e) {
            outputField.setText("Error");
        }
    }

    private double convertTemperature(double value, String dari, String ke) {
        //ubah ke Celsius dulu
        double celsius;
        switch (dari) {
            case "Celsius" :
                celsius = value;
                break;
            case "Fahrenheit" :
                celsius = (value - 32) * 5.0 / 9.0;
                break;
            case "Kelvin" :
                celsius = value - 273.15;
                break;
            default:
                throw new IllegalArgumentException("Unit Temperatur tidak diketahui: " + dari);
        }

        //Ubah dari Celsius ke tujuan
        switch (ke) {
            case "Celsius" :
                return celsius;
            case "Fahrenheit" :
                return celsius * 9.0 / 5.0 + 32;
            case "Kelvin" :
                return celsius + 273.15;
            default:
                throw new IllegalArgumentException("Unit Temperatur Tidak Diketahui: " + ke);
        }
    }

    private void clearFields() {
        inputField.setText("");
        outputField.setText("");
        inputField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                //Use default look and feel
            }

            new UnitConverterGUI().setVisible(true);
        });
    }
}
