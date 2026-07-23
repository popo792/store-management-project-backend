package com.store.database.controller;

import com.store.database.model.Customer;
import com.store.database.model.Product;
import com.store.database.repository.CustomerRepository;
import com.store.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setStockQty(productDetails.getStockQty());
        
        return productRepository.save(product);
    }

    @PostMapping("/products/add")
    public Product addMasterProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/products/delete/{productId}")
    public void deleteMasterProduct(@PathVariable Integer productId) {
        productRepository.deleteById(productId);
    }

    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PostMapping("/{customerId}/cart/add/{productId}")
    public Customer addProductToCart(@PathVariable Integer customerId, @PathVariable Integer productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
                
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.decreaseStock(1);
        productRepository.save(product);

        customer.getVirtualCart().add(product);
        return customerRepository.save(customer);
    }

    @DeleteMapping("/{customerId}/cart/remove/{productId}")
    public Customer removeProductFromCart(@PathVariable Integer customerId, @PathVariable Integer productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQty(product.getStockQty() + 1);
        productRepository.save(product);

        customer.getVirtualCart().remove(product);
        return customerRepository.save(customer);
    }
}