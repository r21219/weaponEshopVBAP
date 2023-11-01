package cz.osu.weaponeshop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeaponEshopApplication {
//TODO
// I recommend:
// adding objectMapper
// adding swagger docs
//
    public static void main(String[] args) {
        SpringApplication.run(WeaponEshopApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
