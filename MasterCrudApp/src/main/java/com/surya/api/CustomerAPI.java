package com.surya.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surya.dto.CustomerDTO;
import com.surya.exception.CustomerException;
import com.surya.service.CustomerService;

@RestController
@RequestMapping(value = "/suryaApp")
public class CustomerAPI {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private Environment environment;

	@GetMapping(value = "/customers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws CustomerException {
		List<CustomerDTO> customerList = customerService.getAllCustomers();
		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}

	@GetMapping(value = "/customers/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Integer customerId) throws CustomerException {
		CustomerDTO customer = customerService.getCustomer(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customer) throws CustomerException {
		Integer customerId = customerService.addCustomer(customer);
		String successMessage = environment.getProperty("API.INSERT_SUCCESS") + customerId;
		
		//no headers are returned in controller method so it's not there
		//adding one only for this class  to test
		HttpHeaders headers = new HttpHeaders();
		headers.set("sample header", "header testing ");
		//return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		// gonna work
		// this header is working
		// missed component annotation on filter class p
		return ResponseEntity.ok().headers(headers).body(successMessage);
	}

	@PutMapping(value = "/customers/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customer)
			throws CustomerException {
		customerService.updateCustomer(customerId, customer.getEmailId());
		String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	@DeleteMapping(value = "/customers/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws CustomerException {
		customerService.deleteCustomer(customerId);
		String successMessage = environment.getProperty("API.DELETE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}
}
