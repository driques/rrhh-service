package com.mingeso.rrhhservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JustificacionService {

    @Autowired
    RestTemplate restTemplate;

    public String[] getDatesByRut(int rut){
        String uri = "http://justificacion-service/justificacion/" + rut;
        ResponseEntity<String[]> response = restTemplate.getForEntity(uri, String[].class);
        return response.getBody();

    }


}
