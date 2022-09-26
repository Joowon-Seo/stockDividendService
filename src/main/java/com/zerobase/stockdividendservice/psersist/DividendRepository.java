package com.zerobase.stockdividendservice.psersist;

import com.zerobase.stockdividendservice.psersist.entity.DividendEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

	List<DividendEntity> findAllByCompanyId(Long CompanyId);

	@Transactional
	void deleteAllByCompanyId(Long Id);

	boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
