package ch.hearc.ig.guideresto.business;

import java.util.Objects;
import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMM")
    @SequenceGenerator(name="SEQ_COMM", sequenceName = "SEQ_COMMENTAIRES", allocationSize = 1)

    @Column (name = "numero")
    private Integer id;
    @Column (name = "date_eval")
    private Date visitDate;

    //toOne : on ne peut pas faire du mapped car la FK pointe toujours d'un seul coté
    @ManyToOne
    @JoinColumn(name = "fk_rest")
    private Restaurant restaurant;

    public Evaluation() {
        this(null, null, null);
    }

    public Evaluation(Integer id, Date visitDate, Restaurant restaurant) {
        this.id = id;
        this.visitDate = visitDate;
        this.restaurant = restaurant;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    //Iic on a une exception car on n'a pas la clé métier donc on prend l'identité métier
    @Override
    public boolean equals(Object o) {
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        // Nous n'avons pas d'identité métier, on utilise l'identité technique pour définir l'égalité
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evaluation evaluation = (Evaluation) o;
        if(id == null || evaluation.id == null) {
            return false;
        }
        return Objects.equals(id, evaluation.id);
    }

    @Override
    public int hashCode() {
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        // Dans n'avons pas d'identifiant naturel, on retourne une valeur fixe
        return getClass().hashCode();
    }
}