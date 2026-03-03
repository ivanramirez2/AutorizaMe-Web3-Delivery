package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


/**
 * Configuración de OpenAPI / Swagger para - Autorizame API.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Autorizame API")
                        .version("v1")
                        .description("API REST para la gestión de Clientes, Autorizados, Empresas, Repartidores y Pedidos con agregación y manejo de errores."));
    }
}
