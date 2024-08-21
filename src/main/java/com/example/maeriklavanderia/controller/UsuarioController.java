package com.example.maeriklavanderia.controller;


import com.example.maeriklavanderia.models.LoginRequest;
import com.example.maeriklavanderia.models.Usuario;
import com.example.maeriklavanderia.repository.UsuarioDao;
import com.example.maeriklavanderia.service.BackupMssqlFirebase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//se hace la notacion de que sera un clase de tipo controler
//de igual forma se crea en automatico el bean de esta clase
//para posteriormente el contenedor pueda gestionarla
@RestController
public class UsuarioController {

    @Autowired
    private BackupMssqlFirebase backupDataToFirestore;


    @Autowired
    private UsuarioDao usuarioDao;



    @GetMapping("/c")
    public String c() {
        return "Felicidades conecto al router";
    }



       @GetMapping(value = "GeneraToken")
    public String GeneraToken() {
        return "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "<SOAP-ENV:Header/>\n" +
                "<SOAP-ENV:Body>\n" +
                "<ns3:loginResponse xmlns:ns3=\"http://proxy.standard.services.sesame.bappa.com\">\n" +
                "<loginReturn>9456d1c72b86667ac8150256d936dea7d4051a9988</loginReturn>\n" +
                "</ns3:loginResponse>\n" +
                "</SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>";
    }


    @PostMapping("procesar-json")
    public void procesarJson(@RequestBody Map<String, Object> jsonData) {
        System.out.println("Valor del JSON= " + jsonData);
    }
    @GetMapping(value = "Proceso")
    public String Proceso() {
           String tru="{\n" +
                   "\n" +
                   "    \"warningMessages\": [],\n" +
                   "\n" +
                   "    \"exceptions\": [],\n" +
                   "\n" +
                   "    \"succeed\": true,\n" +
                   "\n" +
                   "    \"migratedProduct\": false,\n" +
                   "\n" +
                   "    \"policyNumber\": \"05.0201084545004246\",\n" +
                   "\n" +
                   "    \"policyId\": 153788496645,\n" +
                   "\n" +
                   "    \"operationPk\": 209100459448,\n" +
                   "\n" +
                   "    \"premiumValue\": 708.000000000000000000000000000000000000000000000000\n" +
                   "\n" +
                   "}";

        String fals = "{\n" +
                "    \"warningMessages\": [],\n" +
                "    \"exceptions\": [\n" +
                "        {\n" +
                "            \"errorCode\": [\n" +
                "                \"PIMS_ERROR_CODE\",\n" +
                "                \"POLICY_EXISTS\"\n" +
                "            ],\n" +
                "            \"unExpectedError\": true\n" +
                "        }\n" +
                "    ],\n" +
                "    \"succeed\": false,\n" +
                "    \"migratedProduct\": false,\n" +
                "    \"policyId\": 0\n" +
                "}";
        return fals;
    }

    @GetMapping(value = "poli")
    public String poli() {
        String tru="{\n" +
                "\n" +
                "    \"warningMessages\": [],\n" +
                "\n" +
                "    \"exceptions\": [],\n" +
                "\n" +
                "    \"succeed\": true,\n" +
                "\n" +
                "    \"migratedProduct\": false,\n" +
                "\n" +
                "    \"policyNumber\": \"05.0201084545004245\",\n" +
                "\n" +
                "    \"policyId\": 153788496645,\n" +
                "\n" +
                "    \"operationPk\": 209100459448,\n" +
                "\n" +
                "    \"premiumValue\": 708.000000000000000000000000000000000000000000000000\n" +
                "\n" +
                "}";


        return tru;
    }









    @GetMapping(value = "json")
    public Usuario json() {
        Usuario usuario = new Usuario();
        usuario.setRfid("001");
        usuario.setNombre("Dani");
        usuario.setCorreo("Fuentes");
        usuario.setContrasena("mil@gmail.com");
        usuario.setTelefono("72727262");
        usuario.setSaldo(1234);
        usuario.setFecha("15/09/1997");
        return usuario;
    }


