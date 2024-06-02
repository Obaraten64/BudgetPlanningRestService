# BudgetPlanning REST Service 💲
### This project aimed to develope simple family budget planning REST service

# What I used:
<p align="left"> 
  <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java 17" width="80" height="80"/> </a>
  <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring boot 3.2.5" width="80" height="80"/> </a>
  <a href="https://swagger.io/" target="_blank" rel="noreferrer"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5ShAeKtKygPYMTtobJ3GVtX7tBX8_INrQkA&s" alt="swagger" width="80" height="80"/></a>
  <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="80" height="80"/>   </a>
   <a href="https://testcontainers.com/" target="_blank" rel="noreferrer"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS29woch_wZWYLyxPKYQtvCmv-J-FU4dfHR0w&s" alt="testcontainers" width="80" height="80"/></a>
  <a href="https://projectlombok.org/" target="_blank" rel="noreferrer"> <img src="https://avatars.githubusercontent.com/u/45949248?s=280&v=4" alt="lombok" width="80" height="80"/> </a>
</p>

# ❗ How to run project: ❗
  - Download files from this repository
  - Open Docker Dekstop and the open downloaded file in your favorite IDE and build the project: _mvn clean package_
  - Run the application: _docker compose up_
  - Go to your browser and navigate to _http://localhost:8080/swagger-ui/index.html_ to view the application endpoints documentation and try them out or simply access available endpoints via http clients such as Postman
  - When finished, stop the program by executing: _docker compose down_


![image](https://github.com/Obaraten64/BudgetPlanningRestService/assets/71453885/5996f6ba-bb8f-4446-bdd1-ae2b5550d03c)

# Important API endpoints
## Registration ✍🏻
  - Endpoint: POST /user/register
  - Description: Register a new user
  - Request: Requires a JSON object containing info about the user
  - Response: Returns a string that reports the success of the registration
  - Example:
    ```json
    {
      "name": "vadim",
      "email": "email@gmail.com",
      "password": "1234",
      "role": "UsEr"
    }

## Bank account creation
  - Endpoint: POST /account/register
  - Description: Register a new bank account
  - Request: Requires a JSON object containing initial balance of a new bank account
  - Response: Returns a JSON object representing created bank account
  - Example:
    ```json
    request
    {
      "balance": 100
    }
    response
    {
      "id": 1,
      "balance": 100
    }

## Money withdraw
  - Endpoint: POST /account/withdraw
  - Description: Withdraw money from account
  - Request: Requires a JSON object containing information abount operation
  - Response: Returns a JSON object representing state of a bank account
  - Example:
    - Correct request
    ```json
    request
    {
      "amount": 100,
      "reason": "notebook"
    }
    response
    {
      "id": 1,
      "balance": 200
    }
    ```
    - You do not have a bank account
    ```json
    request
    {
      "amount": 100,
      "reason": "notebook"
    }
    response
    {
      "error": "You do not have a bank account"
    }
