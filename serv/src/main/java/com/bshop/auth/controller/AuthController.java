package com.bshop.auth.controller;

import com.bshop.auth.JwtTokenUtil;
import com.bshop.auth.dto.jwt.JwtRequest;
import com.bshop.auth.dto.jwt.JwtResponse;
import com.bshop.auth.dto.registration.RegistrationData;
import com.bshop.auth.dto.registration.RegistrationResult;
import com.bshop.contactInfo.model.ContactInfo;
import com.bshop.contactInfo.model.ContactInfoRepository;
import com.bshop.user.model.Authority;
import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private ContactInfoRepository contactInfoRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserRepository userRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, ContactInfoRepository contactInfoRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.contactInfoRepository = contactInfoRepository;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        ResponseEntity result = null;
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        result = new ResponseEntity(new JwtResponse(token),headers, HttpStatus.OK);
        return result;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public RegistrationResult signup(@RequestBody RegistrationData data) {
        RegistrationResult result = null;
        List<String> problems = Lists.newArrayList();

        if (data != null && !Strings.isNullOrEmpty(data.username) && !Strings.isNullOrEmpty(data.pwd)) {
            Optional<User> user = userRepository.findByUsername(data.username);
            if (!user.isPresent()) {
                User newUser = new User();
                newUser.setUsername(data.username);
                newUser.setPwd(passwordEncoder.encode(data.pwd));

                ContactInfo contactInfo = new ContactInfo();

                if(!Strings.isNullOrEmpty(data.email)){
                    contactInfo.setEmail(data.email);
                }
                if(!Strings.isNullOrEmpty(data.phone)){
                    contactInfo.setPhone1(data.phone);
                }
                this.contactInfoRepository.save(contactInfo);
                newUser.setContactInfo(contactInfo);

                newUser.setAuthorities(getDefaultUserAuthorities());
                userRepository.save(newUser);

                result = RegistrationResult.successRequest();
            } else {
                problems.add("User is already present on the system.");
            }
        } else {
            problems.add("Data object is not valid.");
        }

        if (result == null) {
            result = RegistrationResult.erorrorRequest(problems);
        }

        return result;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private List<Authority> getDefaultUserAuthorities() {
        List<Authority> list = Lists.newArrayList(Authority.TEMP1, Authority.TEMP2, Authority.TEMP3, Authority.TEMP4);
        return list;
    }
}