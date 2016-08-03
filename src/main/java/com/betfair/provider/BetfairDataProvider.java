package com.betfair.provider;


import com.betfair.provider.enums.BetfairOperationCall;
import com.betfair.provider.session.BetfairSessionFactory;
import com.betfair.provider.util.HttpUtil;
import com.betfair.provider.util.JsonConverter;
import com.betmonkey.DataProvider;
import com.betmonkey.domain.*;
import com.betmonkey.enums.MarketProjection;
import com.betmonkey.enums.MarketSort;
import com.betmonkey.enums.MatchProjection;
import com.betmonkey.enums.OrderProjection;
import com.betmonkey.exception.DataRetrievalException;
import com.google.gson.reflect.TypeToken;

import java.util.*;


public class BetfairDataProvider implements DataProvider {

    private static BetfairDataProvider instance = null;
    private final String FILTER = "filter";
    private final String LOCALE = "locale";
    private final String SORT = "sort";
    private final String MAX_RESULT = "maxResults";
    private final String MARKET_IDS = "marketIds";
    private final String MARKET_ID = "marketId";
    private final String INSTRUCTIONS = "instructions";
    private final String CUSTOMER_REF = "customerRef";
    private final String MARKET_PROJECTION = "marketProjection";
    private final String PRICE_PROJECTION = "priceProjection";
    private final String MATCH_PROJECTION = "matchProjection";
    private final String ORDER_PROJECTION = "orderProjection";
    private final String locale = Locale.getDefault().toString();


    private BetfairDataProvider(){}

    private static Session session = null;




    public static BetfairDataProvider getInstance(){
        if (instance == null){
            instance = new BetfairDataProvider();
            BetfairSessionFactory sessionFactory = new BetfairSessionFactory();
            session =sessionFactory.getSession();
        }
        return instance;
    }

    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws DataRetrievalException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        params.put(LOCALE, locale);
        String result = getInstance().makeRequest(BetfairOperationCall.LISTEVENTTYPES.getOperationName(), params, session.getProduct(), session.getToken());


        List<EventTypeResult> container = JsonConverter.convertFromJson(result, new TypeToken<List<EventTypeResult>>() {}.getType());

        return container;

    }

    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                           MatchProjection matchProjection, String currencyCode) throws DataRetrievalException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(MARKET_IDS, marketIds);
        params.put(PRICE_PROJECTION, priceProjection);
        params.put(ORDER_PROJECTION, orderProjection);
        params.put(MATCH_PROJECTION, matchProjection);
        String result = getInstance().makeRequest(BetfairOperationCall.LISTMARKETBOOK.getOperationName(), params, session.getProduct(), session.getToken());


        List<MarketBook> container = JsonConverter.convertFromJson(result, new TypeToken<List<MarketBook>>(){}.getType() );

        return container;

    }

    public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
                                                     MarketSort sort, String maxResult) throws DataRetrievalException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(FILTER, filter);
        params.put(SORT, sort);
        params.put(MAX_RESULT, maxResult);
        params.put(MARKET_PROJECTION, marketProjection);
        String result = getInstance().makeRequest(BetfairOperationCall.LISTMARKETCATALOGUE.getOperationName(), params, session.getProduct(), session.getToken());


        List<MarketCatalogue> container = JsonConverter.convertFromJson(result, new TypeToken< List<MarketCatalogue>>(){}.getType() );

        return container;

    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws DataRetrievalException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = getInstance().makeRequest(BetfairOperationCall.PLACORDERS.getOperationName(), params, session.getProduct(), session.getToken());


        return JsonConverter.convertFromJson(result, PlaceExecutionReport.class);

    }


    protected String makeRequest(String operation, Map<String, Object> params, String appKey, String ssoToken) throws DataRetrievalException {
        String requestString;
        //Handling the Rescript request
        params.put("id", 1);

        requestString =  JsonConverter.convertToJson(params);


        //We need to pass the "sendPostRequest" method a string in util format:  requestString
        HttpUtil requester = new HttpUtil();
        String response = requester.sendPostRequestRescript(requestString, operation, appKey, ssoToken);
        if(response != null)
            return response;
        else
            throw new DataRetrievalException();
    }

}

