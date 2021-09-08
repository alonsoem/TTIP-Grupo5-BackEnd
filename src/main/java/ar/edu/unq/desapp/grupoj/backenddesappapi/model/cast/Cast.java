package ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;


@Entity
public class Cast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;


    @Enumerated(EnumType.STRING)
    private Job job;

    protected Cast(){}

    public Cast(Person person,Job job){
        this.person=person;
        this.job=job;
    }

    public Person getPerson() {
        return person;
    }

    public Integer getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }
}
