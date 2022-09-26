package com.zerobase.stockdividendservice.service;

import com.zerobase.stockdividendservice.model.Company;
import com.zerobase.stockdividendservice.model.Dividend;
import com.zerobase.stockdividendservice.model.ScrapedResult;
import com.zerobase.stockdividendservice.psersist.CompanyRepository;
import com.zerobase.stockdividendservice.psersist.DividendRepository;
import com.zerobase.stockdividendservice.psersist.entity.CompanyEntity;
import com.zerobase.stockdividendservice.psersist.entity.DividendEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FinanceService {

	private final CompanyRepository companyRepository;
	private final DividendRepository dividendRepository;

	// 요청이 자주 들어오는가?
	// 자주 변경되는 데이터 인가?
	@Cacheable(key = "#companyName", value = "finance")
	public ScrapedResult getDividendByCompanyName(String companyName) {

		// 1. 회사명을 기준으로 회사 정보를 조회
		CompanyEntity company = this.companyRepository.findByName(companyName)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

		// 2. 조회된 회사 ID로 배당금 정보 조회
		List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(
			company.getId());

		// 3. 결과 조합 후 반환

//		List<Dividend> dividends = new ArrayList<>();
//		for (var entity : dividendEntities) {
//			dividends.add(Dividend.builder()
//				.date(entity.getDate())
//				.dividend(entity.getDividend())
//				.build());
//		}

		List<Dividend> dividends = dividendEntities.stream()
			.map(e -> new Dividend(e.getDate(), e.getDividend()))
			.collect(Collectors.toList());

		return new ScrapedResult(
			new Company(company.getTicker(), company.getName())
			, dividends);
	}
}
