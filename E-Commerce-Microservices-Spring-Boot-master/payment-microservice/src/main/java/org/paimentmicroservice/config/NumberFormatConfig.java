package org.paimentmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.RoundingMode;
import java.text.DecimalFormat;
@Configuration
public class NumberFormatConfig {

    @Bean
    public DecimalFormat decimalFormat(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        return decimalFormat;
    }

}
