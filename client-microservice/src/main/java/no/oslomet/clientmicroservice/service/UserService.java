package no.oslomet.clientmicroservice.service;

import netscape.javascript.JSObject;
import no.oslomet.clientmicroservice.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
public class UserService {
    private String baseUrl = "http://localhost:9090";
    private RestTemplate restTemplate = new RestTemplate();
    public User login(User user){
        String loginUrl = baseUrl + "/login";
        HttpEntity<User> request = new HttpEntity<>(user, createHeaders(user.getEmail(), user.getPassword()));
        User loggedUser;
        try {
            loggedUser =restTemplate.postForObject(loginUrl, request,  User.class);
        } catch (Exception e ){
            System.out.println("Exception type: " + e.getMessage());
            loggedUser = null;
        }

        return loggedUser;
    }

    public User signup(User user){
        String signupUrl = baseUrl +"/signup";
        User newUser;
        try{
            newUser = restTemplate.postForObject(signupUrl, user, User.class);
        } catch(Exception e ){
            System.out.println("Exception: " + e.getMessage());
            newUser = null;
        }
       return newUser;
    }


    public HttpHeaders createHeaders(String email, String password){
        return new HttpHeaders() {{
            String auth = email + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
