pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'roh652/springboot-app:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t $DOCKER_IMAGE ."
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Log in to DockerHub with the credentials
                        sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                        // Push the Docker image to DockerHub
                        sh "docker push $DOCKER_IMAGE"
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    sh '''
                    docker stop springboot-app || true
                    docker rm springboot-app || true
                    docker run -d -p 8081:8080 --name springboot-app $DOCKER_IMAGE
                    '''
                }
            }
        }
    }
}
