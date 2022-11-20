package com.mingeso.rrhhservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public int rut;
    public String apellidos;
    public String nombres;
    public String fecha_nacimiento;
    public String id_categoria;
    public LocalDate ingreso_empresa;

    public String categoria;

    public String getCategoria(){
        return this.id_categoria;
    }


}
