package com.balasamajam.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Properties
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private double maranavari;

    private double masavari;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMaranavari() {
        return maranavari;
    }

    public void setMaranavari(double maranavari) {
        this.maranavari = maranavari;
    }

    public double getMasavari() {
        return masavari;
    }

    public void setMasavari(double masavari) {
        this.masavari = masavari;
    }
}
