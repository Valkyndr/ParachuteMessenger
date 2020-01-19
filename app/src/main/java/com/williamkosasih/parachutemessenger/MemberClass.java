package com.williamkosasih.parachutemessenger;

public class MemberClass {
    private String name;
    private String color;

    MemberClass() {
    }

    MemberClass(String name, String colour) {
        this.name = name;
        this.color = colour;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
