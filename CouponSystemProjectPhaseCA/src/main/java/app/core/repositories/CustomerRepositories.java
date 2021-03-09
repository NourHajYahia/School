package app.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Customer;

public interface CustomerRepositories extends JpaRepository<Customer, Integer> {

	List<Customer> findByEmail(String email);

	List<Customer> findByIdIsNotAndEmail(int id, String email);

	List<Customer> findByEmailAndPassword(String email, String password);
}
