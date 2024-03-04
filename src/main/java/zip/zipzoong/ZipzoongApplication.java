package zip.zipzoong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ZipzoongApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipzoongApplication.class, args);
	}

}
