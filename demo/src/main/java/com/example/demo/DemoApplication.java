package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.demo.function.ReadLine;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {		
		for(int i = 0;i < args.length;++i) {
			System.out.println(args[i]);
		}
		SpringApplication.run(DemoApplication.class, args);
	}
	
	/**
	 * Build bean with fileName and fileCount
	 * Two variables can be changed from command line
	 * @return
	 */
	@Bean
	public ReadLine readLine() {
		if(fileName.contains("/")||fileName.contains("\\")) {
			return new ReadLine(fileName,fileCount);
		}
		return new ReadLine();
	}
	
	@Value("${file.name}")
	private String fileName;
	
	@Value("${file.count}")
	private long fileCount;
}
