

IMPORTANTE:
----------
* MICROSERVICIO Dummy para pruebas, que devuelve información básica de la FACTURA.


Los LINKs del 'MICROSERVICIO' son:
---------------------------------

  1. Las 'URI' de tipo [GET] son:
     ---------------------------
  
     - consultarFacturas [NODE-PORT]: 
	   $ curl http://localhost:8080/dummy-micro-factura/get/facturas
	   
     - consultarFacturas [CLUSTER-IP]: 
	   $ curl http://my-factura-service-ci:8080/dummy-micro-factura/get/facturas
	  
	   
  Estos 'CONSULTARÁN' los MicroServicios 'INTERNOS' de:
  
  - $ curl my-cliente-service-ci:8080/dummy-micro-cliente/get/clientes/1
  - $ curl my-producto-service-ci:8080/dummy-micro-producto/get/productos/1
	  
  
DETALLE:
------- 
* Para INFORMACIÓN interna del MICROSERVICIO, apoyarse en ACTUATOR ingresando a: 'http://localhost:8080/actuator'


BANNERs:
-------
* http://www.patorjk.com/software/taag

