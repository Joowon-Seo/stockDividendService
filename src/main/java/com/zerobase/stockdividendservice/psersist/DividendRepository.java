package com.zerobase.stockdividendservice.psersist;

import com.zerobase.stockdividendservice.model.Dividend;
import com.zerobase.stockdividendservice.psersist.entity.DividendEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
	List<DividendEntity> findAllByCompanyId(Long CompanyId);
}
