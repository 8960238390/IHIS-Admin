package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.entities.CaseWorkerAccounts;
import in.ashokit.repository.CaseWorkerAccountRepo;
import in.ashokit.requestBinding.CaseWorkerAccountRequest;
import in.ashokit.responseBinding.CaseWorkerAccountResponse;
import in.ashokit.utility.EmailUtility;
import in.ashokit.utility.RandomPazzword;

@Service
public class CaseWorkerAccountServiceImpl implements CaseWorkerAccountService {

	@Autowired
	private CaseWorkerAccountRepo caseWorkerAccountRepo;

	@Autowired
	private EmailUtility emailUtility;

	private Logger logger = LoggerFactory.getLogger(CaseWorkerAccountServiceImpl.class);

	@Override
	public List<CaseWorkerAccountResponse> getAllCWAccount() {

		List<CaseWorkerAccounts> cwAccountsEntityList = caseWorkerAccountRepo.findAll();

		List<CaseWorkerAccountResponse> cwAccountResponseList = new ArrayList<>();

		cwAccountsEntityList.forEach(entity -> {

			CaseWorkerAccountResponse cwAccountResponse = convert(entity);

			cwAccountResponseList.add(cwAccountResponse);
		});

		return cwAccountResponseList;
	}

	@Override
	public CaseWorkerAccountResponse getCWAccountById(Integer id) {

		Optional<CaseWorkerAccounts> optional = caseWorkerAccountRepo.findById(id);

		if (optional.isPresent()) {

			CaseWorkerAccounts caseWorkerAccounts = optional.get();

			return convert(caseWorkerAccounts);

		}

		return null;
	}

	@Override
	public CaseWorkerAccountResponse save(CaseWorkerAccountRequest caseWorkerAccountRequest) {

		CaseWorkerAccounts caseWorkerAccountsEntity = new CaseWorkerAccounts();

		caseWorkerAccountsEntity.setAccId(caseWorkerAccountRequest.getAccId());
		caseWorkerAccountsEntity.setActiveSw(caseWorkerAccountRequest.getActiveSw());
		caseWorkerAccountsEntity.setDob(caseWorkerAccountRequest.getDob());
		caseWorkerAccountsEntity.setEmail(caseWorkerAccountRequest.getEmail());
		caseWorkerAccountsEntity.setFullName(caseWorkerAccountRequest.getFullName());
		caseWorkerAccountsEntity.setGender(caseWorkerAccountRequest.getGender());
		caseWorkerAccountsEntity.setMobileNumber(caseWorkerAccountRequest.getMobileNumber());
		caseWorkerAccountsEntity.setPzw(RandomPazzword.generatePazzwrd());
		caseWorkerAccountsEntity.setSsn(caseWorkerAccountRequest.getSsn());

		CaseWorkerAccounts saved = caseWorkerAccountRepo.save(caseWorkerAccountsEntity);

		boolean isSent = emailUtility.sendEmail(saved.getEmail(), "Account created successfully",
				emailBody("EmailTemplateCWAccountCreation.txt", saved));

		if (isSent) {
			return convert(saved);
		}

		return null;
	}

	@Override
	public String delete(Integer id) {

		caseWorkerAccountRepo.deleteById(id);

		return "success";
	}

	@Override
	public CaseWorkerAccountResponse activateOrDeactivateCWAccount(Integer id, char activeSw) {

		Optional<CaseWorkerAccounts> optional = caseWorkerAccountRepo.findById(id);

		if (optional.isPresent()) {
			CaseWorkerAccounts caseWorkerAccounts = optional.get();

			caseWorkerAccounts.setActiveSw(activeSw);

			CaseWorkerAccounts saved = caseWorkerAccountRepo.save(caseWorkerAccounts);

			return convert(saved);
		}

		return null;

	}

	private CaseWorkerAccountResponse convert(CaseWorkerAccounts caseWorkerAccounts) {

		if (caseWorkerAccounts == null) {
			return null;
		}

		CaseWorkerAccountResponse cwAccountResponse = new CaseWorkerAccountResponse();

		cwAccountResponse.setAccId(caseWorkerAccounts.getAccId());
		cwAccountResponse.setFullName(caseWorkerAccounts.getFullName());
		cwAccountResponse.setEmail(caseWorkerAccounts.getEmail());
		cwAccountResponse.setMobileNumber(caseWorkerAccounts.getMobileNumber());
		cwAccountResponse.setGender(caseWorkerAccounts.getGender());
		cwAccountResponse.setSsn(caseWorkerAccounts.getSsn());
		cwAccountResponse.setActiveSw(caseWorkerAccounts.getActiveSw());

		return cwAccountResponse;
	}

	public String emailBody(String file, CaseWorkerAccounts cwAccount) {

		StringBuilder buffer = new StringBuilder();

		String body = null;

		try (FileReader fileReader = new FileReader(file);
				BufferedReader buffredReader = new BufferedReader(fileReader);) {

			String line = buffredReader.readLine();

			while (line != null) {
				buffer.append(line);
				line = buffredReader.readLine();
			}

			body = buffer.toString();

			body = body.replace("{FULLNAME}", cwAccount.getFullName());
			body = body.replace("{PWD}", cwAccount.getPzw());

		} // try
		catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage(), fnfe);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} // catch

		return body;
	}// emailBody()

}
