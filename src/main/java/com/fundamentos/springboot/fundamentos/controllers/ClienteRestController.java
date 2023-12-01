package com.fundamentos.springboot.fundamentos.controllers;

import com.fundamentos.springboot.fundamentos.entity.Cliente;
import com.fundamentos.springboot.fundamentos.entity.Region;
import com.fundamentos.springboot.fundamentos.services.IClienteService;
import com.fundamentos.springboot.fundamentos.services.IUploadFileService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;
    
    private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);
    
    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }
    
    
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return clienteService.findAll(pageable);
    }
    
    
    @Secured({"ROLE_ADMIN","ROLE_USER"})
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

    @Secured("ROLE_ADMIN")
    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            // para las versiones anteriores a JAVA 8
            /*List<String> errors = new ArrayList<>();
            
            for (FieldError error : result.getFieldErrors()) {
                errors.add("El campo '"+ error.getField() + "' " + error.getDefaultMessage());
            }*/
            
            List<String> errors = result.getFieldErrors()
                                    .stream()
                                    .map( error -> "El campo '"+ error.getField() + "' " + error.getDefaultMessage() )
                                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
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
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id){
        Cliente clienteFind = clienteService.findById(id);
        Cliente clienteUpdated = null;
        
        Map<String, Object> response = new HashMap<>();
        if (clienteFind == null) {
            response.put("message", "Error: no se pudo editar. El cliente con ID: ".concat(id.toString().concat(" no existe.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);            
        }
        
        if(result.hasErrors()){
        
            List<String> errors = result.getFieldErrors()
                                    .stream()
                                    .map( error -> "El campo '"+ error.getField() + "' " + error.getDefaultMessage() )
                                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {

            clienteFind.setName(cliente.getName());
            clienteFind.setLast_name(cliente.getLast_name());
            clienteFind.setEmail(cliente.getEmail());
            clienteFind.setCreatedAt(cliente.getCreatedAt());
            clienteFind.setRegion(cliente.getRegion());
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
    
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            
            Cliente cliente = clienteService.findById(id);
            
            String lastNamePhoto = cliente.getPhoto();
            uploadFileService.delete(lastNamePhoto);
            
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el cliente!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
        response.put("message", "El cliente se ha eliminado con éxito!");
        
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
    
        
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping("/clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, 
            @RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        
        Cliente cliente = clienteService.findById(id);
        
        if (!file.isEmpty()) {
            String fileName = null;
            try {
                fileName = uploadFileService.copy(file);
            } catch (IOException ex) {
                response.put("message", "Error al subir la imagen.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }
            
            String lastNamePhoto = cliente.getPhoto();
            uploadFileService.delete(lastNamePhoto);

            log.info("Se esta por cargar la imagen: " + fileName);
            cliente.setPhoto(fileName);
            clienteService.save(cliente);
            
            response.put("cliente", cliente);
            response.put("message", "Has subido correctamente la imagen: " + fileName);

        }
        
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
    
    
    @GetMapping("/uploads/img/{namePhoto:.+}")
    public ResponseEntity<Resource> seePhoto(@PathVariable String namePhoto){
        
        Resource resource = null;
        
        try {
            resource = uploadFileService.load(namePhoto);
        } catch (MalformedURLException e) {
            log.error("Error no se pudo cargar la imagen: "+ namePhoto);
        }
        
        
        log.info("Se esta por descargar la imagen: " + namePhoto);
        
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() +"\"");
                       
        return new ResponseEntity<Resource>((Resource) resource, header, HttpStatus.OK);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/clientes/regiones")
    public List<Region> listarRegiones(){
        return clienteService.findAllRegiones();
    }
}
