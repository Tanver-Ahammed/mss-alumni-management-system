package com.alumni.services.impl;

import com.alumni.entities.Alumni;
import com.alumni.model.AlumniUserDetails;
import com.alumni.repositories.AlumniRepository;
import com.alumni.services.AlumniUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: Md. Tanver Ahammed,
 * ICT, MBSTU
 */

@Service
public class AlumniUserDetailsServiceImpl implements AlumniUserDetailsService {

    @Autowired
    private AlumniRepository alumniRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumni alumni = this.alumniRepository.findByEmail(username);
        if (alumni == null)
            throw new UsernameNotFoundException("Alumni details not found for this user: " + username);
        return new AlumniUserDetails(alumni);
    }
}
