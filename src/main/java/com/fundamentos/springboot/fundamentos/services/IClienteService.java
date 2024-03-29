package com.fundamentos.springboot.fundamentos.services;

import com.fundamentos.springboot.fundamentos.entity.Cliente;
import com.fundamentos.springboot.fundamentos.entity.Region;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

    public List<Cliente> findAll();    
    
    public Page<Cliente> findAll(Pageable pageable);

    public Cliente findById (Long id);

    public Cliente save(Cliente cliente);

    public void delete (Long id);
    
    public List<Region> findAllRegiones();
}
