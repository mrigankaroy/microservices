package com.mriganka.microservices.gateway_engine.web.controller;

import com.mongodb.MongoClient;
import com.mriganka.microservices.gateway_engine.dao.RouteMongoRepository;
import com.mriganka.microservices.gateway_engine.model.RouteEntity;
import com.mriganka.microservices.gateway_engine.service.ZuulRoutingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.*;
/**
 * Created by Mriganka Shekhar Roy on 2/19/2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = GatewayEngineController.class, secure = false)
public class GatewayEngineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZuulRoutingService zuulRoutingService;

    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private MongoClient mongoClient;

    @MockBean
    private RouteMongoRepository routeMongoRepository;

    @Test
    public void testCreateRoute()throws Exception  {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setProtocol("http://");
        Mockito.when(
                zuulRoutingService.addRoute(
                        Mockito.any(RouteEntity.class))).thenReturn(routeEntity);

        String requestJSON = "{  \"protocol\": \"http://\",  \"requestURI\": \"/config-server\",  \"routeKey\": null,  \"targetURIPath\": \"\",  \"targetURLHost\": \"localhost\",  \"targetURLPort\": 8081}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/gateway-engine/create")
                .accept(MediaType.APPLICATION_JSON).content(requestJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        System.out.println(response.getContentAsString());
    }


}
