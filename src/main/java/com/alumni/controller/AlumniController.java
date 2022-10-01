package com.alumni.controller;

import com.alumni.dto.AlumniDTO;
import com.alumni.services.impl.AlumniServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(path = "/alumni")
public class AlumniController {

    @Autowired
    private AlumniServiceImpl alumniService;

    @GetMapping(path = "/registration")
    public String registrationAlumni(Model model, Principal principal) {

        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else
            model.addAttribute("name", null);

        model.addAttribute("alumniDTO", new AlumniDTO());
        model.addAttribute("message", "");
        return "registration-alumni";
    }

    @PostMapping(path = "/save")
    public String saveAlumni(@Valid @ModelAttribute("alumniDTO") AlumniDTO alumniDTO, BindingResult result,
                             @RequestParam("alumniImage") MultipartFile alumniImage,
                             Model model, Principal principal) throws IOException {

        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
            return "registration-alumni";
        } else
            model.addAttribute("name", null);

        // unique identity email and contact
        Boolean isDuplicateEmailOrContact = this.alumniService
                .isDuplicateAlumniByEmailOrContact(alumniDTO.getEmail(), alumniDTO.getContact());
        if (isDuplicateEmailOrContact) {
            model.addAttribute("dangerMessage", "email or contact already exist!!");
            return "registration-alumni";
        }

        AlumniDTO resultAlumniDTO = this.alumniService
                .registrationAlumni(alumniDTO, alumniImage);

        model.addAttribute("alumniDTO", new AlumniDTO());
        model.addAttribute("message", "alumni is successfully added...");
        return "registration-alumni";
    }

    // get all alumni
    @GetMapping(path = "/all")
    public String getAllAlumni(Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("alumniDTOS", this.alumniService.getAllAlumni());
        return "authenticated/show-all-alumni";
    }

    // get alumni by id
    @GetMapping(path = "/get/{alumniId}")
    public String getAlumniById(@PathVariable("alumniId") Long alumniId,
                                Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            AlumniDTO alumni = this.alumniService.getAlumniDTOIfLoggedIn(principal);
            model.addAttribute("name", alumni.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("alumniDTO", this.alumniService.getSingleAlumniById(alumniId));
        return "authenticated/show-single-alumni";
    }

}
