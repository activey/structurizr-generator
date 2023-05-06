test = container "Test service" "" "" {
   SingleComponent = component "SingleComponent" {
   }

   group "Module1" {
      Component1 = component "Component1" {
      }

      Service1 = component "Service1" {
         tags Service
      }

   }

   group "Module2" {
      Service2 = component "Service2" {
         tags Service
      }

      group "infrastructure" {
         RestController2 = component "RestController2" {
         }

      }

   }

}

Service2 -> Service1 "" "" 
Service1 -> Component1 "Calls component" "JVM" "Asynchronous"
nginx -> RestController2 "Calls the controller" "HTTPS" "Asynchronous"
RestController2 -> Service2 "" "" 
