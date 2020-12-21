package ch.hearc.ig.guideresto.business;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMMENTAIRES")
public class CompleteEvaluation extends Evaluation {

    @Column(name = "commentaire")
    private String comment;
    @Column(name = "non_utilisateur")
    private String username;

    @OneToMany(mappedBy = "evaluation")
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "evaluation", cascade = CascadeType.ALL)
    //CASCADE: ce que je fait la va aller directement sur les grades
    private Set<Grade> grades;

    public CompleteEvaluation() {
        this(null, null, null, null);
    }

    public CompleteEvaluation(Date visitDate, Restaurant restaurant, String comment, String username) {
        this(null, visitDate, restaurant, comment, username);
    }
    
    public CompleteEvaluation(Integer id, Date visitDate, Restaurant restaurant, String comment, String username) {
        super(id, visitDate, restaurant);
        this.comment = comment;
        this.username = username;
        this.grades = new HashSet();
    }

    //Pour la JPA bidirectionnelle
    public void addGrade(Grade grade){
        grade.setEvaluation(this);
        this.getGrades().add(grade);

    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }
}