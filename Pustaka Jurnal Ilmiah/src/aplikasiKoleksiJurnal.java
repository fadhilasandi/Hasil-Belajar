import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class aplikasiKoleksiJurnal extends JFrame {
    private JTable tabelKoleksi;
    private DefaultTableModel modelTabel;
    private List<koleksiJurnal> collections;

    private JTextField nama, editor, berdiri, issn;
    private JTextField penerbit, frekuensi, cari, jumlahJurnal;
    private JTextArea areaDeskripsi, areaTopik;
    private JComboBox<String> comboKategori, comboTipeAkses;

    public aplikasiKoleksiJurnal() {
        collections = new ArrayList<>();
        initializeUI();
        loadDataJurnal();
    }

    private void initializeUI() {
        setTitle("Koleksi Jurnal Akademik");
        setSize(1300, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Panel Atas - cari
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCari.setBorder(BorderFactory.createTitledBorder("Cari Koleksi"));
        cari = new JTextField(30);
        JButton tblCari = new JButton("Cari");
        JButton tblBersihPencarioan = new JButton("Bersihkan");

        tblCari.addActionListener(e -> cariKoleksi());
        tblBersihPencarioan.addActionListener(e -> {
            cari.setText("");
            loadDataTabel(collections);
        });

        panelCari.add(new JLabel("Kata Kunci:"));
        panelCari.add(cari);
        panelCari.add(tblCari);
        panelCari.add(tblBersihPencarioan);

        //Panel Tengah - Tabel
        String[] kolom = {"Nama Koleksi", "Penerbit", "Kategori", "Tipe Akses", "Jumlah Jurnal", "Berdiri", "ISSN"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int baris, int column) {
                return false;
            }
        };

        tabelKoleksi = new JTable(modelTabel);
        tabelKoleksi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelKoleksi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displaySelectedCollection();
            }
        });

        JScrollPane scrollTabel = new JScrollPane(tabelKoleksi);
        scrollTabel.setBorder(BorderFactory.createTitledBorder("Database Koleksi Jurnal"));

        //Panel Kiri
        JPanel panelKiri = new JPanel(new BorderLayout(5, 5));
        panelKiri.setPreferredSize(new Dimension(450, 0));

        //panel Form
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Detail Koleksi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        nama = new JTextField(20);
        penerbit = new JTextField(20);
        editor = new JTextField(20);
        berdiri = new JTextField(20);
        issn = new JTextField(20);
        frekuensi = new JTextField(20);
        jumlahJurnal = new JTextField(20);

        String[] kategori = {"Sains & Teknologi", "Kesehatan & Medis", "Sains Sosial", "Humaniora", "Teknik", "Bisnis & Ekonomi", "MultiDisiplin"};
        comboKategori = new JComboBox<>(kategori);

        String[] tipeAkses = {"Open Access", "Subscription", "Hybrid", "Pay-per-view"};
        comboTipeAkses = new JComboBox<>(tipeAkses);

        areaDeskripsi = new JTextArea(4, 20);
        areaDeskripsi.setLineWrap(true);
        areaDeskripsi.setWrapStyleWord(true);
        JScrollPane scrollDeskripsi = new JScrollPane(areaDeskripsi);

        areaTopik = new JTextArea(3, 20);
        areaTopik.setLineWrap(true);
        areaTopik.setWrapStyleWord(true);
        JScrollPane scrollTopik = new JScrollPane(areaTopik);

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Nama Jurnal:"), gbc);
        gbc.gridx = 1;
        panelForm.add(nama, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Penerbit:"), gbc);
        gbc.gridx = 1;
        panelForm.add(penerbit, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Editor:"), gbc);
        gbc.gridx = 1;
        panelForm.add(editor, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Kategori:"), gbc);
        gbc.gridx = 1;
        panelForm.add(comboKategori, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Tipe Akses:"), gbc);
        gbc.gridx = 1;
        panelForm.add(comboTipeAkses, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Jumlah Jurnal:"), gbc);
        gbc.gridx = 1;
        panelForm.add(jumlahJurnal, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Berdiri:"), gbc);
        gbc.gridx = 1;
        panelForm.add(berdiri, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("ISSN:"), gbc);
        gbc.gridx = 1;
        panelForm.add(issn, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Frekuensi:"), gbc);
        gbc.gridx = 1;
        panelForm.add(frekuensi, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelForm.add(new JLabel("Deskripsi:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        panelForm.add(scrollDeskripsi, gbc);
        row += 2;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridheight = 2;
        panelForm.add(new JLabel("Topik:"), gbc);
        gbc.gridx = 1;
        panelForm.add(scrollTopik, gbc);

        //Panel Tombol
        JPanel panelTbl = new JPanel(new FlowLayout());
        JButton tblTambah = new JButton("Tambah Jurnal");
        JButton tblUpdate = new JButton("Update");
        JButton tblHapus = new JButton("Hapus");
        JButton tblbersih = new JButton("Bersihkan");
        JButton tblStat = new JButton("Statistik");

        tblTambah.addActionListener(e -> tambahJurnal());
        tblUpdate.addActionListener(e -> updateJurnal());
        tblHapus.addActionListener(e -> hapusJurnal());
        tblbersih.addActionListener(e -> clearForm());
        tblStat.addActionListener(e -> lihatStatistik());

        panelTbl.add(tblTambah);
        panelTbl.add(tblUpdate);
        panelTbl.add(tblHapus);
        panelTbl.add(tblbersih);
        panelTbl.add(tblStat);

        panelKiri.add(panelForm, BorderLayout.CENTER);
        panelKiri.add(panelTbl, BorderLayout.SOUTH);

        //tambah semua panel ke frame
        add(panelCari, BorderLayout.NORTH);
        add(scrollTabel, BorderLayout.CENTER);
        add(panelKiri, BorderLayout.EAST);

        createMenuBar();
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int baris, String label, JComponent field){
        gbc.gridx = 0;
        gbc.gridy = baris;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem eksporItem = new JMenuItem("Ekspor ke CSV");
        JMenuItem imporItem = new JMenuItem("Impor Jurnal");
        JMenuItem itemKeluar = new JMenuItem("Keluar");

        eksporItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Fungsionalitas ekspor akan diimplementasikan disini"));
        imporItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Fungsionalitas impor akan diimplementasikan disini"));
        itemKeluar.addActionListener(e -> System.exit(0));

        menuFile.add(imporItem);
        menuFile.add(eksporItem);
        menuFile.addSeparator();
        menuFile.add(itemKeluar);

        JMenu menuLihat = new JMenu("Lihat");
        JMenuItem filterOpenAccess = new JMenuItem("Filter Open Access");
        JMenuItem filterSubscription = new JMenuItem("Filter Subscription");
        JMenuItem lihatSemua = new JMenuItem("Lihat Semua");

        filterOpenAccess.addActionListener(e -> filterBerdasarTipeAkses("Open Access"));
        filterSubscription.addActionListener(e -> filterBerdasarTipeAkses("Subscription"));
        lihatSemua.addActionListener(e -> loadDataTabel(collections));

        menuLihat.add(filterOpenAccess);
        menuLihat.add(filterSubscription);
        menuLihat.addSeparator();
        menuLihat.add(lihatSemua);

        JMenu menuBantuan = new JMenu("Bantuan");
        JMenuItem itemTentang = new JMenuItem("Tentang");
        itemTentang.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Koleksi Jurnal Akademik\nVersi 1.0\n\n", "Tentang", JOptionPane.INFORMATION_MESSAGE));

        menuBantuan.add(itemTentang);

        menuBar.add(menuFile);
        menuBar.add(menuLihat);
        menuBar.add(menuBantuan);

        setJMenuBar(menuBar);
    }

    private void loadDataJurnal(){
        collections.add(new koleksiJurnal(
                "ACM Computing Surveys", "Association of Computing Machinery", "My. T. Thai",
                "Sains & Teknologi", "Open Access", "3000", "1969", "0360-0300",
                "Bulanan", "Survei dan makalah tutorial yang komprehensif dan mudah dipahami ini memberikan panduan melalui literatur dan menjelaskan topik-topik kepada mereka yang ingin mempelajari dasar-dasar bidang di luar keahlian mereka dengan cara yang mudah dipahami.",
                "Teknologi"
        ));

        loadDataTabel(collections);
    }

    private void loadDataTabel(List<koleksiJurnal> koleksiJurnalList) {
        modelTabel.setRowCount(0);
        for (koleksiJurnal k : koleksiJurnalList) {
            modelTabel.addRow(new Object[]{
                    k.nama, k.penerbit, k.kategori, k.tipeAkses, k.jumlahJurnal, k.berdiri, k.issn
            });
        }
    }

    private void displaySelectedCollection() {
        int selectedRow = tabelKoleksi.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < collections.size()) {
            koleksiJurnal k = collections.get(selectedRow);
            nama.setText(k.nama);
            penerbit.setText(k.penerbit);
            editor.setText(k.editor);
            comboKategori.setSelectedItem(k.kategori);
            comboTipeAkses.setSelectedItem(k.tipeAkses);
            jumlahJurnal.setText(k.jumlahJurnal);
            berdiri.setText(k.berdiri);
            issn.setText(k.issn);
            frekuensi.setText(k.frekuensi);
            areaDeskripsi.setText(k.deskripsi);
            areaTopik.setText(k.topik);
        }
    }

    private void tambahJurnal() {
        if (validateInput()) {
            koleksiJurnal jurnalBaru = new koleksiJurnal(
                    nama.getText(),
                    penerbit.getText(),
                    editor.getText(),
                    (String)comboKategori.getSelectedItem(),
                    (String)comboTipeAkses.getSelectedItem(),
                    jumlahJurnal.getText(),
                    berdiri.getText(),
                    issn.getText(),
                    frekuensi.getText(),
                    areaDeskripsi.getText(),
                    areaTopik.getText()
            );

            collections.add(jurnalBaru);
            loadDataTabel(collections);
            clearForm();
            JOptionPane.showMessageDialog(this, "Jurnal berhasil ditambahkan!");
        }
    }

    private void updateJurnal() {
        int selectedRows = tabelKoleksi.getSelectedRow();
        if (selectedRows >= 0 && selectedRows <collections.size() && validateInput()) {
            koleksiJurnal K = collections.get(selectedRows);
            K.nama = nama.getText();
            K.penerbit = penerbit.getText();
            K.editor = editor.getText();
            K.kategori = (String) comboKategori.getSelectedItem().toString();
            K.tipeAkses = (String) comboTipeAkses.getSelectedItem().toString();
            K.jumlahJurnal = jumlahJurnal.getText();
            K.berdiri = berdiri.getText();
            K.issn = issn.getText();
            K.frekuensi = frekuensi.getText();
            K.deskripsi = areaDeskripsi.getText();
            K.topik = areaTopik.getText();

            loadDataTabel(collections);
            JOptionPane.showMessageDialog(this, "Jurnal berhasil diupdate!");
        } else if (selectedRows < 0) {
            JOptionPane.showMessageDialog(this, "Silahkan pilih jurnal untuk diupdate");
        }
    }

    private void hapusJurnal() {
        int selectedRows = tabelKoleksi.getSelectedRow();
        if (selectedRows >= 0 && selectedRows < collections.size()) {
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                    "Apakah anda yakin mau menghapus jurnal tersebut?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                collections.remove(selectedRows);
                loadDataTabel(collections);
                clearForm();
                JOptionPane.showMessageDialog(this, "Jurnal berhasil dihapus!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih jurnal yang mau dihapus");
        }
    }

    private void cariKoleksi() {
        String kataKunci = cari.getText().toLowerCase();
        if (kataKunci.isEmpty()) {
            loadDataTabel(collections);
            return;
        }

        List<koleksiJurnal> hasil = new ArrayList<>();
        for (koleksiJurnal K : collections) {
            if (K.nama.toLowerCase().contains(kataKunci) ||
            K.penerbit.toLowerCase().contains(kataKunci) ||
            K.kategori.toLowerCase().contains(kataKunci) ||
            K.topik.toLowerCase().contains(kataKunci) ||
            K.deskripsi.toLowerCase().contains(kataKunci)) {
                hasil.add(K);
            }
        }

        loadDataTabel(hasil);
        if (hasil.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada jurnal yang cocok: " + kataKunci);
        }
    }

    private void filterBerdasarTipeAkses(String tipeAkses) {
        List<koleksiJurnal> filtered = new ArrayList<>();
        for (koleksiJurnal k : collections) {
            if (k.tipeAkses.equals(tipeAkses)) {
                filtered.add(k);
            }
        }
        loadDataTabel(filtered);
    }

    private void lihatStatistik() {
        int totalKoleksi = collections.size();
        int totalJurnal = 0;
        int openAccess = 0;

        for (koleksiJurnal K : collections) {
            try {
                totalJurnal += Integer.parseInt(K.jumlahJurnal);
            } catch (NumberFormatException e) {}

            if (K.tipeAkses.equals("Open Access")) {
                openAccess++;
            }
        }

        String stat = String.format(
                "Statistik Koleksi:\n\n" +
                        "Total Koleksi: %d\n" +
                        "Total Jurnal: %d\n" +
                        "Koleksi Open Access: %d\n" +
                        "Koleksi Subscription: %d",
                totalKoleksi, totalJurnal, openAccess, totalKoleksi - openAccess
        );

        JOptionPane.showMessageDialog(this, stat, "Statistik", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean validateInput() {
        if (nama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama koleksi diperlukan");
            return false;
        }
        if (penerbit.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Penerbit diperlukan");
            return false;
        }
        return true;
    }

    private void clearForm() {
        nama.setText("");
        penerbit.setText("");
        editor.setText("");
        berdiri.setText("");
        issn.setText("");
        frekuensi.setText("");
        jumlahJurnal.setText("");
        areaDeskripsi.setText("");
        areaTopik.setText("");
        comboKategori.setSelectedItem(0);
        comboTipeAkses.setSelectedItem(0);
        tabelKoleksi.clearSelection();
    }

    static class koleksiJurnal {
        String nama, penerbit, editor, kategori, tipeAkses, jumlahJurnal;
        String berdiri, issn, frekuensi, deskripsi, topik;

        koleksiJurnal(String nama, String penerbit, String editor, String kategori,
                      String tipeAkses, String jumlahJurnal, String berdiri, String issn,
                      String frekuensi, String deskripsi, String topik) {
            this.nama = nama;
            this.penerbit = penerbit;
            this.editor = editor;
            this.kategori = kategori;
            this.tipeAkses = tipeAkses;
            this.jumlahJurnal = jumlahJurnal;
            this.berdiri = berdiri;
            this.issn = issn;
            this.frekuensi = frekuensi;
            this.deskripsi = deskripsi;
            this.topik = topik;
        }
    }

    public static void main(String[]args) {
        SwingUtilities.invokeLater(() -> {
            aplikasiKoleksiJurnal app = new aplikasiKoleksiJurnal();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}

