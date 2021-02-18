package tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void steup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void testDeveRetornarTarefas(){
        RestAssured
            .given()
            .when()
                .get("/todo")
            .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testDeveAdicionarTarefaComSucesso(){
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body("{\"task\": \"Teste via API\", \"dueDate\": \"2021-02-18\"}")
            .when()
                .post("/todo")
            .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testNaoDeveAdicionarTarefaInvalida(){
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .body("{\"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\"}")
            .when()
                .post("/todo")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}