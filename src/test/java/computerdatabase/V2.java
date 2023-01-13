package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class V2 extends Simulation {

    ScenarioBuilder scn = scenario("AddAndFindPersons").exec(
            http("AddPerson-API")
                .post("/orchestrator/payments/app/v1/pump-reserve")
                .header("Content-Type", "application/json")
                .body(StringBody("{\"userStepPresent\":false,\"pumpNumber\":1,\"siteName\":\"testSime\","
                    + "\"siteId\":\"10208588\",\"preAuthAmount\":\"10.00\",\"currencyCode\":\"MYR\","
                    + "\"merchantIdentifier\":\"eghl123\",\"languageCode\":\"MS\","
                    + "\"cardIdentifier\":\"44d8ff7a7bd49c6d9d9c8742021fee968e78f304ce54d847707bb84c22d8c77d\","
                    + "\"customerInfo\":{\"identifierName\":\"customerId\",\"identifierValue\":\"374831221\",\"source\":\"INSTORE\"}}"))
                .check(status().is(200))
        );

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://localhost:8100")
            .header("X-CAP-ENTITY-ID","50139741")
            .header("X-CAP-API-AUTH-ORG-ID","50906");

    {
        setUp(
            scn.injectOpen(rampUsers(10).during(10))
            //scn.injectOpen(rampUsers(2).during(10))
        ).protocols(httpProtocol);
    }


}
