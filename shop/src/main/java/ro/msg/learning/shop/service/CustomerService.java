package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.service.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public boolean existsById(int id) {
        return customerRepository.existsById(id);
    }

    public Customer findCustomerById(int id) {
        if(customerRepository.existsById(id)) {
            return customerRepository.getReferenceById(id);
        }
        else throw new NotFoundException("Customer not found");
    }
}
