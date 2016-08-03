package com.betfair.provider.enums;

public enum BetfairOperationCall {
	LISTEVENTTYPES("listEventTypes"), 
	LISTCOMPETITIONS("listCompetitions"),
	LISTTIMERANGES("listTimeRanges"),
	LISTEVENTS("listEvents"),
	LISTMARKETTYPES("listMarketTypes"),
	LISTCOUNTRIES("listCountries"),
	LISTVENUES("listVenues"),
	LISTMARKETCATALOGUE("listMarketCatalogue"),
	LISTMARKETBOOK("listMarketBook"),
	PLACORDERS("placeOrders");
	
	private String operationName;
	
	private BetfairOperationCall(String operationName){
		this.operationName = operationName;
	}

	public String getOperationName() {
		return operationName;
	}

	

}
