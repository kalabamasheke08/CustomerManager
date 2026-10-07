package org.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final StringProperty name;
    private final StringProperty province;

    public Customer(String name, String province) {
        this.name = new SimpleStringProperty(name);
        this.province = new SimpleStringProperty(province);
    }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getProvince() { return province.get(); }
    public StringProperty provinceProperty() { return province; }
}