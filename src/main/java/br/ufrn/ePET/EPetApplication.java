package br.ufrn.ePET;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufrn.ePET.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageConfig.class})
public class EPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(EPetApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("1234567890"));
	}

}
