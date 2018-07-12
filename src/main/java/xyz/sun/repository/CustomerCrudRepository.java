package xyz.sun.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.sun.entity.Customer;

import java.util.List;

public interface CustomerCrudRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);

}
