package com.example.springboot.camel.cxf;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OrderRoute extends RouteBuilder {

    @Bean(name = "jsonProvider")
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

    @Override
    public void configure() throws Exception {

    	from("cxfrs:bean:cxfEndpoint?bindingStyle=SimpleConsumer")
            // call the route based on the operation invoked on the REST web service
            .toD("direct:${header.operationName}");

        // routes that implement the REST services
        from("direct:createOrder")
            .bean("orderService", "createOrder");

        from("direct:getOrder")
            .bean("orderService", "getOrder(${header.id})");

        from("direct:updateOrder")
            .bean("orderService", "updateOrder");

        from("direct:cancelOrder")
            .bean("orderService", "cancelOrder(${header.id})");
    }
}