package in.ashokit.service;

import java.util.List;

import in.ashokit.requestBinding.CaseWorkerAccountRequest;
import in.ashokit.responseBinding.CaseWorkerAccountResponse;

public interface CaseWorkerAccountService {

	public List<CaseWorkerAccountResponse> getAllCWAccount();
	public CaseWorkerAccountResponse getCWAccountById(Integer id);
	public CaseWorkerAccountResponse save(CaseWorkerAccountRequest caseWorkerAccountRequest);
	public String delete(Integer id);
	public CaseWorkerAccountResponse activateOrDeactivateCWAccount(Integer id,char activeSw);
}
