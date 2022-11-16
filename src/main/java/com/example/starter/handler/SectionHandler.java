package com.example.starter.handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.Util;
import com.example.starter.dao.SectionDAO;
import com.example.starter.dto.Section;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
public class SectionHandler {
    private SectionHandler() {}
    private static final Logger _LOGGER = Logger.getLogger(SectionHandler.class.getName());
    public static void addSection(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject json = rc.body().asJsonObject();
                String name = json.getString("name");
                String description = json.getString("description");
                Section section = new Section(name, description);
                SectionDAO.addSection(section);
                Util.sendResponse(rc, 200, "successfully created Section");
            } catch (Exception e) {
                _LOGGER.log(Level.SEVERE, "add section handler failed", e);
                Util.sendResponse(rc, 500, e.getMessage());
            }
        }, false, null);
    }
    
}
