package pojo;

import java.math.BigDecimal;

/**
 * Author: Chen zheng you
 * CreateTime: 2018-12-25 17:10
 * Description:
 */
public class User {

    private Integer id;

    private Integer sex;

    private String name;

    private BigDecimal height;

    public User() {

    }

    public User(Integer id, Integer sex, String name, BigDecimal height) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.height = height;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, BigDecimal height) {
        this.name = name;
        this.height = height;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", height=" + height +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }
}
