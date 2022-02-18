
package com.blog.blogart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BlogApplication {
	
	//--------------------------------
	//Exemplo do video -> Hello World!
	@GetMapping("/hello")
	public String exemplo(){
		return "Hello World!";
	}
	
	//-------------------------------
	//Atividade Spring-boot 1 e 2
	
	@GetMapping("/atividade1")
	public String atividade1(){
		return "Persistência de crescimento e atenção aos destalhes. ";
	}
	
	@GetMapping("/atividade2")
	public String atividade2(){
		return "Revisar métodos no Java e rever os vídeos da criação do blog pessoal. ";
	}
	
	//----------------------------------
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
