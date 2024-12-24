package com.adm.test.dto.response;

import com.adm.test.entity.User;
import lombok.Data;

@Data
public class LoginResponse {

    private String userName;
    private String token;

    public static LoginResponse fromUser(User user, String token) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserName(user.getUsername());
        loginResponse.setToken(token);
        return loginResponse;
    }
}
