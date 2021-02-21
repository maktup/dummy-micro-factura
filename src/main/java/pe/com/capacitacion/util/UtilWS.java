package pe.com.capacitacion.util;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonPointer;
import javax.json.JsonString;
import javax.json.JsonStructure;

/**
 * UtilWS
 * @author cguerra
 **/
 public class UtilWS{

        private JsonPointer objJsonPointer = null;
        private JsonString  objJsonString  = null; 
	    private JsonNumber  objJsonNumber  = null;
        
       /**
        * obtenerCampoJsonString 
        * @param  campoParam
        * @param  objJsonStructure
        * @return String 
        **/
	    public String obtenerCampoJsonString( String campoParam, JsonStructure objJsonStructure ) {
              
	 	       String vCampoJSONReturn = null;
		       this.objJsonPointer  = Json.createPointer( campoParam ); 
		       this.objJsonString   = (JsonString)this.objJsonPointer.getValue( objJsonStructure );
		       vCampoJSONReturn        = this.objJsonString.getString(); 
  
		       return vCampoJSONReturn;
	    }
		
       /**
        * obtenerCampoJsonInt 
        * @param  campoParam
        * @param  objJsonStructure
        * @return int 
        **/	
	    public int obtenerCampoJsonInt( String campoParam, JsonStructure objJsonStructure ) {
           
		       int vCampoJSONReturn = 0;
		       this.objJsonPointer  = Json.createPointer( campoParam ); 
		       this.objJsonNumber   = (JsonNumber)this.objJsonPointer.getValue( objJsonStructure );
		       vCampoJSONReturn     = this.objJsonNumber.intValue(); 

		       return vCampoJSONReturn;
	    }
	   
 }

 