package com.bshop.user;


import com.bshop.contactInfo.model.ContactInfo;
import com.bshop.contactInfo.model.ContactInfoRepository;
import com.bshop.user.assembler.UserAssembler;
import com.bshop.user.dto.UserFull;
import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    UserRepository userRepository;
    ContactInfoRepository contactInfoRepository;
    UserAssembler userAssembler = new UserAssembler();

    public UserController(UserRepository userRepository, ContactInfoRepository contactInfoRepository) {
        this.userRepository = userRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserFull> getByIds(@RequestParam List<Integer> ids) {
        List<UserFull> result = Lists.newArrayList();

        List<User> users = Lists.newArrayList(userRepository.findAllById(ids));
        result = users.stream().map(userAssembler::assembleShort).collect(Collectors.toList());

        return result;
    }

    @RequestMapping(path = "me", method = RequestMethod.GET)
    public ResponseEntity<UserFull> getMyData() {
        UserFull result = null;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optional = userRepository.findByUsername(username);

        if (optional.isPresent()) {
            result = userAssembler.assembleFull(optional.get());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
        ResponseEntity responseEntity = new ResponseEntity(result, headers, HttpStatus.OK);

        return responseEntity;
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    public ResponseEntity<UserFull> updateUser(@RequestBody UserFull data) {
        UserFull result = null;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optional = userRepository.findByUsername(username);

        if (optional.isPresent()) {
            User user = optional.get();
//            if (!Strings.isNullOrEmpty(data.username)) {
//                user.setUsername(data.username);
//            }

            if (data.contactInfo != null) {
                ContactInfo contactInfo = user.getContactInfo();
                if (!Strings.isNullOrEmpty(data.contactInfo.firstName)) {
                    contactInfo.setFirstName(data.contactInfo.firstName);
                }
                if (!Strings.isNullOrEmpty(data.contactInfo.lastName)) {
                    contactInfo.setLastName(data.contactInfo.lastName);
                }
                if (!Strings.isNullOrEmpty(data.contactInfo.middleName)) {
                    contactInfo.setMiddleName(data.contactInfo.middleName);
                }
                if (!Strings.isNullOrEmpty(data.contactInfo.email)) {
                    contactInfo.setEmail(data.contactInfo.email);
                }
                if (data.contactInfo.dob != null) {
                    contactInfo.setDob(data.contactInfo.dob);
                }
                if (!Strings.isNullOrEmpty(data.contactInfo.phone1)) {
                    contactInfo.setPhone1(data.contactInfo.phone1);
                }
                if (!Strings.isNullOrEmpty(data.contactInfo.phone2)) {
                    contactInfo.setPhone2(data.contactInfo.phone2);
                }
                contactInfoRepository.save(contactInfo);
            }
            this.userRepository.save(user);

            result = userAssembler.assembleFull(user);
        }

        ResponseEntity responseEntity = new ResponseEntity(result, HttpStatus.OK);

        return responseEntity;
    }
}
