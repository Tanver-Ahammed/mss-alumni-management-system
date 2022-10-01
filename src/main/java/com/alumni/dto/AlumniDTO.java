package com.alumni.dto;

import com.alumni.entities.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumniDTO {

    private Long id;

    private String name;

    private String email;

    private String contact;

    private String session;

    private String designation;

    private String workPlace;

    private String facebookLink;

    private String linkedLink;

    private String githubLink;

    private String image;

    private boolean isEnable;

    private String verificationCode;

    private String role;

    private String password;

    private List<BlogDTO> blogDTOS = new ArrayList<>();


}
