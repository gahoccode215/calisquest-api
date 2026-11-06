package com.calisquest.api;

import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.jsonwebtoken.Jwts;

@SpringBootApplication
public class CalisquestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalisquestApiApplication.class, args);
	}

}
