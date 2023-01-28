package com.example.starter;

import static com.example.starter.Path.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.core.JWT;
import com.example.starter.handler.ActivityHandler;
import com.example.starter.handler.AuthenticationHandler;
import com.example.starter.handler.FileUploadHandler;
import com.example.starter.handler.MilestoneHandler;
import com.example.starter.handler.PriorityHandler;
import com.example.starter.handler.ProjectHandler;
import com.example.starter.handler.ProjectUserHandler;
import com.example.starter.handler.ReportHandler;
import com.example.starter.handler.ResultHandler;
import com.example.starter.handler.RoleHandler;
import com.example.starter.handler.SectionHandler;
import com.example.starter.handler.TestCaseHandler;
import com.example.starter.handler.TestRunHandler;
import com.example.starter.handler.UserHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
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
        router.route().handler(BodyHandler.create().setUploadsDirectory(Util.uploadsDirectory));
        router.route().failureHandler(Util::failureResponse);
        router.route().handler(routingContext -> {
            logger.log(Level.INFO, "header: {0}\nbody: {1}", new Object[] {routingContext.request().headers(), routingContext.body().asString()});
            routingContext.next();
        });

        JWTAuth jwt = JWTAuth.create(vertx, JWT.getSignatureOptions());

        router.post(PREFIX + LOGIN).handler(rc -> AuthenticationHandler.login(rc, jwt));
        router.post(PREFIX + SIGNUP).handler(AuthenticationHandler::signup);

        router.route().handler(JWTAuthHandler.create(jwt));

        router.get(PREFIX + LOGIN).handler(AuthenticationHandler::profile);

        // PROJECT
        router.get(PREFIX + PROJECT).handler(ProjectHandler::findAll);
        router.post(PREFIX + PROJECT).handler(ProjectHandler::create);
        // /tms/api/v1/project/:projectId...
        router.routeWithRegex("\\/tms\\/api\\/v1\\/project\\/(?<projectId>\\d+).*").handler(ProjectHandler::accessible);
        router.get(PREFIX + PROJECT + "/:projectId").handler(ProjectHandler::findByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + TEST_RUN).handler(TestRunHandler::findAllByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + TEST_CASE).handler(TestCaseHandler::findAllByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + ACTIVITY).handler(ActivityHandler::findAllByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + MILESTONE).handler(MilestoneHandler::findAllByProjectId);
        router.post(PREFIX + PROJECT + "/:projectId" + TEST_CASE).handler(TestCaseHandler::importTestCases);
        router.get(PREFIX + PROJECT + "/:projectId" + REPORT).handler(ReportHandler::findAllByProjectId);
        router.get(PREFIX + PROJECT + "/:projectId" + PROJECT_USER).handler(ProjectUserHandler::findAllByProjectId);

        // TEST CASE
        router.post(PREFIX + TEST_CASE).handler(TestCaseHandler::addTestCase);
        router.get(PREFIX + TEST_CASE + "/:testCaseId").handler(TestCaseHandler::findByTestCaseId);
        router.put(PREFIX + TEST_CASE).handler(TestCaseHandler::update);
        router.delete(PREFIX + TEST_CASE + "/:testCaseId").handler(TestCaseHandler::deleteByTestCaseId);

        // MILESTONE
        router.post(PREFIX + MILESTONE).handler(MilestoneHandler::create);
        router.get(PREFIX + MILESTONE + "/:milestoneId").handler(MilestoneHandler::findAllByMilestoneId);
        router.put(PREFIX + MILESTONE).handler(MilestoneHandler::update);
        router.get(PREFIX + MILESTONE + "/:milestoneId" + TEST_RUN).handler(TestRunHandler::findAllByMilestoneId);

        // TEST RUN
        router.post(PREFIX + TEST_RUN).handler(TestRunHandler::create);
        router.get(PREFIX + TEST_RUN + "/:testRunId").handler(TestRunHandler::findByTestRunId);
        router.put(PREFIX + TEST_RUN).handler(TestRunHandler::update);
        router.get(PREFIX + TEST_RUN + "/:testRunId" + RESULT).handler(ResultHandler::findAllByTestRunId);

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
        router.get(PREFIX + RESULT + "/:resultId").handler(ResultHandler::findByResultId);

        WebClient client = WebClient.create(vertx);

        // ATTACHMENT
        router.get(PREFIX + ATTACHMENT + "/:fileUploadId").handler(rc -> FileUploadHandler.findByFileUploadId(rc, client));

        // REPORT
        router.post(PREFIX + REPORT).handler(ReportHandler::create);
        router.get(PREFIX + REPORT + "/:reportId").handler(ReportHandler::findByReportId);

        // PROJECT USER
        router.post(PREFIX + PROJECT_USER).handler(ProjectUserHandler::create);
        router.delete(PREFIX + PROJECT_USER + "/:projectUserId").handler(ProjectUserHandler::delete);

        // Create the HTTP server
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .onSuccess(server -> logger.info("HTTP server started on port " + server.actualPort()));
    }
}
