package com.fundamentos.springboot.fundamentos.services;

import com.fundamentos.springboot.fundamentos.entity.Cliente;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Cliente findById (Long id);

    public Cliente save(Cliente cliente);

    public void delete (Long id);
}
