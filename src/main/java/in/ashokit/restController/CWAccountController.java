package in.ashokit.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.requestBinding.CaseWorkerAccountRequest;
import in.ashokit.responseBinding.CaseWorkerAccountResponse;
import in.ashokit.service.CaseWorkerAccountService;

@RestController
public class CWAccountController {

	@Autowired
	private CaseWorkerAccountService caseWorkerAccountService;

	@GetMapping("/caseworkers")
	public ResponseEntity<List<CaseWorkerAccountResponse>> getAllCWAccount() {

		List<CaseWorkerAccountResponse> allCWAccount = caseWorkerAccountService.getAllCWAccount();

		if (allCWAccount != null) {
			return new ResponseEntity<>(allCWAccount, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/caseworker/{id}")
	public ResponseEntity<CaseWorkerAccountResponse> getCWAccountById(@PathVariable("id") Integer id) {

		CaseWorkerAccountResponse cwAccount = caseWorkerAccountService.getCWAccountById(id);

		if (cwAccount != null) {
			return new ResponseEntity<>(cwAccount, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/caseworker")
	public ResponseEntity<CaseWorkerAccountResponse> upsert(@RequestBody CaseWorkerAccountRequest caseWorkerAccountRequest) {

		CaseWorkerAccountResponse saved = caseWorkerAccountService.save(caseWorkerAccountRequest);

		if (saved != null) {
			return new ResponseEntity<>(saved, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/caseworker/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {

		String msg = caseWorkerAccountService.delete(id);

		if (msg != null) {
			return new ResponseEntity<>(msg, HttpStatus.OK);
		}

		return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/accountswitch/{id}/{status}")
	public ResponseEntity<CaseWorkerAccountResponse> activateOrDeactivateCWAccount(@PathVariable("id") Integer id, @PathVariable("status") char status) {
		
		CaseWorkerAccountResponse caseWorkerAccountResponse = caseWorkerAccountService.activateOrDeactivateCWAccount(id, status);
		
		if(caseWorkerAccountResponse!=null) {
			return new ResponseEntity<>(caseWorkerAccountResponse,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(caseWorkerAccountResponse,HttpStatus.NOT_FOUND);
	
	}
}
