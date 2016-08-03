package com.betfair.provider;

import com.betfair.provider.session.BetfairSessionFactory;
import com.betmonkey.domain.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 *
 */
public class BetfairDemoRunner {

    private static Properties prop = new Properties();
    private static Boolean jsonRpcRequest;
    private static String applicationKey;
    private static String sessionToken;
    private static String jsonOrRescript;
    private static boolean debug;

    static {
        try {
            InputStream in = BetfairDemoRunner.class.getResourceAsStream("/apingdemo.properties");
            prop.load(in);
            in.close();

            debug = new Boolean(prop.getProperty("DEBUG"));

        } catch (IOException e) {
            System.out.println("Error loading the properties file: "+e.toString());
        }
    }

    public static void main(String[] args) {



        BufferedReader inputStreamReader = null;
        //getting the AppKey and the session token
        BetfairSessionFactory factory = new BetfairSessionFactory();
        Session session = factory.getSession();

        BetfairProviderDemo rescriptDemo = new BetfairProviderDemo();
        rescriptDemo.start(session.getProduct(), session.getToken());

    }

    public static Properties getProp() {
        return prop;
    }

    public static boolean isDebug(){
        return debug;
    }
}
