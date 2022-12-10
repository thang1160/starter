package com.example.starter.handler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.core.JWT;
import com.example.starter.entity.Functionality;
import com.example.starter.entity.Users;
import com.example.starter.service.BaseService;
import com.example.starter.service.FunctionalityService;
import com.example.starter.service.UsersService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;

public class AuthenticationHandler {
    private AuthenticationHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(AuthenticationHandler.class.getName());

    public static void login(RoutingContext rc, JWTAuth jwt) {
        rc.vertx().executeBlocking(blockingCodeHandler -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String email = json.getString("email");
                String password = json.getString("password");
                if (email == null || password == null) {
                    rc.response().setStatusCode(400).end();
                    return;
                }
                int accountId = UsersService.getUserIdByEmailAndPassword(email, password);
                if (accountId == 0) {
                    rc.response().setStatusCode(401).end();
                    return;
                }
                rc.response().end(jwt.generateToken(
                        new JsonObject().put("sub", accountId).put("email", email),
                        new JWTOptions().setAlgorithm(JWT.algorithm).setExpiresInMinutes(120)));
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "login failed: ", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void profile(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Long userId = Long.parseLong(rc.user().principal().getString("sub"));
                CompletableFuture<Users> userFuture = CompletableFuture.supplyAsync(() -> BaseService.findById(Users.class, userId));
                CompletableFuture<List<Functionality>> functionalitiesFuture = CompletableFuture.supplyAsync(() -> FunctionalityService.findByUserId(userId));
                CompletableFuture.allOf(userFuture, functionalitiesFuture).get();
                Users user = userFuture.get();
                user.setFunctionalities(functionalitiesFuture.get());
                Util.sendResponse(rc, 200, user);
            } catch (Exception e) {
                _LOGGER.severe(e.getMessage());
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
