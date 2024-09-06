# Service Match

An app build with Spring Boot microservices and React client.

Service match suppose to bridge clients, projects and companies.
Client creates a project, and adds details. Companies can view the projects and respond with a message or an offer.
Client can view companies and their contact info. 

## Tech Stack

**Backend:** Java, Spring Boot 3.  
Used libraries:


**Frontend:** TypeScript, React, Tailwind CSS, PrimeFaces (UI)

**Databases and other**
- Mongo DB (Project_MS)
- PostgreSQL (Companies_MS)

## Run Locally

### Run with Docker compose
Requierments:
- Docker

1. Copy this repository
```bash
  git clone https://github.com/MarcinCho/Service_Match
```

2. Go to Scripts folder
```bash
  cd Service_Match/Scripts
```
3. Run Docker Compose file, (It may take a while to download all images)
```bash
#docker compose up (I'm still working on it)
```

#### Run with script.

```bash
  git clone https://github.com/MarcinCho/Service_Match
```

Go to the project directory

```bash
  cd Service_Match
```

Give run permission to start_microservices.sh
```bash
chmod +x start-projects.sh
```
Run all backend services (Script uses tmux)
```bash
./Scripts/start_microservices.sh
```
Follow the instructions in terminal to switch between microservices.
```bash
console output:
INFO: to access session use: tmux attach -t <project_folder>/
INFO: to detach from session use ctrl + b, then d
INFO: to close Spring Boot app use ctrl + d
INFO: to kill all use tmux kill-server
```














