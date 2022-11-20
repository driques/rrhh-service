package com.mingeso.rrhhservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mingeso.rrhhservice.entities.EmpleadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {
    @Autowired
    RestTemplate restTemplate;

    public List<EmpleadoEntity> getEmpleados() {

        String uri = "http://empleado-service/empleados/listar";

        ResponseEntity<Object[]> response = restTemplate.getForEntity(uri, Object[].class); // Se usa lista de Object para mapear la repuesta JSON
        Object[] records = response.getBody(); // Obtener lista de empleados desde microservicio empleados
        if (records == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper(); // Mapper desde object a modelo Empleado
        return Arrays.stream(records)
                .map(employee -> mapper.registerModule(new JavaTimeModule())
                                .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // Deshabilitar serializacion de fechas
                        .convertValue(employee, EmpleadoEntity.class))
                .collect(Collectors.toList());
    }


    public LocalDate getAnioIngreso(int rut) {
        String uri = "http://empleado-service/empleados/anio-ingreso/" + rut;
        ResponseEntity<LocalDate> response = restTemplate.getForEntity(uri, LocalDate.class);

        return response.getBody();
        //Cast de String a LocalDate
    }
    public EmpleadoEntity getEmpleado(int rut) {
        String uri = "http://empleado-service/empleados/" + rut;
        ResponseEntity<EmpleadoEntity> response = restTemplate.getForEntity(uri, EmpleadoEntity.class);
        return response.getBody();
    }


}


