package com.javatab.service;

import com.javatab.dto.request.AuthenticationRequest;
import com.javatab.dto.response.AuthenticationResponse;
import com.javatab.dto.request.RegistrationRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse refreshToken(String token);
    Object registerUser(RegistrationRequest registrationRequest);
}
