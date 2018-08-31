package commons_study_test;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class Book {

    private String name;

    private String isbn;

    private double retailPrice;

    public Book() {

    }

    public Book(String name, String isbn, double retailPrice) {

        this.name = name;

        this.isbn = isbn;

        this.retailPrice = retailPrice;

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)

                .append("name", name)

                .append("ISBN", isbn)

                .append("retailPrice", retailPrice)

                .toString();

    }

    public String getIsbn() {

        return isbn;

    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public double getRetailPrice() {

        return retailPrice;

    }

    public void setRetailPrice(double retailPrice) {

        this.retailPrice = retailPrice;

    }
}
