//package al.example;

//@Configuration
//public class SwaggerConfig {
	
//	@Value("${swagger.url}")
//	private String swaggerUrl;
//
//	@Bean
//	public OpenAPI customOpenAPI() {
//		final String securitySchemeName = "bearerAuth";
//		final String apiTitle = String.format("%s API", StringUtils.capitalize("Warehouse Management"));
//		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//				.components(
//						new Components().addSecuritySchemes(securitySchemeName,
//								new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
//										.scheme("bearer").bearerFormat("JWT")))
//				.info(new Info().title(apiTitle).version("v0.1.0").description("Warehouse Management API Documentation"))
//				.servers(Arrays.asList(new Server().url(swaggerUrl)));
//	}
//}