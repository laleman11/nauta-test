# nauta-test

## Prerequisites

* Docker
* Java 21

## Execution
* Build project
  ```sh
  ./gradlew build
  ```
* build docker compose
  ```sh
  docker-compose build
  ```

* run docker compose
  ```sh
  docker-compose up
  ```

## Exposed endpoint

  ```sh
    localhost:8080/api/nauta
  ```

## Microservice API

* Get tasks (default page 0, default size 10)
  ```sh
   curl --location 'localhost:8080/api/nauta/tasks'
  ```

* Get tasks by custom page and size
  ```sh
   curl --location 'localhost:8080/api/nauta/tasks?page=1&size=4'
  ```

* Get tasks by state 
  ```sh
   curl --location 'localhost:8080/api/nauta/tasks?state=TO_DO'
  ```

* Create task
  ```sh
  curl --location 'localhost:8080/api/nauta/tasks?page=1' \
  --header 'Content-Type: application/json' \
  --data '{
  "title": "Task 12",
  "description": "OTHER",
  "state": "TO_DO"
  }'
  ```

* Update task
  ```sh
  curl --location --request PUT 'localhost:8080/api/nauta/tasks/{task_id}' \
  --header 'Content-Type: application/json' \
  --data '{
  "title": "Task nueva2_nuevecita",
  "description": "Example2_funcionar",
  "state": "DONE"
  }'
  ```

* Delete task
  ```sh
  curl --location --request DELETE 'localhost:8080/api/nauta/tasks/{task_id}'
  ```

