package com.bshop.user.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    TEMP1("TEMP1"),
    TEMP2("TEMP2"),
    TEMP3("TEMP3"),
    TEMP4("TEMP4"),
    TEMP5("TEMP5");

    Authority(String value) {
        this.value = value;
    }

    String value;

    public static Authority fromCode(String code) throws Exception {
        Authority result = null;
        if ("TEMP1".equals(code)) {
            result = TEMP1;
        } else if ("TEMP2".equals(code)){
            result = TEMP2;
        } else if ("TEMP3".equals(code)){
            result = TEMP3;
        } else if ("TEMP4".equals(code)){
            result = TEMP4;
        } else if ("TEMP5".equals(code)){
            result = TEMP5;
        } else {
            throw new Exception();
        }
        return result;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
