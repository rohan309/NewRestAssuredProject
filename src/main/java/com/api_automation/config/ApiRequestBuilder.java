package com.api_automation.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class ApiRequestBuilder {
    public RequestSpecification requestSpecification = given();
    private static ApiRequestBuilder apiRequestBuilder;
    public Response response;
    private String pathParam;

    public static ApiRequestBuilder getInstance() {
        if (Objects.isNull(apiRequestBuilder)) {
            apiRequestBuilder = new ApiRequestBuilder();
        }
        return apiRequestBuilder;
    }

    public void setRequestConfig() {
        PropertyHandler propertyHandler = new PropertyHandler("config.properties");
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String baseUri = propertyHandler.getProperty("baseUri");

        requestSpecification = requestSpecBuilder.setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", propertyHandler.getProperty("token"))
                .setBasePath(propertyHandler.getProperty("basePath"))
                .build();
    }

    public void execute(Method method, String endPoint) {
        RequestSpecification reqSpec = given().log().all();
        switch (method) {
            case GET:
                response = Objects.isNull(pathParam)
                        ? reqSpec.spec(requestSpecification).get(endPoint)
                        : reqSpec.spec(requestSpecification).get(endPoint + "/" + "{pathParam}");
                break;
            case POST:
                response = Objects.isNull(pathParam)
                        ? reqSpec.spec(requestSpecification).post(endPoint)
                        : reqSpec.spec(requestSpecification).post(endPoint + "/" + "{pathParam}");
                break;
            case PUT:
                response = Objects.isNull(pathParam)
                        ? reqSpec.spec(requestSpecification).put(endPoint)
                        : reqSpec.spec(requestSpecification).patch(endPoint + "/" + "{pathParam}");
                break;
            case PATCH:
                response = Objects.isNull(pathParam)
                        ? reqSpec.spec(requestSpecification).patch(endPoint)
                        : reqSpec.spec(requestSpecification).patch(endPoint + "/" + "{pathParam}");
                break;
            case DELETE:
                response = Objects.isNull(pathParam)
                        ? reqSpec.spec(requestSpecification).delete(endPoint)
                        : reqSpec.spec(requestSpecification).delete(endPoint + "/" + "{pathParam}");
                break;


        }
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        Optional.ofNullable(queryParams).ifPresent(params -> requestSpecification.queryParams(params));
    }

    public void setPathParam(String param) {
        Optional.ofNullable(param).ifPresent(p -> {
            pathParam = p;
            requestSpecification.pathParam("pathParam", p);
        });

    }

    public <T> void setRequestBody(T classObject) {
        Optional.ofNullable(classObject).ifPresent(obj -> requestSpecification.body(obj));
    }

    public JSONObject setRequestBodyWithFile(String filePath) {
        JSONObject jsonObject = null;
        if (Objects.isNull(filePath) && !filePath.isEmpty()) {
            JSONParser jsonParser = new JSONParser();
            FileReader reader;
            byte[] payload;
            try {
                reader = new FileReader(filePath);
                Object object = jsonParser.parse(reader);
                jsonObject = (JSONObject) object;
                payload = Files.readAllBytes(Path.of(filePath));
                requestSpecification.body(payload);
            } catch (IOException | ParseException exception) {
                throw new RuntimeException(exception);
            }
        }
        return jsonObject;
    }

    public <T> void setPostRequest(T clazz, String endPoint) {
        setRequestConfig();
        setRequestBody(clazz);
        execute(Method.POST, endPoint);
    }

    public <T> void setRequestWithQueryParam(Map<String, Object> queryParam, String endPoint) {
        setRequestConfig();
        setQueryParams(queryParam);
        execute(Method.GET, endPoint);
    }

    public void getRequestWithPathParam(String pathParam, String endPoit) {
        setRequestConfig();
        setPathParam(pathParam);
        execute(Method.GET, endPoit);
    }

    public void getRequestWithOutPathParam(String endPoit) {
        setRequestConfig();
        execute(Method.GET, endPoit);
    }

    public <T> void setPutRequest(T clazz, String endPoint) {
        setRequestConfig();
        setRequestBody(clazz);
        execute(Method.PUT, endPoint);
    }

    public <T> void setPatchRequest(T clazz, String endPoint) {
        setRequestConfig();
        setRequestBody(clazz);
        execute(Method.PATCH, endPoint);
    }

    public void setDeleteRequest(String endPoint) {
        setRequestConfig();
        execute(Method.DELETE, endPoint);
    }

    public void setDeleteRequestWithPathParam(String pathParam, String endPoint) {
        setRequestConfig();
        setPathParam(pathParam);
        execute(Method.DELETE, endPoint);
    }

}
