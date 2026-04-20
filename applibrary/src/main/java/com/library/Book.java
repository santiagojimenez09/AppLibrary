package com.library;

public class Book {

    private long id;
    private String tittle;
    private Double price;
    
    
    public Book() {
    }


    public Book(long id, String tittle, Double price) {
        this.id = id;
        this.tittle = tittle;
        this.price = price;
    }


    public Book(String tittle, Double price) {
        this.tittle = tittle;
        this.price = price;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getTittle() {
        return tittle;
    }


    public void setTittle(String tittle) {
        this.tittle = tittle;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    


}
