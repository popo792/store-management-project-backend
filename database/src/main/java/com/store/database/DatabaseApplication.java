package com.store.database;

import com.store.database.repository.CustomerRepository;
import com.store.database.model.Customer;
import com.store.database.model.Employee;
import com.store.database.model.User;
import com.store.database.model.Order;
import com.store.database.model.Product;
import com.store.database.repository.EmployeeRepository;
import com.store.database.repository.OrderRepository;
import com.store.database.repository.ProductRepository;
import com.store.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.store.database.model")
@EnableJpaRepositories("com.store.database.repository")
public class DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedDatabase(CustomerRepository customerRepo,
			EmployeeRepository employeeRepo,
			ProductRepository productRepo,
			OrderRepository orderRepo,
			UserRepository userRepo) {
		return args -> {
			if (customerRepo.count() == 0) {
				Customer customer = customerRepo.save(new Customer("Alice Johnson", "alice@example.com"));
				User user =  userRepo.save(new User("Admin", "pass456", "Admin"));

				Employee manager = employeeRepo.save(new Employee("Maya Patel", "Manager", null));
				Employee cashier = employeeRepo.save(new Employee("Noah Smith", "Cashier", manager));

				Product laptop = productRepo.save(new Product("Laptop", 1200.00, 10));
				Product mouse = productRepo.save(new Product("Wireless Mouse", 35.00, 25));



				Order order = new Order(customer, cashier);
				order.addProduct(laptop);
				order.addProduct(mouse);
				orderRepo.save(order);

				productRepo.save(laptop);
				productRepo.save(mouse);
			}

			System.out.println("----------------------------------------");
			
			long currentCustomers = customerRepo.count();
			long currentEmployees = employeeRepo.count();
			long currentProducts = productRepo.count();
			long currentOrders = orderRepo.count();
			long currentUsers = userRepo.count();
			
			System.out.println("✅ Connected to MSSQL Database successfully!");
			System.out.println("✅ Hibernate created or updated the tables.");
			System.out.println("✅ Customers: " + currentCustomers);
			System.out.println("✅ Employees: " + currentEmployees);
			System.out.println("✅ Products: " + currentProducts);
			System.out.println("✅ Orders: " + currentOrders);
			System.out.println("✅ Users: " + currentUsers);
			System.out.println("✅ Open SSMS and refresh the sample database to see the tables and seed data.");
			System.out.println("----------------------------------------");
		};
	}
}