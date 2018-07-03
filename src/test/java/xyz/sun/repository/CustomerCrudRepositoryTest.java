package xyz.sun.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.sun.entity.Customer;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerCrudRepositoryTest {

    @Autowired
    private CustomerCrudRepository repository;

    @Test
    public void findByLastName() {
        Customer customer = new Customer("first", "last");
        List<Customer> findByLastName = repository.findByLastName("Bauer");
    }
}