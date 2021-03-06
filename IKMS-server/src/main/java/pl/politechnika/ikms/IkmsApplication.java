package pl.politechnika.ikms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class IkmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IkmsApplication.class, args);
	}
}
