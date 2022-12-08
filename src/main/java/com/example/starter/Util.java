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
import com.example.starter.core.Exclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;

public class Util {

    private static final Logger _LOGGER = Logger.getLogger(Util.class.getName());

    private Util() {}

    private static final ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(Exclude.class) != null;
        }
    };

    private static final GsonBuilder b = new GsonBuilder().addSerializationExclusionStrategy(strategy);

    public static final Gson GSON = b.create();

    // returns the ObjectMapper used by Vert.x
    public static final ObjectMapper mapper = DatabindCodec.mapper().registerModule(new JavaTimeModule());

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
                result = mapper.writeValueAsString(object);
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
        String json = GSON.toJson(errorResponse);

        rc.response().setStatusCode(statusCode).end(new JsonObject(json).encode());
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
