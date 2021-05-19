package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderParamsDto {
    private String address;
    private String phone;
    private String email;
}
