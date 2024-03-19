package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Skills")
public class Skill implements Serializable {
    @Id
    @Column(name = "operator_name")
    String nombre;
    @Column(name = "name")
    String nombreS;
    @Column(name = "charge")
    String charge;
    @Column(name = "duration")
    String duration;
    @Column(name = "cost")
    int cost;
    @Column(name = "initial")
    int initial;
    @Column(name = "auto")
    boolean auto;
    public Skill() {
    }
    public Skill(String nombre, String nombreS, String charge, String duration, int cost, int initial, boolean auto) {
        this.nombre = nombre;
        this.nombreS = nombreS;
        this.charge = charge;
        this.duration = duration;
        this.cost = cost;
        this.initial = initial;
        this.auto = auto;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreS() {
        return nombreS;
    }

    public void setNombreS(String nombreS) {
        this.nombreS = nombreS;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }
}
