package com.example.starter.core;

import java.io.IOException;
import java.net.URISyntaxException;
import com.example.starter.Util;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class JWT {
    public static final String algorithm = "RS256";

    public static JWTAuthOptions getSignatureOptions() throws URISyntaxException, IOException {
        String publicKey = Util.readFile("id_rsa.pub");
        String privateKey = Util.readFile("id_rsa");
        return new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm(algorithm)
                        .setBuffer(publicKey))
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm(algorithm)
                        .setBuffer(privateKey));
    }
}
