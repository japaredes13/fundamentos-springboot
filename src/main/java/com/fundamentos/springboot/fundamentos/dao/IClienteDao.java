package com.fundamentos.springboot.fundamentos.dao;

import com.fundamentos.springboot.fundamentos.entity.Cliente;
import com.fundamentos.springboot.fundamentos.entity.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClienteDao extends JpaRepository<Cliente, Long> {
    
    //se trabaja con Objetos
    @Query("from Region")
    public List<Region> findAllRegiones();
}
