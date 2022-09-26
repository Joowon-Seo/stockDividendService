package com.zerobase.stockdividendservice.scheduler;

import static com.zerobase.stockdividendservice.model.constatnts.CacheKey.KEY_FINANCE;

import com.zerobase.stockdividendservice.model.Company;
import com.zerobase.stockdividendservice.model.ScrapedResult;
import com.zerobase.stockdividendservice.psersist.CompanyRepository;
import com.zerobase.stockdividendservice.psersist.DividendRepository;
import com.zerobase.stockdividendservice.psersist.entity.CompanyEntity;
import com.zerobase.stockdividendservice.psersist.entity.DividendEntity;
import com.zerobase.stockdividendservice.scraper.Scraper;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

	private final CompanyRepository companyRepository;
	private final DividendRepository dividendRepository;

	private final Scraper yahooFinanceScraper;

	@CacheEvict(value = KEY_FINANCE, allEntries = true)
	@Scheduled(cron = "${scheduler.scrap.yahoo}")
	public void yahooFinanceScheduling() {
		log.info("scraping scheduler is started");
		// 저장된 회사 목록을 조회
		List<CompanyEntity> companies = this.companyRepository.findAll();

		// 회사마다 배당금 정보를 새로 스크래핑
		for (var company : companies) {
			log.info("scraping scheduler is started -> " + company.getName());
			ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
				new Company(company.getName(), company.getTicker()));

			// 스크래핑한 배당금 정보 중 데이터베이스에 없는 값은 저장
			scrapedResult.getDividendEntities().stream()
				// Dividend model을 entity로 mapping
				.map(e -> new DividendEntity(company.getId(), e))

				// 존재하지 않는경우 element 를 하나씩 dividendRepository 에 삽입
				.forEach(e -> {
					boolean exists = this.dividendRepository.existsByCompanyIdAndDate(
						e.getCompanyId(), e.getDate());
					if (!exists) {
						this.dividendRepository.save(e);
					}
				});

			// 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
			try {
				Thread.sleep(3000); // 3 seconds
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		}

	}

}
