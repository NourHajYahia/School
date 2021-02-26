package app.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

public interface CompanyRepositories extends JpaRepository<Company, Integer> {
	
	List<Company> findByName(String name);
	
	List<Company> findByEmail(String email);

	List<Company> findByIdIsNotAndEmail(Integer id, String email);
	
	List<Company> findByEmailAndPassword(String email, String password);
	
}