    @GetMapping(value = "usuario/{id}")
    public Usuario getusuario(@PathVariable Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setRfid("Dani");
        usuario.setNombre("Dani");
        usuario.setCorreo("Fuentes");
        usuario.setContrasena("mil@gmail.com");
        usuario.setTelefono("72727262");
        usuario.setSaldo(1234);
        usuario.setFecha("15/09/1997");
        return usuario;
    }

    @GetMapping(value = "listausuario")
    public List<Usuario> getListusuario() {

        List<Usuario> listausuarios = new ArrayList<>();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRfid("Dani");
        usuario.setNombre("Dani");
        usuario.setCorreo("Fuentes");
        usuario.setContrasena("mil@gmail.com");
        usuario.setTelefono("72727262");
        usuario.setSaldo(1234);
        usuario.setFecha("15/09/1997");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario.setRfid("Dani");
        usuario.setNombre("Dani");
        usuario.setCorreo("Fuentes");
        usuario.setContrasena("mil@gmail.com");
        usuario.setTelefono("72727262");
        usuario.setSaldo(1234);
        usuario.setFecha("15/09/1997");

        Usuario usuario3 = new Usuario();
        usuario3.setId(3L);
        usuario.setRfid("Dani");
        usuario.setNombre("Dani");
        usuario.setCorreo("Fuentes");
        usuario.setContrasena("mil@gmail.com");
        usuario.setTelefono("72727262");
        usuario.setSaldo(1234);
        usuario.setFecha("15/09/1997");

        Usuario usuario4 = new Usuario();
        usuario4.setId(3L);
        usuario.setRfid("Dani");
        usuario.setNombre("Dani");
        usuario.setCorreo("Fuentes");
        usuario.setContrasena("mil@gmail.com");
        usuario.setTelefono("72727262");
        usuario.setSaldo(1234);
        usuario.setFecha("15/09/1997");

        listausuarios.add(usuario);
        listausuarios.add(usuario2);
        listausuarios.add(usuario3);
        listausuarios.add(usuario4);
        return listausuarios;
    }


    @GetMapping(value = "api/usuario")
    public List<Usuario> getUsuarios() {

           return usuarioDao.getUsuarios();
    }

    @PostMapping(value = "api/usuario")
    public void registrarUsuarios(@RequestBody Usuario usuario) {
        usuarioDao.registrar(usuario);
    }

    @GetMapping(value = "api/usuario/{id}")
    public Usuario getUsuario(@PathVariable("id") long id) {
        return usuarioDao.obtenerPorId(id);
    }




    @GetMapping("/check_uid/{rfid}")
    public String checkUid(@PathVariable String rfid) {
        Usuario usuario = usuarioDao.obtenerPorRfId(rfid);

        if (usuario != null && usuario.getSaldo() >= 10) {
            return "{\"status\": \"registered\"}";
        } else {
            return "{\"status\": \"not registered\"}";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody LoginRequest loginRequest) {
        String correo = loginRequest.getEmail();
        String pass = loginRequest.getPassword();
        Usuario usuario = usuarioDao.validarLogin(correo,pass);

        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        System.out.println("encontro usuario "+usuario+"y correo "+correo);
        return ResponseEntity.ok(usuario);
    }





    @PatchMapping(value = "api/usuario/{id}")
    public void actualizarUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
        usuarioDao.actualizar(id, usuario);
    }


    @DeleteMapping(value = "api/usuario/{id}")
    public void deleteUsuario(@PathVariable("id") Long id) {
        usuarioDao.deleteUsuario(id);
    }





    @GetMapping(value = "/backup")
    public String backupData() {
        try {
            backupDataToFirestore.backupDataToFirestore();
            return "Backup completed successfully";
        } catch (Exception e) {
            return "Backup failed: " + e.getMessage();
        }
    }





}
