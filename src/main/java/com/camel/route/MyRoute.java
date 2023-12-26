package com.camel.route;



import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.camel.entity.Course;
import com.camel.service.CourseService;

@Component
@CrossOrigin
public class MyRoute extends RouteBuilder {

	private final Environment env;

	public MyRoute(Environment env) {
		this.env = env;
	}

	public void configure() throws Exception {

		restConfiguration().contextPath(env.getProperty("camel.component.servlet.mapping.contextPath", "/*"))
				.apiContextPath("/api-doc").apiProperty("api.title", "Spring Boot Camel Postgres Rest API.")
				.apiProperty("api.version", "1.0").apiProperty("cors", "true").apiContextRouteId("doc-api")
				.port(env.getProperty("server.port", "8080")).bindingMode(RestBindingMode.json). enableCORS(true)
			    .corsAllowCredentials(true);
		
		
		

		
		
		
		//controller for routes
		rest("/course")
        .consumes(MediaType.APPLICATION_JSON_VALUE)
        .produces(MediaType.APPLICATION_JSON_VALUE)
        .get("/{id}").route()
        .to("{{route.findById}}")
        .endRest()
        .get("/").route()
        .to("{{route.findAllCourse}}")
        .endRest()
        .post("/").route()
        .marshal().json()
        .unmarshal(getJacksonDataFormat(Course.class))
        .to("{{route.saveCourse}}")
        .endRest()
        .delete("/{Id}").route()
        .to("{{route.removeCourse}}")
        .end();

		from("{{route.findById}}")
         .log("Received header : ${header.id}")
         .bean(CourseService.class, "findById(${header.id})");

		from("{{route.findAllCourse}}")
         .bean(CourseService.class, "all");


		from("{{route.saveCourse}}")
         .log("Received Body ${body}")
         .bean(CourseService.class, "save(${body})");


		from("{{route.removeCourse}}")
         .log("Received header : ${header.Id}")
         .bean(CourseService.class, "delete(${header.Id})");
}

private JacksonDataFormat getJacksonDataFormat(Class<?> unmarshalType) {
JacksonDataFormat format = new JacksonDataFormat();
format.setUnmarshalType(unmarshalType);
return format;
}
		
		

	
}
