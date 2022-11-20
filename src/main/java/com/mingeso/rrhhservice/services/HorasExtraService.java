package com.mingeso.rrhhservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class HorasExtraService {

    @Autowired
    RestTemplate restTemplate;

    public String[] getDatesByRut(int rut){
        String uri = "http://horasExtra-service/hora-extra/obtener/" + rut;
        ResponseEntity<String[]> response = restTemplate.getForEntity(uri, String[].class);
        return response.getBody();

    }




}
