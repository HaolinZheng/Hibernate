package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Classs")
public class Classs implements Serializable {
    @Id
    @Column(name = "class_Id")
    int class_Id;
    @Column(name = "primario")
    String primario;
    @Column(name = "secundario")
    String secundario;
    public Classs() {
    }
    public Classs(int class_Id, String primario, String secundario) {
        this.class_Id = class_Id;
        this.primario = primario;
        this.secundario = secundario;
    }

    public int getClass_Id() {
        return class_Id;
    }

    public void setClass_Id(int class_Id) {
        this.class_Id = class_Id;
    }

    public String getPrimary() {
        return primario;
    }

    public void setPrimary(String primary) {
        this.primario = primary;
    }

    public String getSecondary() {
        return secundario;
    }

    public void setSecondary(String secondary) {
        this.secundario = secondary;
    }
}
