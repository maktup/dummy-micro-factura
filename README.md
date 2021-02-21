

IMPORTANTE:
----------
* MICROSERVICIO Dummy para pruebas, que devuelve información básica de la FACTURA.


Los LINKs del 'MICROSERVICIO' son:
---------------------------------

  1. Las 'URI' de tipo [GET] son:
     ---------------------------
  
     - consultarFacturas []: 
	   http://localhost:8080/dummy-micro-factura/get/facturas
	   
	   
  Estos CONSULTARÁN los MicroServicios INTERNOS de:
  
  - my-cliente-service.default.svc.cluster.local:8080/dummy-micro-cliente/get/clientes/1
  - my-producto-service.default.svc.cluster.local:8080/dummy-micro-producto/get/productos/1
	  
  
DETALLE:
------- 
* Para INFORMACIÓN interna del MICROSERVICIO, apoyarse en ACTUATOR ingresando a: 'http://localhost:8080/actuator'


BANNERs:
-------
* http://www.patorjk.com/software/taag

