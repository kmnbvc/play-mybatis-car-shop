### Run
You can start server by executing following command:

`sbt clean run`

By default server binds to `localhost:9000` address.

Or you can run tests in similar way: 

`sbt clean test`

### Domain entities
* Brand -- _manufacturer brand_
* Model -- _car model_
* Position -- _position of the concrete car in the shop_

### REST endpoints
* /v1/brands[/:id]
* /v1/models[/:id]
* /v1/positions[/:id]
* /v1/search[?model=x&brand=y&priceMin=0&priceMax=10]

### Methods
* GET -- _get entity by id, if present, or list all entities_
  
`curl -X GET localhost:9000/v1/models/1`

`curl -X GET localhost:9000/v1/models`

* POST -- _create entity as specified in body_
  
`curl -X POST -H "Content-Type: application/json" localhost:9000/v1/positions -d "{...}"`

* PUT -- _replace entity with specified id by supplied one_

`curl -X PUT -H "Content-Type: application/json" localhost:9000/v1/positions/uuid -d "{...}"`

* DELETE -- _delete entity with specified id_ 
  
`curl -X DELETE localhost:9000/v1/brands/1`
