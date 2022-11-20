package com.mingeso.rrhhservice.services;


import com.mingeso.rrhhservice.controllers.FileController;
import com.mingeso.rrhhservice.entities.EmpleadoEntity;
import com.mingeso.rrhhservice.entities.PlanillaEntity;
import com.mingeso.rrhhservice.entities.RegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RRHHService {
    @Autowired
    private PlanillaService planillaService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private FileService fileService;

    @Autowired
    private HorasExtraService horasExtraService;

    @Autowired
    private JustificacionService justificacionService;

    //Porcentajes de cotizaciones.
    int cotizacionSalud = 8;
    int cotizacionPrevisional = 10;

    public int aniosEmpresa(int rut){
        LocalDate empleadoIngreso = empleadoService.getAnioIngreso(rut);//<- debo llamar al empleado service para obtener el año de ingreso del empleado.
        LocalDate hoy = LocalDate.now();
        Period period = Period.between(empleadoIngreso,hoy);
        return period.getYears();

    }

    //Se obtiene el anio de ingreso, el anio actual y se retorna el porcentaje de bonificacion.
    public int calculoBonificaciones(int rut){
        LocalDate empleadoIngreso = empleadoService.getAnioIngreso(rut);
        LocalDate hoy = LocalDate.now();
        Period period = Period.between(empleadoIngreso,hoy);
        if(period.getYears()<5){
            return 0;
        } else if (period.getYears()>=5 && period.getYears()<10) {
            return 5;

        } else if (period.getYears()>=10 && period.getYears()<15) {
            return 8;

        } else if (period.getYears()>=15 && period.getYears()<20) {
            return 11;

        } else if (period.getYears()>=20 && period.getYears()<25) {
            return 14;

        } else if (period.getYears()>=25) {
            return 17;
        }
        else{
            return -1;
        }
    }



    boolean myContains(String[] array, String string){
        for(String i:array){
            if(i.equals(string)){
                return true;
            }
        }
        return false;
    }
    public String formatToRut(String toFormat){
        //Recibo string de la forma 11.234.123-6
        //Debo retornar 112341236
        String[] arrayFormat = toFormat.split("\\.",-1);
        //Verificamos que sea un rut valido.
        if(arrayFormat.length>1){
            String strRut = toFormat;
            String[] getRut = strRut.split("\\.",-1);
            String concatRut = getRut[0]+getRut[1]+getRut[2];
            String[] getRutSV = concatRut.split("-",-1);
            String finalRut = getRutSV[0]+getRutSV[1];
            return finalRut;
        }
        return "0"; //<-- Rut invalido.
    }



    public int calculoHorasExtra(int rut){
        EmpleadoEntity empleado = empleadoService.getEmpleado(rut);
        int horasExtra = 0;
        int horaSalida = 18;

        List<RegisterEntity> registers = fileService.getRegisters();

        if(registers == null) return -1;
        for(RegisterEntity a: registers){
            if(a!=null){
                if(Integer.parseInt(formatToRut(a.getRut()))==rut){
                    String date =a.getDate().toString();
                    String[] datesByRut = horasExtraService.getDatesByRut(rut); //<- debo llamar al servicio de horas extra para obtener las fechas de los registros del empleado.
                    String hour = a.getTime();
                    String[] arrayFormat = hour.split(":",-1);
                    int totalHour = Integer.parseInt(arrayFormat[0]);
                    if(totalHour>horaSalida && myContains(datesByRut,date)){
                        horasExtra = horasExtra + (totalHour-horaSalida);
                    }
                }
            }
        }
        //Se hace el calculo de las horas extra.
        String categoriaEmpleado = empleado.getCategoria();
        char charCategoria = categoriaEmpleado.charAt(0);


        return montoHoraExtra(charCategoria,horasExtra);

    }

    public int montoHoraExtra(char charCategoria, int horasExtra){
        if(charCategoria=='a'){
            int montoHorasExtra = 25000;
            return montoHorasExtra*horasExtra;
        }
        else if(charCategoria=='b'){
            int montoHorasExtra = 20000;
            return montoHorasExtra*horasExtra;
        } else if (charCategoria=='c') {
            int montoHorasExtra = 10000;
            return montoHorasExtra*horasExtra;
        }
        //En caso de que no exista la categoria, se retorna 0.
        return  0;
    }
    public int sueldoFijo(char charCategoria){
        if(charCategoria=='a'){
            int sueldo = 1700000;
            return sueldo;
        }
        else if(charCategoria=='b'){
            int sueldo = 1200000;
            return sueldo;
        } else if (charCategoria=='c') {
            int sueldo = 800000;
            return  sueldo;
        }
        //En caso de que no exista la categoria, se retorna 0.
        return  0;


    }

    public int calculoDescuentos(int rut){

        List<RegisterEntity> registers = fileService.getRegisters();
        int porcentajeDescuento = 0;
        for(RegisterEntity a: registers){
            if(a!=null){
                if(Integer.parseInt(formatToRut(a.getRut()))==rut){
                    String date = a.getDate().toString();
                    String[] datesByRut = justificacionService.getDatesByRut(rut);
                    String hour = a.getTime();
                    String[] arrayFormat = hour.split(":",-1);
                    int hora = Integer.valueOf(arrayFormat[0]);
                    int minutos = Integer.valueOf(arrayFormat[1]);
                    if((hora>=8 && minutos>10) || (hora==9 && minutos<=10)){
                        if (hora==8 && minutos>10 && minutos<25) porcentajeDescuento = porcentajeDescuento + 1;
                        if (hora==8 && minutos>=25 && minutos<45) porcentajeDescuento = porcentajeDescuento + 3;
                        if ((hora==8 && minutos>=45 && minutos<60)||(hora==9 && minutos<=10)) porcentajeDescuento = porcentajeDescuento + 6;
                    } else if (hora>=9 && minutos>10){
                        if(myContains(datesByRut,date)) porcentajeDescuento = porcentajeDescuento;
                        else porcentajeDescuento=porcentajeDescuento+15;
                    }
                }
            }
        }
        return porcentajeDescuento;
    }



    public int calculoSueldoFinal(int rut){
        EmpleadoEntity empleado = empleadoService.getEmpleado(rut);
        String categoria = empleado.getCategoria();
        int sueldo = sueldoFijo(categoria.charAt(0));
        int porcentajeBonificacion = (calculoBonificaciones(rut)*sueldo)/100;
        int montoHoraExtra = calculoHorasExtra(rut);
        int sueldoBruto = sueldo+porcentajeBonificacion+montoHoraExtra;
        sueldoBruto = sueldoBruto - montoDescuento(rut,sueldoBruto);
        int sueldoLiquido = sueldoBruto - (getCotizacionPrevisional(sueldoBruto)+getCotizacionSalud(sueldoBruto));
        return sueldoLiquido;
    }

    public int bonificacion(int rut, int sueldo){

        return (calculoBonificaciones(rut)*sueldo)/100;
    }

    public int montoDescuento(int rut ,int sueldoBruto){
        int porcentajeDescuentos = calculoDescuentos(rut);
        int descuento = ((sueldoBruto * porcentajeDescuentos)/100);
        return descuento;

    }

    public int brutoSinCotizacion(int sueldoBruto, int montoDescuento){
        return sueldoBruto-montoDescuento;
    }

    public int getCotizacionPrevisional(int sueldoBruto){
        return (sueldoBruto*cotizacionPrevisional)/100;
    }
    public int getCotizacionSalud(int sueldoBruto){
        return (sueldoBruto * cotizacionSalud)/100;
    }

    public void creaPlanilla(){
        planillaService.dropTable();
        List<EmpleadoEntity> empleados=empleadoService.getEmpleados();
        for(EmpleadoEntity a : empleados){
            PlanillaEntity planilla = new PlanillaEntity();
            planilla.setRut(a.getRut());
            planilla.setAnios_servicio(aniosEmpresa(a.getRut()));
            planilla.setNombre_empleado(a.getNombres()+" "+a.getApellidos());
            planilla.setSueldo_fijo(sueldoFijo(a.getCategoria().charAt(0)));
            planilla.setBonificacion(bonificacion(a.getRut(),sueldoFijo(a.getCategoria().charAt(0))));
            planilla.setHoras_extra_monto(calculoHorasExtra(a.getRut()));
            int sueldoBruto = sueldoFijo(a.getCategoria().charAt(0))+bonificacion(a.getRut(),sueldoFijo(a.getCategoria().charAt(0)))+calculoHorasExtra(a.getRut());

            planilla.setMonto_descuento(montoDescuento(a.getRut(),sueldoBruto));

            planilla.setBruto(brutoSinCotizacion(sueldoBruto,montoDescuento(a.getRut(),sueldoBruto)));

            planilla.setCotizacion_previsional(getCotizacionPrevisional(brutoSinCotizacion(sueldoBruto,montoDescuento(a.getRut(),sueldoBruto))));

            planilla.setCotizacion_salud(getCotizacionSalud(brutoSinCotizacion(sueldoBruto,montoDescuento(a.getRut(),sueldoBruto))));
            planilla.setSueldo_final(calculoSueldoFinal(a.getRut())-(montoDescuento(a.getRut(),sueldoBruto))+calculoHorasExtra(a.getRut()));
            planillaService.guardarPlanilla(planilla);
        }
    }



    public List<PlanillaEntity> getPlanilla(){
        creaPlanilla();
        return planillaService.obtenerPlanilla();
    }










}
