package com.zerobase.stockdividendservice.psersist;

import com.zerobase.stockdividendservice.psersist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

}
