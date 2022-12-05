package com.example.starter.handler;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.entity.Users;
import com.example.starter.service.BaseService;
import com.example.starter.service.UsersService;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {
    private UserHandler() {}

    private static final Logger _LOGGER = Logger.getLogger(UserHandler.class.getName());
    private static final Random RANDOM = new Random();

    public static void findAll(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Users> response = UsersService.findAll();
                Util.sendResponse(rc, 200, response);
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "get project handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void create(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Users user = rc.body().asPojo(Users.class);
                // create passwordSalt string with 6 random characters a-z A-Z 0-9 using StringBuilder
                StringBuilder passwordSalt = new StringBuilder();
                for (int i = 0; i < 6; i++) {
                    int random = RANDOM.nextInt(62);
                    if (random < 10) {
                        passwordSalt.append(random);
                    } else if (random < 36) {
                        passwordSalt.append((char) (random + 55));
                    } else {
                        passwordSalt.append((char) (random + 61));
                    }
                }
                // Create hashed password
                String hashedPassword = Util.hashPassword(user.getPassword(), passwordSalt.toString());
                user.setPassword(hashedPassword);
                user.setSalt(passwordSalt.toString());
                BaseService.create(user);
                Util.sendResponse(rc, 200, "successfully created user");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "create user handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }

    public static void update(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Users user = rc.body().asPojo(Users.class);
                UsersService.update(user);
                Util.sendResponse(rc, 200, "successfully update user");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "update user handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
}
