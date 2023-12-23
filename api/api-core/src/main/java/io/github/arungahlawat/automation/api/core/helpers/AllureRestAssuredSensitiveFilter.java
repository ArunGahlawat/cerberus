package io.github.arungahlawat.automation.api.core.helpers;

import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.FilterContext;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.qameta.allure.attachment.http.HttpRequestAttachment.Builder.create;
import static java.util.Optional.ofNullable;

public class AllureRestAssuredSensitiveFilter extends AllureRestAssured {
    public String requestTemplatePath = "http-request-sensitive.ftl";
    public String responseTemplatePath = "http-response-sensitive.ftl";
    public String responseAttachmentName;

    private static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
        final Map<String, String> result = new HashMap<>();
        items.forEach(h -> result.put(h.getName(), h.getValue()));
        return result;
    }

    private Map<String, String> toMapConverterForHeaders(final Iterable<? extends NameAndValue> items) {
        final Map<String, String> result = new HashMap<>();
        String[] blackListHeaders = new String[0];
        items.forEach(existingHeader -> {
            if (StringUtils.equalsAnyIgnoreCase(existingHeader.getName(), blackListHeaders)) {
                result.put(existingHeader.getName(), existingHeader.getValue().replaceAll("[\\w\\d]*", "*"));
            } else
                result.put(existingHeader.getName(), existingHeader.getValue());
        });
        return result;
    }

    @Override
    public Response filter(final FilterableRequestSpecification requestSpec,
                           final FilterableResponseSpecification responseSpec,
                           final FilterContext filterContext) {
        final Prettifier prettifier = new Prettifier();
        final String url = requestSpec.getURI();
        final String stepName = requestSpec.getMethod() + " : " + url;
        final HttpRequestAttachment.Builder requestAttachmentBuilder = create(stepName, url)
                .setMethod(requestSpec.getMethod())
                .setHeaders(toMapConverterForHeaders(requestSpec.getHeaders()))
                .setCookies(toMapConverter(requestSpec.getCookies()));

        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachmentBuilder.setBody(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }

        final HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();

        new DefaultAttachmentProcessor().addAttachment(
                requestAttachment,
                new FreemarkerAttachmentRenderer(requestTemplatePath)
        );

        final Response response = filterContext.next(requestSpec, responseSpec);

        final String attachmentName = ofNullable(responseAttachmentName)
                .orElse(response.getStatusLine());

        final HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder.create(attachmentName)
                .setResponseCode(response.getStatusCode())
                .setHeaders(toMapConverterForHeaders(response.getHeaders()))
                .setBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody()))
                .build();

        new DefaultAttachmentProcessor().addAttachment(
                responseAttachment,
                new FreemarkerAttachmentRenderer(responseTemplatePath)
        );

        return response;
    }
}
