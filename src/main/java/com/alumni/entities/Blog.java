package com.alumni.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String date;

    @Column(length = 5000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "alumni_id_fk", referencedColumnName = "id")
    private Alumni alumni;

}
