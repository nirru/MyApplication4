package com.shareads.user.myapplication;

import java.math.BigDecimal;

/**
 * Created by user on 11/13/2016.
 */

public class book {
    private String book_name;
    private String book_sale;
    private String book_points;
    private Integer book_image;
    private Integer book_explore;
    BigDecimal pPrice;

    public book() {
        super();
    }

    public book( String book_name, BigDecimal pPrice, String book_points, Integer book_image,Integer book_explore) {
        setbook_name(book_name);
        setBook_image(book_image);
        setBook_points(book_points);
        setbook_sale(book_sale);
        setPrice(pPrice);
        setbook_explore(book_explore);
    }
    public BigDecimal getPrice() {
        return pPrice;
    }
    public void setPrice(BigDecimal price) {
        this.pPrice = price;
    }
    public String getBook_name()
    {
        return book_name;
    }
    public void setbook_name ( String book_name )
    {
    this.book_name=book_name;
    }
    public String getBook_sale()
    {
        return book_sale;
    }
    public void setbook_sale ( String book_sale )
    {
        this.book_sale=book_sale;
    }
    public String getBook_points()
    {
        return book_points;
    }
    public void setBook_points ( String book_points )
    {
        this.book_points=book_points;
    }
    public Integer getBook_image()
    {
        return book_image;
    }

    public void setBook_image(Integer book_image)
    {
        this.book_image=book_image;
    }

    public void setbook_explore(Integer book_explore) {
        this.book_explore = book_explore;
    }
    public Integer getbook_explore()
    {
        return book_explore;
    }
}

