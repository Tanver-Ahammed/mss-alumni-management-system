package com.alumni.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "alumni", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Blog> blogs = new ArrayList<>();

}
