package com.alumni.services;

import com.alumni.dto.AlumniDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlumniService {

    AlumniDTO registrationAlumni(AlumniDTO alumniDTO, MultipartFile alumniImage) throws IOException;

    AlumniDTO getSingleAlumniById(Long alumniId);

    AlumniDTO getAlumniByEmail(String alumniEmail);

    List<AlumniDTO> getAllAlumni();

    void deleteAlumni(long alumniId);

    AlumniDTO updateAlumni(AlumniDTO alumniDTO, Long alumniId);

}
