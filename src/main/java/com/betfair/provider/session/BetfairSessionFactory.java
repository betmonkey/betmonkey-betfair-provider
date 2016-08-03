package com.betfair.provider.session;


import com.betmonkey.domain.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


/**
 * Created by scott on 01/08/16.
 */
public class BetfairSessionFactory implements SessionFactory {


        private Session session = null;
        private static String BETFAIR_URL = "https://identitysso.betfair.com/api/login";
        private static final String SUCCESS = "SUCCES";
        private String userName = "";
        private static final Logger Log = LoggerFactory.getLogger((Class)BetfairSessionFactory.class);



        public Session login(String userName, String password, String appKey) {
            this.userName = userName;

            this.session = SessionCache.getInstance().getSession(userName);
            if (this.session == null) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
                headers.add("X-Application", appKey);
                headers.add("username", userName);
                headers.add("password", password);
                LinkedMultiValueMap postParameters = new LinkedMultiValueMap();
                postParameters.add((Object)"username", (Object)userName);
                postParameters.add((Object)"password", (Object)password);
                HttpEntity requestEntity = new HttpEntity((Object)postParameters, (MultiValueMap)headers);
                this.session = (Session)restTemplate.postForObject(BETFAIR_URL, (Object)requestEntity, (Class)Session.class, new Object[0]);
                SessionCache.getInstance().addSession(userName, this.session);
            }
            System.out.println(this.session.toString());
            return this.session;
        }


        public Session getSession() {
            Session session = SessionCache.getInstance().getSession(this.userName);
            if (session == null) {
                session = this.login(System.getenv("USER"), System.getenv("PASSWORD"), System.getenv("APP_KEY"));
            }
            Log.info(session.toString());
            return session;
        }

}
