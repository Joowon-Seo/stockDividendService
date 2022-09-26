package com.zerobase.stockdividendservice.psersist;

import com.zerobase.stockdividendservice.psersist.entity.CompanyEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
	boolean existsByTicker(String ticker);
	Optional<CompanyEntity>findByName(String name);
}
