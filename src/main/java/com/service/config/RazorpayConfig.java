package com.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "razorpay")
public class RazorpayConfig {
    private String rzp_key_id;
    private String rzp_key_secret;
    private String rzp_currency;
    private String rzp_company_name;
    private String rzp_webhook_secret;
    private boolean validate_webhook;
}
