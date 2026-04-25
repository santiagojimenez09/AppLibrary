package com.library;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    // Metodos para el CRUD
    // Crear libro
    public void create(Book book){

        String sql = "INSERT INTO book (tittle, price) values(?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            
                ps.setString(1, book.getTittle());
                ps.setDouble(2, book.getPrice());
                ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Book> findAll () {

        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)){
            
                while (rs.next()) {
                    
                    list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("tittle"),
                        rs.getDouble("price")
                    ));

                }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Book findById (long id) {

        String sql = "SELECT * FROM book WHERE id = ?";
        Book book = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setLong(1,id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    book = new Book(
                        rs.getInt("id"),
                        rs.getString("tittle"),
                        rs.getDouble("price")
                    );
                    
                }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return book;

    }

    public void update(Book book) {

        String sql = "UPDATE book SET tittle=?, price=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setString(1, book.getTittle());
                ps.setDouble(2, book.getPrice());
                ps.setLong(3, book.getId());
                ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    } 

    public void delete (int id){

        String sql = "DELETE FROM book WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setInt(1, id);
                ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    

}
