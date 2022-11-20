package com.mingeso.rrhhservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "register")
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private LocalDate date;
    private String time;
    private String rut;

    public RegisterEntity(LocalDate date, String time, String rut) {
        this.date = date;
        this.time = time;
        this.rut = rut;
    }
}
