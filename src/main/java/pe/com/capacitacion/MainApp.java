package pe.com.capacitacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
/**
 * MainApp
 * @author cguerra
 **/
 @SpringBootApplication
 public class MainApp{

	    public static final String PAQUETE_SWAGGER_SCAN = "pe.com.capacitacion.controller";
 
	   /**
	    * main 
	    * @param argumentos
	    **/
	    public static void main( String[] argumentos ){
		 	   SpringApplication.run( MainApp.class, argumentos );
	    }

 }

 