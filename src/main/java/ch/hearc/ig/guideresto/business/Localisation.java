package ch.hearc.ig.guideresto.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name = "RESTAURANTS")
public class Localisation {

    @Column(name = "adresse")
    private String street;
    @Transient
    private City city;

    public Localisation() {
        this(null, null);
    }
    
    public Localisation(String street, City city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}