package com.fundamentos.springboot.fundamentos.services;

import com.fundamentos.springboot.fundamentos.dao.IUsuarioDao;
import com.fundamentos.springboot.fundamentos.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{
    
    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private IUsuarioDao usuarioDao;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioDao.findByUsername(username);
        
        if (usuario == null) {
            logger.error("Error en el login: no existe el usuario '".concat(username).concat("' en el sistema!"));
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '".concat(username).concat("' en el sistema!"));
        }
               
        
        List<GrantedAuthority> authorities = usuario.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getNombre()))
            .peek(authority -> logger.info("Role: ".concat(authority.getAuthority())))
            .collect(Collectors.toList());
        
        return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
    
}
