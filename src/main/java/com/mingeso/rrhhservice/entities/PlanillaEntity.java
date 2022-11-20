package com.mingeso.rrhhservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private int id;
    private int rut;
    private String nombre_empleado;
    private int anios_servicio;
    private int sueldo_fijo;
    private int bonificacion;
    private int horas_extra_monto;
    private int monto_descuento;
    private int bruto;
    private int cotizacion_previsional;
    private int cotizacion_salud;
    private int sueldo_final;
}
