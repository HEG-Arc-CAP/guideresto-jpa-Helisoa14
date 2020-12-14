package ch.hearc.ig.guideresto.business;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "TYPE_GASTRONOMIQUES")
@NamedNativeQuery(
    name="RestaurantsTypeList",
    query ="SELECT * FROM TYPE_GASTRONOMIQUES"
)
public class RestaurantType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TYPE")
    @SequenceGenerator(name="SEQ_TYPE", sequenceName = "SEQ_TYPES_GASTRONOMIQUES", allocationSize = 1)

    @Column (name = "numero")
    private Integer id;
    @Column (name = "libelle")
    private String label;
    @Column (name = "description")
    private String description;

    //Car on a deja un mapping du coté restaurant
    @OneToMany (mappedBy = "type")
    private Set<Restaurant> restaurants;

    public RestaurantType() {
        this(null, null);
    }

    public RestaurantType(String label, String description) {
        this(null, label, description);
    }

    public RestaurantType(Integer id, String label, String description) {
        this.restaurants = new HashSet();
        this.id = id;
        this.label = label;
        this.description = description;
    }

    //Pour la JPA bidirectionnelle
    public void addRestaurant(Restaurant restaurant){
        restaurant.setType(this);
        this.getRestaurants().add(restaurant);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}