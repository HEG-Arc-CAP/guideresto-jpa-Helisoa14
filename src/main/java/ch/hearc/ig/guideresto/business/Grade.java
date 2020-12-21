package ch.hearc.ig.guideresto.business;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "NOTES")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTE")
    @SequenceGenerator(name="SEQ_NOTE", sequenceName = "SEQ_NOTES", allocationSize = 1)

    @Column (name = "numero")
    private Integer id;
    @Column (name = "note")
    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "fk_comm")
    private CompleteEvaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "fk_crit")
    private EvaluationCriteria criteria;

    public Grade() {
        this(null, null, null);
    }

    public Grade(Integer grade, CompleteEvaluation evaluation, EvaluationCriteria criteria) {
        this(null, grade, evaluation, criteria);
    }

    public Grade(Integer id, Integer grade, CompleteEvaluation evaluation, EvaluationCriteria criteria) {
        this.id = id;
        this.grade = grade;
        this.evaluation = evaluation;
        this.criteria = criteria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public CompleteEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(CompleteEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    public EvaluationCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(EvaluationCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public boolean equals(Object o) {
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        // La clé métier correspond à l'évaluation et au critère d'évaluation
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grade grade1 = (Grade) o;
        return Objects.equals(getGrade(), grade1.getGrade()) &&
            Objects.equals(getEvaluation(), grade1.getEvaluation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGrade(), getEvaluation());
    }
}