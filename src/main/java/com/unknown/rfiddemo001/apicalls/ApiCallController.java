package com.unknown.rfiddemo001.apicalls;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiCallController {


    RestTemplate restTemplate = new RestTemplate();
    Login login = new Login();

    @GetMapping("/call-api")
    public Quote callApi(){
        Quote quote = restTemplate.getForObject("https://quoters.apps.pcfone.io/api/random", Quote.class );
        return quote;

    }

    @GetMapping("/post-api")
    public ResponseEntity<Token> postApi(){

        login.setEmail("admin@gmai.com");
        login.setPassword("admin");
        HttpEntity<Login> loginHttpEntity = new HttpEntity<>(login);
        ResponseEntity<Token> tokenResponseEntity = restTemplate.exchange("http://127.0.0.1:8000/api/token", HttpMethod.POST,loginHttpEntity,Token.class);

        return  tokenResponseEntity;

    }
}
