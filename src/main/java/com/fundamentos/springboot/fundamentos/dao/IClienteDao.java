package com.fundamentos.springboot.fundamentos.dao;

import com.fundamentos.springboot.fundamentos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

}
