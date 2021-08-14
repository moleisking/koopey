package com.koopey.api.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  private static Logger LOGGER = Logger.getLogger(SecurityService.class.getName());

  @PostConstruct
  private void init() {

  }

  public void generatePrivateKey() {
    try {
      // Create a Key Pair Generator
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");

      // Initialize the Key Pair Generator
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
      keyGen.initialize(1024, random);

      // Generate the Pair of Keys
      KeyPair pair = keyGen.generateKeyPair();
      PrivateKey priv = pair.getPrivate();
      PublicKey pub = pair.getPublic();
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, ex.getMessage());
    }

  }
}
