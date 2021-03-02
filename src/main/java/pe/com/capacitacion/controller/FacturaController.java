package pe.com.capacitacion.controller;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.json.Json;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import pe.com.capacitacion.util.UtilWS;
 
/**
 * FacturaController
 * @author cguerra
 **/
 @Slf4j      //Autogenerar LOG4J. 
 @RestController
 @RequestMapping( "/dummy-micro-factura" )   //NO USAR: [server.servlet.context-path], 'BOOT-ADMIN' reconocera el 'ACTUATOR'.
 public class FacturaController{
 	 
	   private String vArchivoJson  = "{\"numero\": \"1\", \"fecha\": \"1\", \"clientes\":{\"nombre\": \"1\", \"direccion\": \"1\", \"dni\": \"1\"}, \"productos\": {\"producto\": [{\"cantidad\": 1, \"nombre\": \"1\", \"precio\": 1, \"importe\": 1}] }, \"totalPagar\": 1}";  
       private UtilWS objUtlWS      = null;
       
       //LOCALMENTE: 
	   private String vURLClientes  = "http://192.168.99.100:32556/dummy-micro-cliente/get/clientes/1";
	   private String vURLProductos = "http://192.168.99.100:30586/dummy-micro-producto/get/productos/1";
       
	   //KUBERNETES: 'SERVICE.NAMESPACE.SVC.CLUSTER.LOCAL:PUERTO/URI'
	   //private String vURLClientes  = "my-cliente-service.dummy-istio.svc.cluster.local:8080/dummy-micro-cliente/get/clientes/1";
	   //private String vURLProductos = "my-producto-service.dummy-istio.svc.cluster.local:8080/dummy-micro-producto/get/productos/1";
	   
	   private JsonReader       objReader        = null;
	   private JsonStructure    objJsonStructure = null;		       
       private RestTemplate     objTemplate      = null;
	   private JsonPatchBuilder objPatBuilder    = null; 
	   private JsonPatch        objPatch         = null;
	   private JsonStructure    objStruct_01     = null;
	   private JsonStructure    objStruct_02     = null;
	   private JSONObject       vCadenaJSON      = null;
	   
	   /**
	    * consultarFacturas	
	    * @return String 
	    **/
		@GetMapping( "/get/facturas" )
		public String  consultarFacturas(){
			   log.info( "'consultarFacturas'" );
 
			   this.objUtlWS    = new UtilWS();
			   this.objTemplate = new RestTemplate();
	 
			   //String vResClientesJSON = this.objTemplate.getForObject( "http://callme-service:8080/callme/ping", String.class);
    	       
			   String vResClientesJSON = this.objTemplate.getForObject( "http://my-cliente-service-ci:8080/dummy-micro-cliente/get/clientes/1", String.class );
		       log.info( "vResClientesJSON: " + vResClientesJSON );
 
		       String vResProductosJSON = this.objTemplate.getForObject( "http://my-producto-service-ci:8080/dummy-micro-producto/get/productos/1", String.class );
		       log.info( "vResProductosJSON: " + vResProductosJSON );
		       		       
		       try{
		    	   //----------------------------------------//
		    	   //------------- [JSON-POINTER] -----------//
		    	   //----------------------------------------//
		    	   
		    	   //Cargando CLIENTES: 
				   this.objReader        = Json.createReader( new StringReader( vResClientesJSON ) );
				   this.objJsonStructure = this.objReader.read(); 
                    
				   String vNombreCliente = this.objUtlWS.obtenerCampoJsonString( "/nombre", objJsonStructure );
				   log.info( "vNombreCliente: " + vNombreCliente );
				   				   
				   String vDireccionCliente = this.objUtlWS.obtenerCampoJsonString( "/direccion", objJsonStructure );
				   log.info( "vDireccionCliente: " + vDireccionCliente );
				   
				   String vDniCliente = this.objUtlWS.obtenerCampoJsonString( "/dni", objJsonStructure );
				   log.info( "vDniCliente: " + vDniCliente );
 				   
		    	   //Cargando PRODUCTOS: 
				   this.objReader        = Json.createReader( new StringReader( vResProductosJSON ) );
				   this.objJsonStructure = this.objReader.read(); 
				   
				   String vNombreProducto = this.objUtlWS.obtenerCampoJsonString( "/nombre", objJsonStructure );
				   log.info( "vNombreProducto: " + vNombreProducto );
				   
				   int vPrecioProducto = this.objUtlWS.obtenerCampoJsonInt( "/precio", objJsonStructure );
				   log.info( "vPrecioProducto: " + vPrecioProducto ); 
	 
				   int vCantidadProducto = this.objUtlWS.obtenerCampoJsonInt( "/cantidad", objJsonStructure );
				   log.info( "vCantidadProducto: " + vCantidadProducto ); 
				   
				   int vImporteProducto = this.objUtlWS.obtenerCampoJsonInt( "/importe", objJsonStructure );
				   log.info( "vImporteProducto: " + vImporteProducto ); 			   
		    	   
				   //----------------------------------------//
				   //-------------- [JSON-PATCH] ------------//
		    	   //----------------------------------------//	
				   Calendar         objCalendar    = GregorianCalendar.getInstance();
				   SimpleDateFormat objSDC         = new SimpleDateFormat( "dd/MMMMM/yyyy hh:mm:ss" ); 
				   String           vFechaActual   = objSDC.format( objCalendar.getTime() );
				   String           vNumeroFactura = "F-0010";
				   
			       this.objPatBuilder = Json.createPatchBuilder();  
			       this.objPatch      = this.objPatBuilder.add( "/fecha",      vFechaActual   )
			    		                                  .add( "/numero",     vNumeroFactura )
			    		                                  .add( "/clientes/nombre",    vNombreCliente    )
			                                              .add( "/clientes/direccion", vDireccionCliente ) 		        		    		   
			                                              .add( "/clientes/dni",       vDniCliente )
			                                              .add( "/productos/producto/0/precio",   vPrecioProducto   )
			                                              .add( "/productos/producto/0/cantidad", vCantidadProducto )
			                                              .add( "/productos/producto/0/nombre",   vNombreProducto   )
			                                              .add( "/productos/producto/0/importe",  vImporteProducto  )
			                                              .add( "/totalPagar", (vPrecioProducto * vCantidadProducto) ) 
                                                          .build(); 
			        
			       this.objReader    = Json.createReader( new StringReader( this.vArchivoJson ) ); 
			       this.objStruct_01 = this.objReader.read();
			       this.objStruct_02 = this.objPatch.apply( this.objStruct_01 );
				   
			       //Formatear Impresion: 
			       this.vCadenaJSON = new JSONObject( this.objStruct_02 + "" );  
			       log.info( this.vCadenaJSON.toString( 4 ) );
			        
			       this.vArchivoJson = this.vCadenaJSON.toString( 4 );			        
			       this.objReader.close();
			   }
		       catch( Exception e ){
		    	      e.printStackTrace();
		       }
  
			   return this.vArchivoJson; 
		} 
		
 }

 