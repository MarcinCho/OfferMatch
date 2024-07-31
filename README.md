# Service Match
___
## Overview
Key languages technologies and frameworks:
* Back-end:
  - Java 21
  - Spring Boot 3
  - PostgreSQL
  - MongoDB
* Front-end:
  - TypeScript
  - React
  - Tailwind
  - PrimeReact (UI Library)
* DevOps:
  - Docker
  - Git
  - Kubernetes

### Description
ServiceMatch is a web application designed to connect clients with service providers, implemented features

* Project Listing:
   - Clients can easily post their projects, detailing requirements and specifications.
   - Projects are categorized and searchable, enhancing visibility to relevant service providers.
   - Service providers can browse and filter projects that align with their expertise and interests.
 
* Quoting System:
   - Companies can submit detailed, professional quotes for projects they're interested in pursuing.
   - Clients receive and compare multiple quotes, helping them make informed decisions.

* Built in Communication:
   - Built-in messaging system allows clients and service providers to discuss project details before formal quoting.
   - This feature ensures clarity and helps in refining project requirements.

* Profiles:
   - Clients can view company profiles, including ratings, reviews, and portfolio.
   - Service providers can showcase their expertise, past projects, and client testimonials.

  
## How to run
___
### Requirements
- Docker 
- internet connection
___
1. Clone the repository
```bash
git clone https://github.com/MarcinCho/Service_Match.git
```
2. Navigate to project -> SCRIPTS directory
```bash
cd Service_Match/SCRIPTS
```
3. ...

## Architecture and microservices
___
### Client_FE - Front-end client [REACT]


### Companies_MS - Companies microservice


### Config_Server_MS - Configuration server


### Projects_MS - projects microservice

### Users_MS - Users microservice

### SCRIPTS - devOps files
contains Dockercompose files scripts and such

___

[//]: # (#### Use case scenario:)

[//]: # ()
[//]: # (<strong> Client </strong>)

[//]: # ()
[//]: # (Mark is interested in painting his leaving room. He opens up ServiceMatch webapp as customer, clics on add project and fills)

[//]: # (out a form, than he hits submmit. If Mark does not have account in Service Match any comunication will be sen to him by email)

[//]: # (otherwise h cna just log in and check the inbox directly on the site.)

[//]: # ()
[//]: # (<strong> Company </strong>)



