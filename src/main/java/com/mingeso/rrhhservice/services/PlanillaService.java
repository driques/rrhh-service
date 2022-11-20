package com.mingeso.rrhhservice.services;


import com.mingeso.rrhhservice.entities.PlanillaEntity;
import com.mingeso.rrhhservice.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanillaService {
    @Autowired
    PlanillaRepository planillaRepository;

    public ArrayList<PlanillaEntity> obtenerEmpleados(){
        return (ArrayList<PlanillaEntity>) planillaRepository.findAll();
    }
    @Transactional
    public void dropTable(){
        planillaRepository.dropTable();
    }

    public PlanillaEntity guardarPlanilla(PlanillaEntity planilla){
        return planillaRepository.save(planilla);
    }

    public List<PlanillaEntity> obtenerPlanilla(){
        return (List<PlanillaEntity>) planillaRepository.findAll();
    }
}
