
import com.gofluent.elasticsearch.infra.ElasticSearchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = ElasticSearchConfig.class)
public class ElasticsearchPrototypeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchPrototypeApplication.class, args);
	}
}


