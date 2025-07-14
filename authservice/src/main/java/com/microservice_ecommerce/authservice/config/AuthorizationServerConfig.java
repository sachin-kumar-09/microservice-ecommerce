package com.microservice_ecommerce.authservice.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@Import({SecurityConfig.class})
public class AuthorizationServerConfig {

    /**
     * Configures the security filter chain specific to OAuth2 Authorization Server.
     *
     * Why use OAuth2AuthorizationServerConfigurer?
     * - It enables support for the OAuth2 protocol (token issuance, introspection, revocation, etc.)
     * - Automatically exposes endpoints: /oauth2/token, /oauth2/jwks, /oauth2/authorize, etc.
     *
     * Customizer vs Full Manual:
     * - Using `Customizer.withDefaults()` is sufficient here because we’re using standard endpoint behavior.
     */
    @Bean
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    /**
     * Creates and registers an in-memory OAuth2 client for testing and token generation.
     *
     * Why in-memory?
     * - Quick to set up for local development
     * - Can later be replaced by JDBCRegisteredClientRepository to persist in DB
     *
     * Alternatives:
     * - JDBC-based repository for persistent storage
     * - External identity providers (e.g., Keycloak) if going external
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("test-client")
//                .clientSecret("{noop}secret") // Plaintext for demo. Will replace with encoder in prod
                .clientSecret(passwordEncoder.encode("secret"))
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("read")
                .scope("write")
                .build();
        return new InMemoryRegisteredClientRepository(client);
    }

    /**
     * Generates a KeyPair (RSA) to sign JWTs dynamically at startup.
     *
     * Why use this approach?
     * - No external keystore dependency
     * - Public key exposed via `/oauth2/jwks` for resource servers to validate tokens
     *
     * Warning: New keys are generated on every restart. Tokens issued with old keys become invalid.
     * For persistence, you'd extract keys from file or env.
     */
    @Bean
    public JWKSet jwkSet() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();

        return new JWKSet(rsaKey);
    }

    /**
     * Specifies the server metadata settings like issuer URL.
     *
     * Why define AuthorizationServerSettings explicitly?
     * - Allows configuring issuer, endpoint paths, etc.
     *
     * Alternatives:
     * - Use default values (ok for dev)
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:9000") // Must match resource server validation config
                .build();
    }
}
