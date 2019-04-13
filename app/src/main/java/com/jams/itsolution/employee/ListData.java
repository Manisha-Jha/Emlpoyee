package com.jams.itsolution.employee;

import java.io.Serializable;

public class ListData implements Serializable {
    private String id, name, gen, image, des, age;

    public ListData() {
    }

    public ListData(String title, String id, String image, String des, String age, String gen) {

        this.name = title;
        this.id = id;
        this.image = image;
        this.des =  des;
        this.age = age;
        this.gen = gen;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }





}