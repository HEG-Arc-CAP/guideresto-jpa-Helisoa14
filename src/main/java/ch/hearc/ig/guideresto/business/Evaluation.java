package ch.hearc.ig.guideresto.business;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "COMMENTAIRES")
public abstract class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMM")
    @SequenceGenerator(name="SEQ_COMM", sequenceName = "SEQ_COMMENTAIRES", allocationSize = 1)

    @Column (name = "numero")
    private Integer id;
    @Column (name = "date_eval")
    private Date visitDate;

    @Transient
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
}