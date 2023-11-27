package com.fundamentos.springboot.fundamentos.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="regiones")
public class Region implements Serializable{
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
