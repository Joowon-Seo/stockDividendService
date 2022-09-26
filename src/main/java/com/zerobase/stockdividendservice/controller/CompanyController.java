package com.zerobase.stockdividendservice.controller;

import com.zerobase.stockdividendservice.model.Company;
import com.zerobase.stockdividendservice.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping("/autocomplete")
	public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
		return null;
	}

	@GetMapping
	public ResponseEntity<?> searchCompany() {
		return null;
	}

	/**
	 * 회사 및 배당금 정보 추가
	 * @param request
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> addCompany(@RequestBody Company request) {
		String ticker = request.getTicker().trim();
		if (ObjectUtils.isEmpty(ticker)) {
			throw new RuntimeException("ticker is empty");
		}

		Company company = this.companyService.save(ticker);

		return ResponseEntity.ok(company);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCompany() {
		return null;
	}

}
