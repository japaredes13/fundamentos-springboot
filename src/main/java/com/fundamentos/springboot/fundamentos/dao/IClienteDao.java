package com.fundamentos.springboot.fundamentos.dao;

import com.fundamentos.springboot.fundamentos.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
