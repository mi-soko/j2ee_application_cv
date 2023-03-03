package com.javatab.service.impl;

import com.javatab.domain.entity.User;
import com.javatab.dto.request.AuthenticationRequest;
import com.javatab.dto.request.RegistrationRequest;
import com.javatab.dto.response.AuthenticationResponse;
import com.javatab.model.security.SecurityUser;
import com.javatab.repository.UserRepository;
import com.javatab.security.TokenUtils;
import com.javatab.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return new AuthenticationResponse(
                Objects.requireNonNull(this.tokenUtils.generateToken(userDetails, authenticationRequest.getDevice())));
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser user = (SecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            return new AuthenticationResponse(this.tokenUtils.refreshToken(token));
        }
        return new AuthenticationResponse();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        User newUser = User.builder()
                .username(registrationRequest.getEmail())
                .password(hashedPassword)
                .email(registrationRequest.getEmail())
                .lastPasswordReset(new Date())
                .levelStudy(registrationRequest.getLevelStudy())
                .age(registrationRequest.getAge())
                .address(registrationRequest.getAddress())
                .speciality(registrationRequest.getSpeciality())
                .tel(registrationRequest.getTel())
                .professionalExperience(registrationRequest.getProfessionalExperience())
                .authorities("ADMIN")
                .build();
        return userRepository.save(newUser);
    }
}
