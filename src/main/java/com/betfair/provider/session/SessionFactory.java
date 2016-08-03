package com.betfair.provider.session;

import com.betmonkey.domain.Session;
import com.betmonkey.exception.DataRetrievalException;

/**
 * Created by scott on 01/08/16.
 */
public interface SessionFactory

{
    public Session getSession() throws DataRetrievalException;
}
