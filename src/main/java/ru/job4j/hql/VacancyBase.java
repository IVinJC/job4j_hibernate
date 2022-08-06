package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "vacancybase")
public class VacancyBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameBase;
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public VacancyBase() {
    }

    public VacancyBase(String nameBase) {
        this.nameBase = nameBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBase() {
        return nameBase;
    }

    public void setNameBase(String nameBase) {
        this.nameBase = nameBase;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacancyBase that = (VacancyBase) o;
        return id == that.id && Objects.equals(nameBase, that.nameBase) && Objects.equals(vacancies, that.vacancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameBase, vacancies);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VacancyBase{");
        sb.append("id=").append(id);
        sb.append(", nameBase='").append(nameBase).append('\'');
        sb.append(", vacancies=").append(vacancies);
        sb.append('}');
        return sb.toString();
    }
}
