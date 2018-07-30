
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gofluent"})
public class ElasticsearchPrototypeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchPrototypeApplication.class, args);
	}
}


