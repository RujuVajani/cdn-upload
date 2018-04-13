package za.co.pnp.cdnupload.uploadingfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class CDNUploadApplication {


	public static void main(String[] args) {

		new File(UploadingController.uploadingdir).mkdirs();
		SpringApplication.run(CDNUploadApplication.class, args);
	}
}

