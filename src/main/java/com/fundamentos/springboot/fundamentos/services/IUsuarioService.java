package com.fundamentos.springboot.fundamentos.services;

import com.fundamentos.springboot.fundamentos.entity.Usuario;



public interface IUsuarioService {
    
    public Usuario findByUsername(String username);
    
}
