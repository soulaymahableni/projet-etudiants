package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EtudiantSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(System.getProperty("gatling.baseUrl", "http://localhost:8080"))
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/Stress-Test");

    ScenarioBuilder listEtudiantsScenario = scenario("Liste des etudiants")
            .exec(
                    http("GET /api/etudiants")
                            .get("/api/etudiants")
                            .check(status().is(200))
            )
            .pause(Duration.ofMillis(500))
            .exec(
                    http("GET /api/departements")
                            .get("/api/departements")
                            .check(status().is(200))
            );

    {
        setUp(
                listEtudiantsScenario.injectOpen(
                        rampUsers(50).during(Duration.ofSeconds(30))
                )
        ).protocols(httpProtocol)
         .assertions(
                 global().responseTime().percentile3().lt(2000),  // p95 < 2s
                 global().successfulRequests().percent().gt(95.0)
         );
    }
}
