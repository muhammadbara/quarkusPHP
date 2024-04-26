package org.belajar.resourceTest.event;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.belajar.api.resources.event.CategoryResource;
import org.belajar.api.services.event.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(CategoryResource.class)
public class CategoryResourceTest {

    @InjectSpy
    CategoryService categoryService;

    @Test
    void getAllCategoryTestEndPoint_willSuccess() {
        given()
                .when().get()
                .then().statusCode(200);
        Mockito.verify(categoryService, Mockito.times(1)).getAll();
    }

    @Test
    void getAllCategoryTestEndPoint_willFailed() {
        given()
                .when().get("/category")
                .then().statusCode(405);
        Mockito.verify(categoryService, Mockito.never()).getAll();
    }   
}