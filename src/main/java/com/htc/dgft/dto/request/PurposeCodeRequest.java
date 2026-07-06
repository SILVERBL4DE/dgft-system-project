package com.htc.dgft.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PurposeCodeRequest {
    @NotBlank(message = "Code is required")
    private String code;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Status is required")
    private String status;
}
