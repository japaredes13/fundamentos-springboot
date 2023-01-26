package com.fundamentos.springboot.fundamentos.controllers;

import com.fundamentos.springboot.fundamentos.entity.Cliente;
import com.fundamentos.springboot.fundamentos.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.annotations.common.util.impl.Log_$logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        
        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (cliente == null) {
            response.put("message", "El cliente con ID: ".concat(id.toString().concat(" no existe.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);            
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        try {
            clienteNew = clienteService.save(cliente);
    
        } catch (DataAccessException e) {
            response.put("message", "Error al insertar los datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
        response.put("message", "El cliente ha sido creado con éxito!");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteFind = clienteService.findById(id);
        Cliente clienteUpdated = null;
        
        Map<String, Object> response = new HashMap<>();
        if (clienteFind == null) {
            response.put("message", "Error: no se pudo editar. El cliente con ID: ".concat(id.toString().concat(" no existe.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);            
        }
        
        try {

            clienteFind.setName(cliente.getName());
            clienteFind.setLast_name(cliente.getLast_name());
            clienteFind.setEmail(cliente.getEmail());
        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar el cliente!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
        
        clienteUpdated = clienteService.save(clienteFind);
        response.put("message", "El cliente ha actualizado con éxito!");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el cliente!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
        response.put("mensaje", "El cliente se ha eliminado con éxito!");
        
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
}
