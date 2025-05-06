package lockBox;

import lockBox.Service.impl.ImageProcessingImpl;
import lockBox.Service.impl.KohJao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import static lockBox.Utils.printMatrix.printMatrix;

@SpringBootApplication
public class ImageProcessingService {
    public static void main(String[] args) {
        SpringApplication.run(ImageProcessingService.class, args);
    }
}

