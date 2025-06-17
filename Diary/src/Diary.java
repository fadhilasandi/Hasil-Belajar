import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class Diary extends JFrame {
    private JTextArea teksDiari;
    public Diary() {
        setTitle("Personal Diari");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        teksDiari = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(teksDiari);
        JButton tombolSimpan = new JButton("Simpan");
        tombolSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simpan();
            }
        });
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(tombolSimpan, BorderLayout.SOUTH);
    }
    private void Simpan() {
        try (FileWriter writer = new FileWriter("Diari.txt", true)) {
            writer.write(teksDiari.getText() + "\n");
            teksDiari.setText("");
            JOptionPane.showMessageDialog(this, "Entri Berhasil Tersimpan.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Ketika Menyimpan.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Diary app = new Diary();
            app.setVisible(true);
        });
    }
}
