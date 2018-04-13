package za.co.pnp.cdnupload.uploadingfiles;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadingController {
    private static String bucketName     = "pnp-cdn-test";
    //private static String prod_bucketName     = "pnp-cdn-prod";
    private static String keyName        = "test";
    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";
    private static String error_message ;

    @RequestMapping("/")
    public String uploading(Model model) {
        File file = new File(uploadingdir);
        model.addAttribute("prefix" , keyName);
        model.addAttribute("files", file.listFiles());
        try {
            FileUtils.cleanDirectory(file);
        }
        catch(IOException io){
            error_message = "Something is wrong while deleting local files";

        }
        if(error_message != null) {
            model.addAttribute("error", error_message);
            error_message = "";
        }
        else {
            model.addAttribute("error", "");
        }

        return "uploading";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,  @RequestParam("prefix") String prefix) throws IOException {
        //File uploadFolder = new File(uploadingdir);

        bucketName = System.getenv("CDN_BUCKET");
        for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
            try{
            uploadedFile.transferTo(file);
            keyName = prefix;
            //System.out.println("Env variable "+System.getenv("AWS_ACCESS_KEY")+"\n"+System.getenv("AWS_SECRET_KEY"));
            AWSCredentials credentials = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY"), System.getenv("AWS_SECRET_KEY"));
            AmazonS3 s3client = new AmazonS3Client(credentials);

                System.out.println("Uploading a new object to S3 from a file\n" );
                s3client.putObject(new PutObjectRequest(
                            bucketName, prefix + "/" + uploadedFile.getOriginalFilename(), file));
                //sync cdn-test and cdn-prod
                //s3client.copyObject(bucketName, prefix+ "/" + uploadedFile.getOriginalFilename() ,prod_bucketName, prefix+ "/" + uploadedFile.getOriginalFilename());


            } catch (Exception ase) {
                System.out.println("Caught an AmazonServiceException, which " +
                        "means your request made it " +
                        "to Amazon S3, but was rejected with an error response" +
                        " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                error_message = "Something is wrong while uploading or copying";
                FileUtils.cleanDirectory(new File(uploadingdir));

            }
        }


        return "redirect:/";
    }


}