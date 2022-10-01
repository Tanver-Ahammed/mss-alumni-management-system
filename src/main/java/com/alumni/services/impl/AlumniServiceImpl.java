package com.alumni.services.impl;

import com.alumni.config.AppContents;
import com.alumni.dto.AlumniDTO;
import com.alumni.entities.Alumni;
import com.alumni.exception.ResourceNotFoundException;
import com.alumni.repositories.AlumniRepository;
import com.alumni.services.AlumniService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumniServiceImpl implements AlumniService {

    @Autowired
    private AlumniRepository alumniRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${project.image}")
    private String path;

    @Override
    public AlumniDTO registrationAlumni(AlumniDTO alumniDTO, MultipartFile alumniImage) throws IOException {
        String alumniImageName = this.fileService.uploadImage(path, alumniImage);
        alumniDTO.setImage(alumniImageName);
        String verificationCode = RandomString.make(6);
        com.alumni.entities.Alumni alumni = this.modelMapper.map(alumniDTO, com.alumni.entities.Alumni.class);
        alumni.setEnable(true);
        alumni.setVerificationCode(verificationCode);
        alumni.setRole(String.valueOf(AppContents.ALUMNI));
        alumni.setPassword(passwordEncoder.encode(alumniDTO.getPassword()));
        alumniDTO = this.modelMapper.map(this.alumniRepository.save(alumni), AlumniDTO.class);
//        String siteURL = AppConstants.host + "/student/verify";
//        sendVerificationEmail(studentDTO, siteURL);
        return alumniDTO;
    }

    @Override
    public AlumniDTO getSingleAlumniById(Long alumniId) {
        return this.alumniToDTO(this.getAlumniById(alumniId));
    }

    @Override
    public AlumniDTO getAlumniByEmail(String alumniEmail) {
        Alumni alumni = this.alumniRepository.findByEmail(alumniEmail);
        return this.alumniToDTO(alumni);
    }

    @Override
    public List<AlumniDTO> getAllAlumni() {
        return this.alumniRepository
                .findAll()
                .stream()
                .map(this::alumniToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAlumni(long alumniId) {

    }

    @Override
    public AlumniDTO updateAlumni(AlumniDTO alumniDTO, Long alumniId) {
        return null;
    }

    // get alumni by id
    public Alumni getAlumniById(Long alumniId) {
        return this.alumniRepository.findById(alumniId).orElseThrow(() ->
                new ResourceNotFoundException("Alumni", "id", alumniId));
    }

    // alumni to dto
    public AlumniDTO alumniToDTO(Alumni alumni) {
        return this.modelMapper.map(alumni, AlumniDTO.class);
    }

    // alumni to dto
    public Alumni alumniDTOToAlumni(AlumniDTO alumniDTO) {
        return this.modelMapper.map(alumniDTO, Alumni.class);
    }


    public AlumniDTO getAlumniDTOIfLoggedIn(Principal principal) {
        String username = principal.getName();
        AlumniDTO authorityDTO = this.getAlumniByEmail(username);
        if (authorityDTO.getName().length() > 10)
            authorityDTO.setName(authorityDTO.getName().substring(0, 10));
        authorityDTO.setPassword(null);
        return authorityDTO;
    }

    public Boolean isDuplicateAlumniByEmailOrContact(String email, String contact) {
        Alumni alumni = this.alumniRepository.findByEmailOrContact(email, contact);
        return alumni != null;
    }
}
