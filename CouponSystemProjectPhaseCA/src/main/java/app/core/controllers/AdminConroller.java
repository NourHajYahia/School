package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.exceptions.ServiceException;
import app.core.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminConroller {
	
	@Autowired
	public AdminService adminService;
	
	@PostMapping("/addCompany/{id}/{name}/{password}/{email}")
	public Company addCompany(@PathVariable int id, @PathVariable String name, @PathVariable String email, @PathVariable String password) {
		try {
			Company company = new Company(id, name, email, password);
			return adminService.addCompany(company);
		} catch (ServiceException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}
	
	@DeleteMapping("/deleteCompany")
	public String deleteCompany(@RequestParam int companyID) {
		try {
			adminService.deleteCompany(companyID);
			return "Company with id = " + companyID + ", is deleted";
		} catch (ServiceException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PostMapping("/getAllCompanies")
	public List<Company> getAllCompanies() {
		return adminService.getAllCompanies();
	}
	
	@PostMapping("/updateCompany/{id}/{name}/{password}/{email}")
	public Company updateCompany(@PathVariable int id, @PathVariable String name, @PathVariable String email, @PathVariable String password) {
		try {
			Company company = new Company(id, name, email, password);
			return adminService.updateCompany(company);
		} catch (ServiceException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

}
