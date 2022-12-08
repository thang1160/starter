package com.example.starter;

import static com.example.starter.Path.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.core.JWT;
import com.example.starter.handler.AuthenticationHandler;
import com.example.starter.handler.MilestoneHandler;
import com.example.starter.handler.PriorityHandler;
import com.example.starter.handler.ProjectHandler;
import com.example.starter.handler.ResultHandler;
import com.example.starter.handler.RoleHandler;
import com.example.starter.handler.SectionHandler;
import com.example.starter.handler.TestCaseHandler;
import com.example.starter.handler.TestRunHandler;
import com.example.starter.handler.UserHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.Cookie;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

public class MainVerticle extends AbstractVerticle {
    private static Logger logger = Logger.getLogger(MainVerticle.class.getName());
    public static final String JSON = "application/json";
    private static final String PREFIX = "/tms/api/v1";

    @Override
    public void start() throws Exception {
        // Register Java 8 DateTime/LocalDateTime modules
        ObjectMapper mapper = DatabindCodec.mapper();
        mapper.registerModule(new JavaTimeModule());

        ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
        prettyMapper.registerModule(new JavaTimeModule());

        // Create a Router
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().failureHandler(Util::failureResponse);
        router.route().handler(routingContext -> {
            logger.log(Level.INFO, "header: {0}\nbody: {1}", new Object[] {routingContext.request().headers(), routingContext.body().asString()});
            routingContext.next();
        });

        JWTAuth jwt = JWTAuth.create(vertx, JWT.getSignatureOptions());

        router.post(PREFIX + LOGIN).handler(rc -> AuthenticationHandler.login(rc, jwt));

        router.delete(PREFIX + LOGOUT).handler(rc -> {
            Cookie cookie = rc.request().getCookie("token");
            if (cookie != null)
                cookie.setMaxAge(0);
            rc.response().setStatusCode(204).end();
        });

        router.route().handler(JWTAuthHandler.create(jwt));

        router.get(PREFIX + LOGIN).handler(AuthenticationHandler::profile);

        // PROJECT
        router.get(PREFIX + PROJECT).handler(ProjectHandler::findAll);
        router.post(PREFIX + PROJECT).handler(ProjectHandler::create);
        router.get(PREFIX + PROJECT + "/:projectId").handler(ProjectHandler::findByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + TEST_RUN).handler(TestRunHandler::findAllByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + TEST_CASE).handler(TestCaseHandler::findAllByProjectId);

        // TEST CASE
        router.post(PREFIX + TEST_CASE).handler(TestCaseHandler::addTestCase);
        router.get(PREFIX + TEST_CASE + "/:testCaseId").handler(TestCaseHandler::findByTestCaseId);
        router.put(PREFIX + TEST_CASE).handler(TestCaseHandler::update);

        // MILESTONE
        router.post(PREFIX + MILESTONE).handler(MilestoneHandler::create);
        router.get(PREFIX + MILESTONE + "/:projectId").handler(MilestoneHandler::findAllByProjectId);

        // TEST RUN
        router.post(PREFIX + TEST_RUN).handler(TestRunHandler::create);
        router.get(PREFIX + TEST_RUN + "/:testRunId").handler(TestRunHandler::findByTestRunId);
        router.put(PREFIX + TEST_RUN).handler(TestRunHandler::update);

        // SECTION
        router.get(PREFIX + SECTION + "/:projectId").handler(SectionHandler::findAllByProjectId);
        router.post(PREFIX + SECTION).handler(SectionHandler::create);

        // PRIORITY
        router.get(PREFIX + PRIORITY).handler(PriorityHandler::findAll);

        // USER
        router.get(PREFIX + USER).handler(UserHandler::findAll);
        router.post(PREFIX + USER).handler(UserHandler::create);
        router.put(PREFIX + USER).handler(UserHandler::update);

        // ROLE
        router.get(PREFIX + ROLE).handler(RoleHandler::findAll);

        // RESULT
        router.put(PREFIX + RESULT).handler(ResultHandler::update);
        router.get(PREFIX + RESULT + "/:testRunId").handler(ResultHandler::findAllByTestRunId);

        // Create the HTTP server
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .onSuccess(server -> logger.info("HTTP server started on port " + server.actualPort()));
    }
}
