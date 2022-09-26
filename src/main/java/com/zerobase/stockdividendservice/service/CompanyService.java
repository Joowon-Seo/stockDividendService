package com.zerobase.stockdividendservice.service;

import com.zerobase.stockdividendservice.model.Company;
import com.zerobase.stockdividendservice.model.ScrapedResult;
import com.zerobase.stockdividendservice.psersist.CompanyRepository;
import com.zerobase.stockdividendservice.psersist.DividendRepository;
import com.zerobase.stockdividendservice.psersist.entity.CompanyEntity;
import com.zerobase.stockdividendservice.psersist.entity.DividendEntity;
import com.zerobase.stockdividendservice.scraper.Scraper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class CompanyService {

	private final Scraper yahooFinanceScraper;

	private final CompanyRepository companyRepository;
	private final DividendRepository dividendRepository;

	public Company save(String ticker) {
		boolean exists = this.companyRepository.existsByTicker(ticker);
		if (exists) {
			throw new RuntimeException("already exists ticker -> " + ticker);
		}
		return this.storeCompanyAndDividend(ticker);
	}

	private Company storeCompanyAndDividend(String ticker) {
		// ticker 를 기준으로 회사를 스크래핑
		Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
		if (ObjectUtils.isEmpty(company)) {
			throw new RuntimeException("failed to scrap ticker -> " + ticker);
		}

		// 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
		ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

		// 스프래핑 결과
		CompanyEntity companyEntity = this.companyRepository.save(
			new CompanyEntity(company));
		List<DividendEntity> dividendEntities = scrapedResult.getDividendEntities()
			.stream()
			.map(e -> new DividendEntity(companyEntity.getId(), e))
			.collect(Collectors.toList());

		this.dividendRepository.saveAll(dividendEntities);
		return company;
	}

}
