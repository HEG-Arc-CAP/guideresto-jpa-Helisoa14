package ch.hearc.ig.guideresto.business;

import javax.persistence.*;

//Embedded, je ne suis pas capable de me mapper tout seul
@Embeddable
public class Localisation {

    @Column(name = "adresse")
    private String street;

    @ManyToOne
    @JoinColumn(name = "fk_vil")
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

    //Dès qu'on n'a pas d'identité, l'egalité est fait sur la somme de ces attributs
    @Override
    public boolean equals(Object o) {
        // C'est une valeur, elle ne possède pas d'identité
        // Son égalité est définit sur la somme de ses attributs
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localisation that = (Localisation) o;

        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        return city != null ? city.equals(that.city) : that.city == null;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}