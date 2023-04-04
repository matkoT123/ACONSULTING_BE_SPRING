package sk.aconsulting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.aconsulting.models.Item;
import sk.aconsulting.service.ItemService;

@SpringBootApplication
public class AConsultingSpringZadanieApplication {

    public static void main(String[] args) {
        SpringApplication.run(AConsultingSpringZadanieApplication.class, args);
    }

}
