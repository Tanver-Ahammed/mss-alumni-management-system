package com.alumni.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private Long id;

    private String title;

    private String date;

    @Column(length = 5000)
    private String description;

    private AlumniDTO alumni;

}
