package it.aulab.progetto_blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProgettoBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoBlogApplication.class, args);
	}

	@Bean
	//handler per istanziare l'oggetto modelMapper che mi fa il mapping
	public ModelMapper istanceModelMapper(){
		ModelMapper mapper= new ModelMapper();
		// ModelMapper: converte automaticamente Entity in DTO.
		// Il filtraggio avviene nel Service come anche l'autowired del ModelMapper.
		// @Bean lo registra in Spring così può essere iniettato con @Autowired ovunque serva.
		return mapper;
	}

}
