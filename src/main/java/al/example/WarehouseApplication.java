package al.example;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import al.example.service.UserService;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
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

//	, "/users/*", "/trucks/*", "/items/*", "/orders/*"
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.paths(PathSelectors.ant("/"))
				.apis(RequestHandlerSelectors.basePackage("al.example.controller"))
				.build()
				.apiInfo(apiCustomData());
	}

	private ApiInfo apiCustomData() {
		return new ApiInfo("Warehouse Management API Application", "Warehouse Management Documentation", "v1.0.0",
				"Warehouse Management Service Terms",
				new Contact("Laert Kokona", "https://github.com/laertkokona", "laerti98@gmail.com"),
				"Warehouse Management License", "", Collections.emptyList());
	}

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

}
