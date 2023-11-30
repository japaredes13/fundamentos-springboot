package com.fundamentos.springboot.fundamentos.auth;

import com.fundamentos.springboot.fundamentos.entity.Usuario;
import com.fundamentos.springboot.fundamentos.services.IUsuarioService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;


@Component
public class InfoAdicionalToken implements TokenEnhancer{
    
    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        
        Usuario usuario = usuarioService.findByUsername(authentication.getName());
        Map<String, Object> info = new HashMap<>();
        
        info.put("info_adicional", "Hola ".concat(authentication.getName().concat(", esto es una prueba")));        
        info.put("nombre", usuario.getNombre());
        info.put("apellido", usuario.getApellido());
        info.put("email", usuario.getEmail());

        
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        
        return accessToken;
    }
    
}
