package com.bshop.auth.dto.registration;

import java.util.List;

public class RegistrationResult {
    public boolean success;
    public List<String> errors;

    public RegistrationResult(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }

    public static RegistrationResult successRequest(){
        return new RegistrationResult(true, null);
    }

    public static RegistrationResult erorrorRequest(List<String> problems){
        return new RegistrationResult(false, problems);
    }
}
