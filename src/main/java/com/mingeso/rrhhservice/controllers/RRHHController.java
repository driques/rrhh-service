package com.mingeso.rrhhservice.controllers;


import com.mingeso.rrhhservice.entities.PlanillaEntity;
import com.mingeso.rrhhservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/rrhh")
public class RRHHController {
    @Autowired
    RRHHService oficinaRRHHService;
    @Autowired
    PlanillaService planillaService;
    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    HorasExtraService horasExtraService;
    @Autowired
    JustificacionService justificacionService;


   @GetMapping("/planilla")
   public ResponseEntity<List<PlanillaEntity>> obtenerPlanilla(){
       List<PlanillaEntity> planillas = oficinaRRHHService.getPlanilla();
       return ResponseEntity.ok(planillas);
   }

//
//
//    @GetMapping("/delete-datafile")
//    public String deleteDataFile(){
//        oficinaRRHHService.removePath();
//        return "index";
//
//    }


}

