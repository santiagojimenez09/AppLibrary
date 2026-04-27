package com.library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookView extends JFrame {

    private JTextField txtId, txtTitle, txtPrice;
    private JTable table;
    private DefaultTableModel model;
    private final BookController controller = new BookController();

    // 🎨 Colores modernos
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(46, 204, 113);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color WARNING = new Color(241, 196, 15);
    private final Color BG = new Color(245, 247, 250);

    public BookView() {
        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("📚 Gestión de Libros");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(15, 15));

        add(createHeader(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY);
        panel.setPreferredSize(new Dimension(100, 60));

        JLabel title = new JLabel("Sistema de Gestión de Libros");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        panel.add(title);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(createFormPanel());
        panel.add(createTablePanel());

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel title = new JLabel("Datos del Libro");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        txtId = createTextField("ID");
        txtTitle = createTextField("Título");
        txtPrice = createTextField("Precio");

        panel.add(title);
        panel.add(txtId);
        panel.add(txtTitle);
        panel.add(txtPrice);

        return panel;
    }

    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(new String[]{"ID", "Título", "Precio"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        return scroll;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(createButton("Agregar", SUCCESS, e -> addBook()));
        panel.add(createButton("Actualizar", PRIMARY, e -> updateBook()));
        panel.add(createButton("Eliminar", DANGER, e -> deleteBook()));
        panel.add(createButton("Buscar", WARNING, e -> searchBook()));
        panel.add(createButton("Refrescar", Color.GRAY, e -> loadData()));

        return panel;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return field;
    }

    private JButton createButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.addActionListener(action);
        return btn;
    }

    // 🔹 Lógica (igual que antes)

    private void addBook() {
        try {
            String title = txtTitle.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());

            controller.createSave(title, price);
            showMessage("Libro agregado");
            loadData();
            clearFields();

        } catch (Exception e) {
            showMessage("Datos inválidos");
        }
    }

    private void updateBook() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String title = txtTitle.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());

            controller.update(id, title, price);
            showMessage("Libro actualizado");
            loadData();
            clearFields();

        } catch (Exception e) {
            showMessage("Error al actualizar");
        }
    }

    private void deleteBook() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());

            controller.delete(id);
            showMessage("Libro eliminado");
            loadData();
            clearFields();

        } catch (Exception e) {
            showMessage("Error al eliminar");
        }
    }

    private void searchBook() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Book b = controller.findById(id);

            if (b != null) {
                txtTitle.setText(b.getTittle());
                txtPrice.setText(String.valueOf(b.getPrice()));
            } else {
                showMessage("No encontrado");
            }

        } catch (Exception e) {
            showMessage("ID inválido");
        }
    }

    private void loadData() {
        model.setRowCount(0);
        for (Book b : controller.list()) {
            model.addRow(new Object[]{b.getId(), b.getTittle(), b.getPrice()});
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtTitle.setText("");
        txtPrice.setText("");
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
