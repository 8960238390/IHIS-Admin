package in.ashokit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.repository.PlanCategoryMasterRepo;

@Service
public class PlanCategoryServiceImpl implements PlanCategoryService {

	@Autowired
	private PlanCategoryMasterRepo planCategoryMasterRepo;

	@Override
	public String[] getPlanCategories() {

		return planCategoryMasterRepo.findCategoryName();
	}

}
