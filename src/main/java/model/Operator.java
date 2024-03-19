package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Operator")
public class Operator implements Serializable {
    @Id
    @Column(name = "nombreO")
    String nombreO;
    @Column(name = "position_op")
    String position_op;
    @Column(name = "attack")
    String attack;
    @Column(name = "alter_op")
    boolean alter_op;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_Id")
    public Classs aClasss;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "operator_skill",
            joinColumns = @JoinColumn(name = "nombreO"),
            inverseJoinColumns = @JoinColumn(name = "nombre")
    )
    private List<Skill> skills = new ArrayList<Skill>();
    public Operator() {
    }
    public Operator(String nombreO, String position_op, String attack, boolean alter_op, Classs aClasss, List<Skill> skills) {
        this.nombreO = nombreO;
        this.position_op = position_op;
        this.attack = attack;
        this.alter_op = alter_op;
        this.aClasss = aClasss;
        this.skills = skills;
    }

    public String getNombreO() {
        return nombreO;
    }

    public void setNombreO(String nombreO) {
        this.nombreO = nombreO;
    }

    public String getPosition_op() {
        return position_op;
    }

    public void setPosition_op(String position_op) {
        this.position_op = position_op;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public boolean isAlter_op() {
        return alter_op;
    }

    public void setAlter_op(boolean alter_op) {
        this.alter_op = alter_op;
    }

    public Classs getaClass() {
        return aClasss;
    }

    public void setaClass(Classs aClasss) {
        this.aClasss = aClasss;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
