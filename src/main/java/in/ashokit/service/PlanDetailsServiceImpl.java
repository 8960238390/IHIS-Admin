package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.entities.AppPlans;
import in.ashokit.repository.AppPlansRepo;
import in.ashokit.requestBinding.PlanRequest;
import in.ashokit.responseBinding.PlanResponse;

@Service
public class PlanDetailsServiceImpl implements PlanDetailsService {

	@Autowired
	private AppPlansRepo appPlansRepo;

	@Override
	public List<PlanResponse> getAllPlans() {

		ArrayList<PlanResponse> planResponseList = new ArrayList<>();

		List<AppPlans> findAll = appPlansRepo.findAll();

		findAll.forEach(planEntity -> {

			PlanResponse response = new PlanResponse();

			BeanUtils.copyProperties(planEntity, response);

			planResponseList.add(response);

		});

		return planResponseList;
	}

	@Override
	public PlanResponse getPlanById(Integer id) {

		Optional<AppPlans> planEntity = appPlansRepo.findById(id);

		PlanResponse planResponse = new PlanResponse();

		if (planEntity.isPresent()) {

			BeanUtils.copyProperties(planEntity.get(), planResponse);

			return planResponse;
		}

		return null;
	}

	@Override
	public PlanResponse save(PlanRequest planRequest) {
		
		AppPlans entity = new AppPlans();
		
		entity.setPlanId(planRequest.getPlanId());
		entity.setPlanName(planRequest.getPlanName());
		entity.setPlanStartDate(planRequest.getPlanStartDate());
		entity.setPlanEndDate(planRequest.getPlanEndDate());
		entity.setCategoryId(planRequest.getPlanCategoryId());
		entity.setActiveSw(planRequest.getActiveSw());
		
		AppPlans saved = appPlansRepo.save(entity);
		
		
		PlanResponse planResponse = new PlanResponse();
		planResponse.setPlanId(saved.getPlanId());
		planResponse.setPlanName(saved.getPlanName());
		planResponse.setPlanStartDate(saved.getPlanStartDate());
		planResponse.setPlanEndDate(saved.getPlanEndDate());
		planResponse.setCategoryId(saved.getCategoryId());
		planResponse.setActiveStatus(saved.getActiveSw());

		return planResponse;
	}

	@Override
	public String delete(Integer id) {

		appPlansRepo.deleteById(id);

		return "Success";
	}

	@Override
	public PlanResponse activateOrDeactivatePlan(Integer id, char activeStatus) {
		
		Optional<AppPlans> optional = appPlansRepo.findById(id);
		
		if (optional.isPresent()) {
			
			PlanResponse planResponse = new PlanResponse();
		
			AppPlans planEntity = optional.get();

			planEntity.setActiveSw(activeStatus);
			
			AppPlans saved = appPlansRepo.save(planEntity);
			
			BeanUtils.copyProperties(saved, planResponse);
			
			return planResponse;
		}

		return null;
	}

}
