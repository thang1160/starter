package com.example.starter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;

public class Util {

    private static final Logger _LOGGER = Logger.getLogger(Util.class.getName());

    public static final String uploadsDirectory = "/opt/tms-upload/";

    private Util() {}

    public static List<Map<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    public static void sendResponse(RoutingContext rc, int statusCode, Object object) {
        try {
            String result = null;
            JsonObject jContent = null;
            String contentType = "application/json";
            if (object instanceof JsonObject) {
                jContent = (JsonObject) object;
                result = jContent.encode();
            } else if (object instanceof String) {
                contentType = "text/plain";
                result = (String) object;
            } else {
                // result = GSON.toJson(object);
                result = DatabindCodec.mapper().writeValueAsString(object);
            }

            rc.response().setStatusCode(statusCode)
                    .putHeader("Content-Type", contentType)
                    .end(result);
        } catch (Exception e) {
            _LOGGER.log(Level.SEVERE, "send response failed", e);
            rc.fail(e);
        }
    }

    public static void failureResponse(RoutingContext rc) {
        Throwable throwable = rc.failure();

        int statusCode = rc.statusCode();
        String message = throwable.getMessage();

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", statusCode);
        errorResponse.put("message", message);

        rc.response().setStatusCode(statusCode).end(new JsonObject(errorResponse).encode());
    }

    public static String hashPassword(String password, String salt) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password + salt);
    }

    public static String readFile(String fileName) throws URISyntaxException, IOException {
        java.nio.file.Path path = Paths.get(Util.class.getClassLoader()
                .getResource(fileName).toURI());
        String data = null;
        try (Stream<String> lines = Files.lines(path)) {
            data = lines.collect(Collectors.joining("\n"));
        }
        return data;
    }
}
