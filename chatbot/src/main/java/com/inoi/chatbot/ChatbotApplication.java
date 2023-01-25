package com.inoi.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.UUID;

@SpringBootApplication
@RestController
public class ChatbotApplication {

	@GetMapping("/generate")
	public String generateIds(@RequestParam(value = "phone")String phoneNumber) {
		UUID uuid = UUID.randomUUID();
		String deviceId = uuid.toString() + "-chatbot";
		KeyPairHelper helper = null;
		try {
			helper = new KeyPairHelper();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		//phoneNumber.deviceId.publicKey
		String messageToSign = phoneNumber + "." + deviceId + "." + helper.getEncodedPublicKey();

		String result = null;
		try {
			result = "publicKey = " + helper.getEncodedPublicKey() +
					"<br> privateKey =  " + helper.getEncodedPrivateKey() +
					"<br> chatbot device id = " + deviceId +
					"<br> signature = " + helper.sign(messageToSign);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

}
