package ru.job4j.lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn (name = "mark_id")
    private Mark carMark;

    public Model() {
    }

    public Model(String name, Mark carMark) {
        this.name = name;
        this.carMark = carMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model carModel = (Model) o;
        return id == carModel.id && Objects.equals(name, carModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Model{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", carMark=").append(carMark);
        sb.append('}');
        return sb.toString();
    }
}
