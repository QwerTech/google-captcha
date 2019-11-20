package org.qwertech.captcha;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class CaptchaApplication {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String PRIVATE_KEY = "6LcZi8MUAAAAAILvNOUsyqWR-m5dychkqiaFJtzU";
    private RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(CaptchaApplication.class, args);
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkCaptcha(@RequestBody Req req) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", PRIVATE_KEY);
        params.add("response", req.token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForEntity(CAPTCHA_URL, request, String.class);

    }

    @Data
    private static class Req {
        private String token;
    }
}
