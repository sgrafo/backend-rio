package bo.gob.aj.rio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableScheduling

public class RioApplication {

	public static String CARPETA_ARCHIVOS_RIO;

	@Value("${rio.filepath}")
	public void setCarpetaArchivoRio(String pCarpetaArchivos) {
		CARPETA_ARCHIVOS_RIO = pCarpetaArchivos;
	}

	public static void main(String[] args) {
		SpringApplication.run(RioApplication.class, args);
	}
}
