package com.betfair.provider.session;

import com.betmonkey.domain.Session;

import java.util.HashMap;

public class SessionCache {
    private static SessionCache instance = null;
    private static HashMap cache = new HashMap();

    private SessionCache() {
    }

    public static SessionCache getInstance() {
        if (instance == null) {
            instance = new SessionCache();
        }
        return instance;
    }

    public Session getSession(String userName) {
        return (Session)cache.get(userName);
    }

    public void addSession(String userName, Session session) {
        cache.put(userName, session);
    }
}