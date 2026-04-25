package com.library;

import java.util.List;

public class BookController {

    //Crear los metodos para ejecutar BoolDAO
    private BookDAO dao = new BookDAO();
    
    public void createSave (String tittle, double price) {
        dao.create(new Book(tittle,price));
    }

    public List<Book> list(){
        return dao.findAll();
    }

    public Book findById(long id){
        return dao.findById(id);
    }

    public void update (long id, String tittle, double price) {
        dao.update(new Book(id,tittle,price));
    }

    public void delete(int id) {
        dao.delete(id);
    }

}
