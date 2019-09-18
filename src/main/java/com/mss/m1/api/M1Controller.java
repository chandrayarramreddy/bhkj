package com.mss.m1.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RefreshScope
public class M1Controller {
	
	@Autowired private RestTemplate restTemplate;
	
	 private M1Repository m1Repository; 
	
	@Value("${configString:Not found}")
	private String configString;
	

	
	@GetMapping("name/{id}")
	public String getNameById(@PathVariable (value="id") int id) {
		return m1Repository.findNameById(id);  
	}  
	
	@GetMapping("string/{name}")
	public String getString(@PathVariable (value="name") String name) {
		return "Hello "+name+". Welcome to "+configString;
	}
	
	@GetMapping("m2String/{name}")
	@HystrixCommand(fallbackMethod="m2Response")
	public String getM2Response(@PathVariable (value="name") String name) {
		return restTemplate.getForObject("https://MICROSERVICE-2/string/"+name, String.class);
	}
	
	public String m2Response(String name) {
		return "Hello "+name+ ". Microservice-2 is not responding. Pls try later";
	}

}
