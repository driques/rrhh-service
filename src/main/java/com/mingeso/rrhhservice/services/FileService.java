package com.mingeso.rrhhservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mingeso.rrhhservice.entities.EmpleadoEntity;
import com.mingeso.rrhhservice.entities.RegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    RestTemplate restTemplate;

    public List<RegisterEntity> getRegisters(){

        String uri = "http://upload-service/upload/registers";

        ResponseEntity<Object[]> response = restTemplate.getForEntity(uri, Object[].class); // Se usa lista de Object para mapear la repuesta JSON

        Object[] records = response.getBody(); // Obtener lista de registros
        if (records == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper(); // Mapper desde object a modelo register
        return Arrays.stream(records)
                .map(register ->mapper.registerModule(new JavaTimeModule())
                                .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // Deshabilitar serializacion de fechas
                                .convertValue(register, RegisterEntity.class))
                .collect(Collectors.toList());
    }


}
