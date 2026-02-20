package com.crm.item.core.dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ErrorResponse {
    private String message;
}
