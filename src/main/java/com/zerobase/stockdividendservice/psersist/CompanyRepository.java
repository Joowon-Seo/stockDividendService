package com.zerobase.stockdividendservice.psersist;

import com.zerobase.stockdividendservice.psersist.entity.CompanyEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
	boolean existsByTicker(String ticker);
	Optional<CompanyEntity>findByName(String name);

	Page<CompanyEntity> findByNameStartingWithIgnoreCase(String s, Pageable pageable);

	Optional<CompanyEntity> findByTicker(String ticker);
}
