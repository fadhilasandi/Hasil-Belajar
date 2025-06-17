import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator {
    private JTextField num1Field, num2Field, resultField;
    private JButton addButton, subtractButton, multiplyButton, divideButton, clearButton;

    public Calculator() {
        // set up bingkai
        JFrame frame = new JFrame("Kalkulator GUI Simpel");
        frame.setSize(480, 272);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Heading atas
        JLabel headingLabel = new JLabel("Kalkulator GUI Oleh Fadhil", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(headingLabel, BorderLayout.NORTH);

        //buat form panel
        JPanel formpanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formpanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //Nomor 1
        JLabel num1Label = new JLabel("Nomor 1");
        num1Field = new JTextField();
        formpanel.add(num1Label);
        formpanel.add(num1Field);

        //nomor 2
        JLabel num2Label = new JLabel("Nomor 2");
        num2Field = new JTextField();
        formpanel.add(num2Label);
        formpanel.add(num2Field);

        //tampilan hasil
        JLabel resultLabel = new JLabel("Hasil:");
        resultField = new JTextField();
        resultField.setEditable(false);
        formpanel.add(resultLabel);
        formpanel.add(resultField);

        //tombol operasi
        addButton = new JButton("Tambah");
        subtractButton = new JButton("Kurang");
        multiplyButton = new JButton("Kali");
        divideButton = new JButton("Bagi");
        clearButton = new JButton("Hapus");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(clearButton);

        //tambah komponen ke frame
        frame.add(formpanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //tambah Action Listener pake Lambda
        ActionListener listener = e -> handleOperation(e.getActionCommand());
        addButton.addActionListener(listener);
        subtractButton.addActionListener(listener);
        multiplyButton.addActionListener(listener);
        divideButton.addActionListener(listener);
        clearButton.addActionListener(e -> clearFields());

        frame.setVisible(true);
    }

    private void handleOperation(String operation){
        try {
            double num1 = Double.parseDouble(num1Field.getText());
            double num2 = Double.parseDouble(num2Field.getText());
            double result = 0;

            switch (operation) {
                case "Tambah":
                    result = num1 + num2;
                    break;
                case "Kurang":
                    result = num1 - num2;
                    break;
                case "Kali":
                    result = num1 * num2;
                    break;
                case "Bagi":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        resultField.setText("Tidak bisa dibagi 0.");
                        return;
                    }
                    break;
                default:
                    resultField.setText("Error: Operasi Tidak Diketahui.");
                    return;
            }

            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultField.setText("Mohon dimasukkan nomor yang valid.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearFields() {
        num1Field.setText("");
        num2Field.setText("");
        resultField.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
