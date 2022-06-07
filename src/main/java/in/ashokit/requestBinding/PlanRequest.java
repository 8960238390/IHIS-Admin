package in.ashokit.requestBinding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanRequest {

	private Integer planId;
	private String planName;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Integer planCategoryId;
	private char activeSw='D';

}
