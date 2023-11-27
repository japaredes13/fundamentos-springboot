package com.fundamentos.springboot.fundamentos.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "No puede estar vacío")
    @Size(min=4, max=12, message = "El tamaño tiene que estar entre 4 y 12")
    @Column(nullable=false)
    private String name;
    
    @NotEmpty(message = "No puede estar vacío")
    private String last_name;
    
    @NotEmpty(message = "No puede estar vacío")
    @Email(message = "No es una dirección de correo válido")
    @Column(nullable=false, unique=false)
    private String email;
    
    @NotNull(message = "No puede estar vacío")
    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private String photo;
    
    @NotNull(message = "La región no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
    
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    private static final long serialVersionUID = 864214680062510077L;
}
