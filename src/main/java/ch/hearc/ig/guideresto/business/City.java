package ch.hearc.ig.guideresto.business;

import java.util.Objects;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="VILLES")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIL")
    @SequenceGenerator(name="SEQ_VIL", sequenceName = "SEQ_VILLES", allocationSize = 1)

    @Column(name="numero")
    private Integer id;
    @Column (name="code_postal")
    private String zipCode;
    @Column (name="nom_ville")
    private String cityName;

    //Quand on pointe sur une classe embadded, il faut aller de nouveau dans l'attribut en question
    @OneToMany (mappedBy = "address.city")
    private Set<Restaurant> restaurants;

    public City() {
        // Pour JPA On est obliger d'avoir un constructeur par défaut
        //this(null, null);
    }

    public City(String zipCode, String cityName) {
        this(null, zipCode, cityName);
    }
    
    public City(Integer id, String zipCode, String cityName) {
        this.id = id;
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.restaurants = new HashSet();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String city) {
        this.cityName = city;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public boolean equals(Object o) {
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        // Dans notre cas, on considère que notre identifiant métier/naturel est le code postal
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return Objects.equals(getZipCode(), city.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZipCode());
    }
}