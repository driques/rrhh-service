package com.mingeso.rrhhservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RRHHRepository {
    int aniosEmpresa(int rut);
    int calculoBonificaciones(int rut);
    int calculoHorasExtra(int rut);
    int montoHoraExtra(int rut);
    int sueldoFijo(int rut);
    int calculoDescuentos(int rut);
    int calculoSueldoFinal(int rut);
    int bonificacion(int rut);
    int montoDescuento(int rut);
    int brutoSinCotizacion(int rut);
    int getCotizacionPrevisional(int rut);
    int getCotizacionSalud(int rut);

}
