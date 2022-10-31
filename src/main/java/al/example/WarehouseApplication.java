package al.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import al.example.service.UserService;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
					
//			userService.saveUser(new UserModel(null, "lkokona", "1234", "Laert", "Kokona", true, null));
//			System.err.println(userService.getUserByUsername("lkokona"));
			
		};
	}

}
