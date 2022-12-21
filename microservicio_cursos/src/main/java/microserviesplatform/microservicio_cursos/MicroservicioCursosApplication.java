package microserviesplatform.microservicio_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservicioCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioCursosApplication.class, args);
    }

}
