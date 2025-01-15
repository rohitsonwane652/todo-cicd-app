pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk'
        PATH = "$JAVA_HOME/bin:$PATH"
        DOCKER_IMAGE = 'yourdockerhubusername/springboot-app:latest'  // Update with your DockerHub username
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from GitHub
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the Spring Boot application with Maven
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image for the Spring Boot application
                    sh '''
                    docker build -t $DOCKER_IMAGE .
                    '''
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    // Login to DockerHub (you should store your credentials in Jenkins)
                    withDockerRegistry(credentialsId: 'dockerhub-credentials', url: '') {
                        sh '''
                        docker push $DOCKER_IMAGE
                        '''
                    }
                }
            }
        }

        stage('Deploy with Docker') {
            steps {
                script {
                    // Stop and remove any existing container
                    sh 'docker ps -q -f name=springboot-app | xargs -r docker stop'
                    sh 'docker ps -a -q -f name=springboot-app | xargs -r docker rm'

                    // Run the new container
                    sh '''
                    docker run -d -p 8080:8080 --name springboot-app $DOCKER_IMAGE
                    '''
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            // You can add steps to clean up old Docker images, containers, etc.
        }
    }
}
