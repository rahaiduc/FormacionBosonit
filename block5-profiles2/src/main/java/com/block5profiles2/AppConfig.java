package com.block5profiles2;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppConfig
{
    @Value("${bd.url}")
    private String bd;

}
