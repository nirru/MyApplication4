package com.shareads.user.myapplication.model;

/**
 * Created by user on 10/27/2016.
 */

public class Book extends Item {

    public Book(Long id, String title, String desc, String imagePath,Double price) {
        super(id, title, desc, imagePath,price);
    }

    public Book() {
        super();
    }

}