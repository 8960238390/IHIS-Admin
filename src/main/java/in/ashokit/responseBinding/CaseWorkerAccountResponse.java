package in.ashokit.responseBinding;

import lombok.Data;

@Data
public class CaseWorkerAccountResponse {

	private Integer accId;
	private String fullName;
	private String email;
	private Long mobileNumber;
	private char gender;
	private Integer ssn;
	private char activeSw;

}
