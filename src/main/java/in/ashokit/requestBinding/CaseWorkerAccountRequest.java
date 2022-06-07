package in.ashokit.requestBinding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CaseWorkerAccountRequest {

	private Integer accId;
	private String fullName;
	private String email;
	private Long mobileNumber;
	private char gender;
	private LocalDate dob;
	private Integer ssn;
	private char activeSw = 'D';

}
