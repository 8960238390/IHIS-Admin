package in.ashokit.responseBinding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanResponse {

	private Integer planId;
	private String planName;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Integer categoryId;
	private char activeStatus;
}
