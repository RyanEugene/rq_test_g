package com.example.restservice;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@RestController
public class EmployeeController {
	private final EmployeeRepository repository;

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EntityManager entityManager;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * Base URL endpoint
	 * @param name
	 * @return a Greeting entity
	 */
	@GetMapping("/")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/employee/{id}")
	Employee one(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@GetMapping("/employeeByName/{name}")
	Employee getEmployeeByName(@PathVariable String name) {
		Query query = entityManager.createNativeQuery("SELECT id, name, role, salary FROM Employees where name = ?1");
		query.setParameter(1, name);  // don't allow SQL injection from the API
		List<Object[]> results = query.getResultList();
		int numResults = results.size();
		if (numResults != 1) {
			log.error("getEmployeeByName: Found " + numResults + " results from Employees table in - expected exactly 1 record");
			throw new EmployeeNotFoundException(name);
		}
		else {
			return new Employee(results.get(0));
		}
	}

	@GetMapping("/employee")
	List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	@PostMapping("/employee")
	Employee createEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}

	/**
	 * Returns max salary of all
	 * @return an Int of max salary - note, should be JSON, but question/spec said return integer
	 */
	@GetMapping("/maxEmployeeSalary")
    int getHighestSalaryOfEmployees() {
		log.debug("Calling getHighestSalaryOfEmployees");
		List<Integer> results = entityManager.createNativeQuery("SELECT max(salary) FROM Employees").getResultList();
		int numResults = results.size();
		if (numResults != 1) {
			String error = "getHighestSalaryOfEmployees: Found " + numResults + " results from Employees table in - expected exactly 1 record";
			log.error(error);
			throw new EmployeeException(error);
		}
		else {
			return results.get(0).intValue();
		}
	}


	@GetMapping("/top10EmployeesBySalary")
	List<Employee> getTop10HighestEarningEmployee() {
		log.debug("getTop10HighestEarningEmployee");
		Query query = entityManager.createNativeQuery("SELECT id, name, role, salary FROM Employees order by salary DESC limit 10");
		List<Object[]> results = query.getResultList();
		int numResults = results.size();
		if (numResults > 10) {
			String error = "getTop10HighestEarningEmployeeNames: Found " + numResults + " results from Employees table - expected max 10 records";
			log.error(error);
			throw new EmployeeException(error);
		}
		else {
			return results.stream().map(e -> new Employee(e)).collect(Collectors.toList());

		}
	}

	// deleteEmployee
	@DeleteMapping("/employee/{id}")
	Employee deleteEmployee(@PathVariable Long id) {
		log.debug("Calling deleteEmployee(" + id + ")");
		Employee e = repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
		repository.deleteById(id);
		return e;
	}
}
