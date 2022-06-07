package in.ashokit.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.requestBinding.PlanRequest;
import in.ashokit.responseBinding.PlanResponse;
import in.ashokit.service.PlanCategoryService;
import in.ashokit.service.PlanDetailsService;

@RestController
public class PlanController {

	@Autowired
	private PlanDetailsService planDetailsService;
	
	@Autowired
	private PlanCategoryService planCategoryService;

	@GetMapping("/plans")
	@CrossOrigin
	public ResponseEntity<List<PlanResponse>> findAllPlans() {

		List<PlanResponse> allPlans = planDetailsService.getAllPlans();
		
		if(allPlans == null)
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(allPlans, HttpStatus.OK);
	}

	@GetMapping("/plan/{id}")
	@CrossOrigin
	public ResponseEntity<PlanResponse> findPlanById(@PathVariable("id") Integer id) {

		PlanResponse planResponse = planDetailsService.getPlanById(id);

		if (planResponse == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(planResponse, HttpStatus.OK);
	}

	@PostMapping("/plan")
	@CrossOrigin
	public ResponseEntity<PlanResponse> insert(@RequestBody PlanRequest planRequest) {

		PlanResponse save = planDetailsService.save(planRequest);

		if (save == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

	@DeleteMapping("/plan/{id}")
	@CrossOrigin
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		String message = planDetailsService.delete(id);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping("/categories")
	@CrossOrigin
	public ResponseEntity<String[]> getAllPlanCategories(){
		
		String[] planCategories = planCategoryService.getPlanCategories();
		
		if(planCategories == null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(planCategories,HttpStatus.OK);
	}
	
	@GetMapping("/planswitch/{id}/{status}")
	public ResponseEntity<PlanResponse> activateOrDeactivatePlan(@PathVariable("id") Integer id, @PathVariable("status") char status) {
		
		PlanResponse planResponse = planDetailsService.activateOrDeactivatePlan(id, status);
		
		if(planResponse!=null) {
			new ResponseEntity<>(planResponse,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
}
