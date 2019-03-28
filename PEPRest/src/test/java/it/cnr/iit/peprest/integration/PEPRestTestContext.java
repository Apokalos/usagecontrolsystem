package it.cnr.iit.peprest.integration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tngtech.jgiven.integration.spring.EnableJGiven;

@Configuration
@EnableJGiven
@ComponentScan( basePackages = { "it.cnr.iit.peprest" } )
public class PEPRestTestContext {

}