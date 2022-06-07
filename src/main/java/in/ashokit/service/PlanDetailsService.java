package in.ashokit.service;

import java.util.List;

import in.ashokit.requestBinding.PlanRequest;
import in.ashokit.responseBinding.PlanResponse;

public interface PlanDetailsService {

	public List<PlanResponse> getAllPlans();
	public PlanResponse getPlanById(Integer id);
	public PlanResponse save(PlanRequest planRequest);
	public String delete(Integer id);
	public PlanResponse activateOrDeactivatePlan(Integer id,char activeSw);
}
