
# Spring API Challenge :+1:


## Quick overview

Main features:
* Dockerized Spring Boot Java 17 REST Api
* Asynchronously log every http request into a PostgresSQL JPA repository with pagination
* Provide an api to fetch the request log
* Small simple calc service with a backing cache
* Rate limiter is implemented with a Filter, this works fine for a single service, for a distributed system I'd use
a redis bucket.
* Ran out of time for other stuff like using Spring Retry and general error handling (sorry!)

The most complete feature is the AsyncRequestLogging, please don't judge too harsh for the missing features!

Also please check out the integration tests :)


## Considerations
Request logging was implemented through @Async and ApplicationEvents but can be easily swapped for another messaging 
solution such as RabbitAMQP. Integration test provided :smile:

Cache is implemented on Redis on a very simple lazy-load cache-aside strategy. For production, depending on concurrency
and cache size I'd probably try to keep it on sharded cluster with a write-through on the writer side. Other more 
complex alternatives are push-sub notifications or leases. Most strategies add an increasingly complex


## Trying it out

### Build local container :vertical_traffic_light:
`docker run --rm -it -p 8080 $(docker build -q .)`

### Or 

### Replicated with compose :airplane:
`docker compose up --build`

### Swagger docs

Check that you are pointing to a bound port with `docker ps`

[Local swagger URI](http://localhost:8080/swagger-ui/index.html)
