package com.zerobase.stockdividendservice.scraper;

import com.zerobase.stockdividendservice.model.Company;
import com.zerobase.stockdividendservice.model.ScrapedResult;

public interface Scraper {
	Company scrapCompanyByTicker(String ticker);
	ScrapedResult scrap(Company company);

}
