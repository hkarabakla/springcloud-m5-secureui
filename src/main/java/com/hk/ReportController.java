package com.hk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Created by z003rv2s on 02.02.2017.
 */
@Controller
@EnableOAuth2Sso
public class ReportController extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext clientContext;

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping(path = "/")
    public String loadHome() {
        return "home";
    }

    @RequestMapping("/reports")
    public String loadReports(Model model) {

        OAuth2AccessToken token = clientContext.getAccessToken();
        System.out.println("Token : " + token.getValue());


        ResponseEntity<ArrayList<TollUsage>> tolls = oAuth2RestTemplate.exchange("http://localhost:9001/services/tolldata", HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<TollUsage>>(){});

        model.addAttribute("tolls", tolls.getBody());

        return "reports";
    }

    public static class TollUsage {

        public String Id;
        public String stationId;
        public String licensePlate;
        public String timestamp;

        public TollUsage() {}

        public TollUsage(String id, String stationid, String licenseplate, String timestamp){
            this.Id = id;
            this.stationId = stationid;
            this.licensePlate = licenseplate;
            this.timestamp = timestamp;
        }


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/login**")
                    .permitAll()
                .anyRequest()
                    .authenticated();
    }
}
