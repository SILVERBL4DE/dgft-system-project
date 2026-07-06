package com.htc.dgft.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String name;
    private String email;
    private List<String> roles;
}
