package com.betfair.provider.session;

import com.betmonkey.domain.Session;

/**
 * Created by scott on 01/08/16.
 */
public interface SessionFactory

{
    public Session getSession();
}
