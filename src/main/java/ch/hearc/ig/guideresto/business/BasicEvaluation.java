package ch.hearc.ig.guideresto.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "LIKES")
public class BasicEvaluation extends Evaluation {

    @Column(name = "appreciation")
    private boolean likeRestaurant;
    @Column(name = "adresse_ip")
    private String ipAddress;
    @Transient
    private String hello;

    public BasicEvaluation() {
        this(null, null, false, null);
    }

    public BasicEvaluation(Date visitDate, Restaurant restaurant, boolean likeRestaurant, String ipAddress) {
        this(null, visitDate, restaurant, likeRestaurant, ipAddress);
    }
    
    public BasicEvaluation(Integer id, Date visitDate, Restaurant restaurant, boolean likeRestaurant, String ipAddress) {
        super(id, visitDate, restaurant);
        this.likeRestaurant = likeRestaurant;
        this.ipAddress = ipAddress;
    }

    public boolean isLikeRestaurant() {
        return likeRestaurant;
    }

    public void setLikeRestaurant(boolean likeRestaurant) {
        this.likeRestaurant = likeRestaurant;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHello() {
        return hello;
    }
}
