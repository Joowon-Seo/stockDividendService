package com.zerobase.stockdividendservice.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dividend {

	private LocalDateTime data;
	private String dividend;

}
