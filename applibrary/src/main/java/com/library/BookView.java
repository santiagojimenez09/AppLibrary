package com.library;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javafx.scene.layout.Border;

public class BookView extends JFrame{

    private JTextField txtTitle, txtPrice;
    private JTextField txtId;
    private JTable table;
    private DefaultTableModel model;
    private BookController controller = new BookController();

    public BookView () {
        setTitle("CRUD de libros");
        setSize(800,500);   
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // 🔹 Panel superior (formulario)
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        txtId = new JTextField();
        txtTitle = new JTextField();
        txtPrice = new JTextField();

        topPanel.add(new JLabel("Id Libro a Buscar:"));
        topPanel.add(txtId);
        topPanel.add(new JLabel("Título:"));
        topPanel.add(txtTitle);
        topPanel.add(new JLabel("Precio:"));
        topPanel.add(txtPrice);

        add(topPanel, BorderLayout.NORTH);

        // 🔹 Tabla
        model = new DefaultTableModel(new String[]{"ID", "Título", "Precio"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 Botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton btnAdd = new JButton("Agregar");
        JButton btnUpdate = new JButton("Actualizar");
        JButton btnDelete = new JButton("Eliminar");
        JButton btnSearch = new JButton("Buscar");
        JButton btnRefresh = new JButton("Refrescar");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // Eventos
        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnSearch.addActionListener(e -> searchBook());
        btnRefresh.addActionListener(e -> loadData());

        loadData();

    }

    private void addBook() {
        String title = txtTitle.getText().trim();
        String priceText = txtPrice.getText().trim();

        // Validar título obligatorio
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio");
            return;
        }

        // Validar precio obligatorio
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio es obligatorio");
            return;
        }

        // Validar que sea número
        double price;

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser numérico");
            return;
        }

        controller.createSave(title, price);
        JOptionPane.showMessageDialog(this, "Libro guardado correctamente");
        loadData();
    }

    private void updateBook() {

        String idText = txtId.getText().trim();
        String title = txtTitle.getText().trim();
        String priceText = txtPrice.getText().trim();

        // Validaciones
        if (idText.isEmpty() || title.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        int id;
        double price;

        try {
            id = Integer.parseInt(idText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID o precio inválido");
            return;
        }

        // Confirmación
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Desea actualizar el libro ID: " + id + "?",
                "Confirmar actualización",
                JOptionPane.YES_NO_OPTION
        );

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        // Usar datos modificados del form
        controller.update(id, title, price);

        JOptionPane.showMessageDialog(this, "Libro actualizado correctamente");

        loadData();
        clearFields();
    }

    private void deleteBook() {
        String idText = txtId.getText().trim();

        // Validar que haya ID
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese o busque un ID primero");
            return;
        }

        int id;

        // Validar que sea número
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
            return;
        }

        // Confirmación
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el libro con ID: " + id + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        // Eliminar
        controller.delete(id);

        JOptionPane.showMessageDialog(this, "Libro eliminado correctamente");

        loadData();
        clearFields();

    }

    private void searchBook() {
        try {
            int id = Integer.parseInt(txtId.getText());

            Book book = controller.findById(id);

            if (book != null) {
                txtTitle.setText(book.getTittle());
                txtPrice.setText(String.valueOf(book.getPrice()));
            } else {
                JOptionPane.showMessageDialog(this, "Libro no encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID válido");
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
    


}
