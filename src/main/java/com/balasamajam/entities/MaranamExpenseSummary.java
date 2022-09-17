package com.balasamajam.entities;

import com.balasamajam.constants.ExpenseType;

import javax.persistence.*;

@Entity
public class MaranamExpenseSummary
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maranam_id")
    private Maranam maranam;

    private ExpenseType expenseType;

    public Maranam getMaranam()
    {
        return maranam;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
