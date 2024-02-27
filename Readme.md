# Wordle Game App

## Introduction
This is a simple implementation of the popular Wordle game written in Java. The game allows players to guess a secret word within a limited number of attempts.

## Prerequisites
Before running the Wordle game app, ensure that you have the following installed:
- Java Development Kit (JDK) version 17 or higher
- Maven build tool
- Docker (if running WildFly in a container)

## Setup
1. Clone the repository to your local machine:

```bash
git https://github.com/Witchio/wordle-app
cd wordle-game
```

2. If you're running WildFly locally:
    - Download and install WildFly from the [official website](https://wildfly.org/downloads/)
    - Start WildFly by navigating to the `bin` directory and running `standalone.bat` (Windows) or `standalone.sh` (Unix/Linux)

   If you're using Docker:
    - Ensure you have Docker installed and running on your machine
    - Run `docker-compose up` in the project directory to start WildFly and MariaDB containers

3. Deploy the application to WildFly:
    - If running WildFly locally, ensure the server is running and accessible at `localhost:8080` and `localhost:9990`
    - If using Docker, the application will automatically be deployed when the containers start

4. Access the application:
    - Once the application is deployed, you can access it at `http://localhost:8080/wordle-game`
    - You can also access the WildFly management console at `http://localhost:9990` (if running locally)

## How to Play
- The objective of the game is to guess the secret word within a limited number of attempts.
- Each guess consists of entering a word containing five letters.
- After each guess, the game provides feedback in the form of colored pegs:
    - Green pegs indicate correct letters in the correct position.
    - Yellow pegs indicate correct letters in the wrong position.
    - Grey pegs indicate incorrect letters.
- The game ends when the player correctly guesses the secret word or when the maximum number of attempts is reached.
