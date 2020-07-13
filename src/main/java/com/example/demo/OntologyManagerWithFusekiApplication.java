package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OntologyManagerWithFusekiApplication {
	public static File file;
	public static OntModel model;
	public static InfModel infModel;
	public static Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	public static Dataset dataset ; 
	public static String ns = "http://www.semanticweb.org/hightech/ontologies/2019/8/untitled-ontology-4#";
	public static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static String owl = "http://www.w3.org/2002/07/owl#"; 
	
	public static String serviceURI = "http://localhost:3030/eduDataSet/";
	
	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			uploadRDF(new File("edu3Ontology.owl"), serviceURI );
		} catch (IOException e) {
			e.printStackTrace();
		}		
		SpringApplication.run(OntologyManagerWithFusekiApplication.class, args);
	}
	
	
	
	
	public static void uploadRDF(File rdf, String serviceURI)
			throws IOException {
			model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			model.setStrictMode(false);
			try(FileInputStream in = new FileInputStream(rdf)){
				model.read(in, null, "RDF/XML");
				reasoner = reasoner.bindSchema(model); 
				infModel = ModelFactory.createInfModel((Reasoner) reasoner, model);
			}

			DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
			accessor.putModel(infModel);
		}
	public static ResultSet execSelectAndProcess(String serviceURI, String queryString) {
		Query query = QueryFactory.create(queryString);
		query.getQueryPattern();
		QueryExecution qe = QueryExecutionFactory.create(query, infModel);
		ResultSet results = qe.execSelect();
		return results;
	}
	
	@Bean
	public Docket swaggerConfigration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.regex("/api/.*"))
				.apis(RequestHandlerSelectors.basePackage("com.example.demo"))
				.build()
				.apiInfo(apiDetails());
			
	}
		private ApiInfo apiDetails() {
			return new ApiInfo("OntologyManager API","API for ontology", "1.0", "Free to use", new springfox.documentation.service.Contact("EduEdge", "", "EduEdgeEmail"), "API license", "" , Collections.emptyList());
		}
	
		@Bean 
		public WebMvcConfigurer configure() {
				return new WebMvcConfigurer() {
					@Override
					public void addCorsMappings(CorsRegistry registry) {
						registry.addMapping("/**").allowedOrigins("*");						
					}	
				};	
		}
		
		
		
		

}


