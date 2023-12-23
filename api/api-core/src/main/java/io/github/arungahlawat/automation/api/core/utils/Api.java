package io.github.arungahlawat.automation.api.core.utils;

import com.github.dzieciou.testing.curl.Http2Curl;
import com.github.dzieciou.testing.curl.Options;
import io.github.arungahlawat.automation.api.core.enums.Url;
import io.github.arungahlawat.automation.api.core.helpers.AllureRestAssuredMaskedHeadersFilter;
import io.github.arungahlawat.automation.api.core.helpers.AllureRestAssuredSensitiveFilter;
import io.github.arungahlawat.automation.api.core.helpers.Jackson2ObjectMapperFactoryHelper;
import io.github.arungahlawat.automation.core.utils.DateTimeUtils;
import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.RandomUtils;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.impl.writers.JsonWriter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.*;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;

@Getter
public class Api {
    private static final Map<String, List<Map<Response, RequestSpecification>>> responseRequestMap = new LinkedHashMap<>();
    private final RequestSpecBuilder requestSpecBuilder;
    private RequestSpecification requestSpecification;

    public Api() {
        RestAssuredConfig defaultConfig = RestAssured.config()
                .connectionConfig(ConnectionConfig.connectionConfig())
                .redirect(redirectConfig())
                .failureConfig(FailureConfig.failureConfig())
                .headerConfig(HeaderConfig.headerConfig())
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))
                .logConfig(LogConfig.logConfig().blacklistDefaultSensitiveHeaders()
                        .and()
                        .blacklistHeader("key", "token")
                        .and()
                        .enableLoggingOfRequestAndResponseIfValidationFails())
                .multiPartConfig(MultiPartConfig.multiPartConfig())
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(new Jackson2ObjectMapperFactoryHelper()));
        this.requestSpecBuilder = new RequestSpecBuilder().setConfig(defaultConfig).addFilter(new AllureRestAssuredMaskedHeadersFilter(defaultConfig.getLogConfig()));
    }

    public static String logNetworkTraceOnFailure(String threadId, String testMethodName) {
        if (!responseRequestMap.containsKey(threadId))
            return "";
        List<Map<Response, RequestSpecification>> responseRequests = Api.responseRequestMap.get(threadId);
        String logsDirectory = FileUtils.transformFilePath(System.getProperty("user.dir") + "/../logs/network_trace/" + System.getProperty("BUILD_NUMBER", "0")) + File.separator;
        String logFileName = FileUtils.transformFilePath(StringUtils.isNotBlank(testMethodName) ? testMethodName.replace("io.github.arungahlawat.automation.api.", "").replaceAll("\\.", "/") + ".json" : DateTimeUtils.format(DateTimeUtils.getCurrentDateTime("+05:30"), "yyyy-MM-dd_HH:mm:ss") + "_" + threadId + ".json");

        List<Map<String, Object>> networkTrace = new ArrayList<>();
        int listSize = responseRequests.size();
        for (int index = 0; index < listSize; index++) {
            Map<Response, RequestSpecification> responseMap = responseRequests.get(index);
            for (Response response : responseMap.keySet()) {
                Map<String, Object> traceMap = new HashMap<>();
                RequestSpecificationImpl request = (RequestSpecificationImpl) responseMap.get(response);
                traceMap.put("request", getRequest(request));
                traceMap.put("response", getResponse(response, request));
                networkTrace.add(traceMap);
            }
        }
        return JsonWriter.getInstance().write(networkTrace, logsDirectory + logFileName, true);
    }

    public static String getRequest(RequestSpecificationImpl request) {
        if (request == null)
            return "";
        RequestBuilder requestBuilder = RequestBuilder.get();
        switch (io.restassured.http.Method.valueOf(request.getMethod())) {
            case PATCH:
                requestBuilder = RequestBuilder.patch();
                break;
            case PUT:
                requestBuilder = RequestBuilder.put();
                break;
            case POST:
                requestBuilder = RequestBuilder.post();
                break;
            case DELETE:
                requestBuilder = RequestBuilder.delete();
                break;
            case OPTIONS:
                requestBuilder = RequestBuilder.options();
                break;
        }
        boolean isSensitive = false;
        for (Filter filter : request.getDefinedFilters()) {
            if (filter.getClass().equals(AllureRestAssuredSensitiveFilter.class)) {
                isSensitive = true;
                break;
            }
        }
        if (!isSensitive) {
            if (request.getBody() != null)
                requestBuilder.setEntity(new StringEntity(request.getBody().toString(), org.apache.http.entity.ContentType.parse(request.getContentType().split(";")[0])));
            String[] blackListHeaders = request.getConfig().getLogConfig().blacklistedHeaders().toArray(new String[0]);
            for (Header header : request.getHeaders()) {
                requestBuilder.addHeader(
                        header.getName(),
                        StringUtils.equalsAnyIgnoreCase(header.getName(), blackListHeaders)
                                ? "************"
                                : header.getValue());
            }
        } else {
            requestBuilder.setEntity(new StringEntity("************", org.apache.http.entity.ContentType.TEXT_PLAIN));
            requestBuilder.addHeader("Content-Type", org.apache.http.entity.ContentType.TEXT_PLAIN.toString());
        }
        requestBuilder.setUri(request.getURI());
        HttpRequest httpRequest = requestBuilder.build();
        Options options = Options.builder()
                .printSingleliner()
                .useLongForm()
                .escapeNonAscii()
                .build();
        Http2Curl http2Curl = new Http2Curl(options);
        try {
            return http2Curl.generateCurl(httpRequest);
        } catch (Exception e) {
            Log.error("Could not generate curl", e);
            return "";
        }
    }

    public static Map<String, Object> getResponse(Response response, RequestSpecificationImpl request) {
        Map<String, Object> responseMap = new HashMap<>();
        if (response == null) {
            return null;
        }
        responseMap.put("statusCode", String.valueOf(response.statusCode()));
        boolean isSensitive = false;
        for (Filter filter : request.getDefinedFilters()) {
            if (filter.getClass().equals(AllureRestAssuredSensitiveFilter.class)) {
                isSensitive = true;
                break;
            }
        }
        if (!isSensitive) {
            try {
                responseMap.put("body", response.asString());
            } catch (Exception e) {
                Log.warn(e.getMessage());
            }
            String[] blackListHeaders = request.getConfig().getLogConfig().blacklistedHeaders().toArray(new String[0]);
            List<String> responseHeaders = new ArrayList<>();
            for (Header header : response.getHeaders()) {
                responseHeaders.add(
                        header.getName() + " : " +
                                (StringUtils.equalsAnyIgnoreCase(header.getName(), blackListHeaders)
                                        ? "************"
                                        : header.getValue()));
            }
            responseMap.put("headers", responseHeaders);
        } else {
            responseMap.put("body", "************");
        }
        return responseMap;
    }

    public static void request(RequestSpecificationImpl request) {
        Log.info("Request :");
        Log.info("{}", getRequest(request));
    }

    public static void request(Response response) {
        for (List<Map<Response, RequestSpecification>> responseRequestList : Api.responseRequestMap.values()) {
            for (Map<Response, RequestSpecification> responseRequest : responseRequestList) {
                if (responseRequest.containsKey(response))
                    request((RequestSpecificationImpl) responseRequest.get(response));
            }
        }
    }

    private static String getRequest(Response response, String threadId) {
        if (!Api.responseRequestMap.containsKey(threadId))
            return null;
        List<Map<Response, RequestSpecification>> responseRequests = Api.responseRequestMap.get(threadId);
        int listSize = responseRequests.size();
        for (int index = 0; index < listSize; index++) {
            Map<Response, RequestSpecification> responseMap = responseRequests.get(index);
            if (responseMap.containsKey(response))
                return getRequest((RequestSpecificationImpl) responseMap.get(response));
        }
        return null;
    }

    public Headers getHeaders() {
        return ((RequestSpecificationImpl) this.requestSpecification).getHeaders();
    }

    public ContentType getContentType() {
        if (getHeaders().exist() && getHeaders().hasHeaderWithName("Content-Type"))
            return ContentType.fromContentType(((RequestSpecificationImpl) this.requestSpecification).getContentType());
        return null;
    }

    public ExtractableResponse<Response> extract(Response response) {
        return response.then().extract();
    }

    public Api addQueryParams(String name, Object value) {
        this.requestSpecBuilder.addQueryParam(name, value);
        return this;
    }

    public Api addQueryParams(String name, Object[] values) {
        this.requestSpecBuilder.addQueryParam(name, values);
        return this;
    }

    public Api addQueryParams(Map<String, List<Object>> queryParams) {
        this.requestSpecBuilder.addQueryParams(queryParams);
        return this;
    }

    public Api addPathParams(String name, Object value) {
        this.requestSpecBuilder.addPathParam(name, value);
        return this;
    }

    public Api addPathParams(Map<String, Object> pathParams) {
        this.requestSpecBuilder.addPathParams(pathParams);
        return this;
    }

    public Api addHeaders(String name, String value) {
        this.requestSpecBuilder.addHeader(name, value);
        return this;
    }

    public Api addHeaders(Map<String, String> headers) {
        this.requestSpecBuilder.addHeaders(headers);
        return this;
    }

    public Api setBaseUri(String uri) {
        this.requestSpecBuilder.setBaseUri(uri);
        return this;
    }

    public Api setJsonBody(String jsonBody) {
        this.requestSpecBuilder.setBody(jsonBody);
        return this;
    }

    public Api setJsonBody(Object jsonObject) {
        this.requestSpecBuilder.setBody(jsonObject);
        return this;
    }

    public Api addMultipart(Object jsonObject) {
        Map<String, Object> valueMap = JsonUtils.convertFromObjectToMap(jsonObject);
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            if (entry.getValue() instanceof List) {
                for (Object obj : (List<?>) entry.getValue()) {
                    this.requestSpecBuilder.addMultiPart(new MultiPartSpecBuilder(obj)
                            .with()
                            .controlName(entry.getKey())
                            .and()
                            .emptyFileName()
                            .build());
                }
            } else
                this.requestSpecBuilder.addMultiPart(new MultiPartSpecBuilder(entry.getValue())
                        .with()
                        .controlName(entry.getKey())
                        .and()
                        .emptyFileName()
                        .build());
        }
        return this;
    }

    public Api addMultipart(String fileParamName, Object filePaths, String mimeType) {
        List<?> path;
        if (!(filePaths instanceof List)) {
            path = Collections.singletonList(filePaths);
        } else
            path = (List<?>) filePaths;
        for (Object currentPath : path) {
            String filePath = FileUtils.transformFilePath(String.valueOf(currentPath));
            File file;
            try {
                file = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filePath)).toURI());
                this.requestSpecBuilder.addMultiPart(new MultiPartSpecBuilder(file)
                        .with()
                        .controlName(fileParamName)
                        .and()
                        .fileName(file.getName())
                        .and()
                        .mimeType(mimeType)
                        .build());
            } catch (Exception e) {
                try {
                    file = new File(filePath);
                    this.requestSpecBuilder.addMultiPart(new MultiPartSpecBuilder(file)
                            .with()
                            .controlName(fileParamName)
                            .and()
                            .fileName(file.getName())
                            .and()
                            .mimeType(mimeType)
                            .build());
                } catch (Exception ex) {
                    Log.error("Error reading file. {}", e.getMessage());
                }
            }
        }
        return this;
    }

    public Response send(Url url) {
        this.requestSpecification = this.requestSpecBuilder.build();
        this.requestSpecification = Log.isDebugEnabled() ? this.requestSpecification.log().all() : this.requestSpecification;
        this.requestSpecification = Log.isDebugEnabled() ? this.requestSpecification.filter(new ResponseLoggingFilter(LogDetail.ALL, true, System.out)).request() : this.requestSpecification;
        if (this.getContentType() == null)
            this.requestSpecification.contentType(ContentType.JSON);
        return send(this.requestSpecification, url);
    }

    public Response send(RequestSpecBuilder requestSpecBuilder, Url url) {
        return send(requestSpecBuilder.build(), url);
    }

    public Response send(RequestSpecification requestSpecification, Url url) {
        if (url.isSensitive()) {
            requestSpecification = requestSpecification.noFiltersOfType(AllureRestAssuredMaskedHeadersFilter.class);
            requestSpecification = requestSpecification.noFiltersOfType(AllureRestAssured.class);
            requestSpecification = requestSpecification.noFiltersOfType(RequestLoggingFilter.class);
            requestSpecification = requestSpecification.noFiltersOfType(ResponseLoggingFilter.class);
            requestSpecification = requestSpecification.filter(new AllureRestAssuredSensitiveFilter());
            requestSpecification = requestSpecification.filter(new RequestLoggingFilter(LogDetail.URI));
            requestSpecification = requestSpecification.filter(new ResponseLoggingFilter(LogDetail.STATUS));
        }
        if (Thread.currentThread().getName().startsWith("TestNG") || Thread.currentThread().getName().startsWith("main"))
            Thread.currentThread().setName(RandomUtils.getUUID().toString());
        RequestSpecification given = given().spec(requestSpecification);
        Response response = switch (url.getMethod()) {
            case POST -> given.post(url.getUrl());
            case PUT -> given.put(url.getUrl());
            case DELETE -> given.delete(url.getUrl());
            case OPTIONS -> given.options(url.getUrl());
            case HEAD -> given.head(url.getUrl());
            case PATCH -> given.patch(url.getUrl());
            default -> given.get(url.getUrl());
        };
        if (responseRequestMap.containsKey(Thread.currentThread().getName()))
            responseRequestMap.get(Thread.currentThread().getName()).add(Collections.singletonMap(response, given));
        else {
            Map<Response, RequestSpecification> responseRequest = new LinkedHashMap<>();
            responseRequest.put(response, given);
            responseRequestMap.put(Thread.currentThread().getName(), new ArrayList<>(Collections.singletonList(responseRequest)));
        }
        return response;
    }
}
