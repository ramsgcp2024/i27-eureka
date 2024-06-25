// This Jenkinsfile is for Eureka microservice
pipeline {
    agent {
        label 'k8s-slave'
    }
    tools {
        maven 'Maven-3.8.8'
        jdk 'JDK-17'
    }
    environment {
        APPLICATION_NAME = "Eureka"
        SONAR_URL = "http://34.125.173.244:9000/"
        SONAR_TOKEN = credentials('sonar_creds')
    }
    stages {
        stage('Build') {
            steps {
                echo "Building the ${env.APPLICATION_NAME} Application"
                //mvn command
                sh 'mvn clean package -DskipTests=true'
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }
        stage('Unit Tests') {
            steps {
                echo "Executing unit tests for ${env.APPLICATION_NAME} Application"
                sh """
                echo "Starting sonar scan"
                mvn clean sonar:sonar \
                    -Dsonar.projectKey=i27-eureka \
                    -Dsonar.host.url= http://34.125.173.244:9000/ \
                    -Dsonar.login=sqa_12fa083602b267056fdb6c977861d1060d1d47bf
                """
            }
        }
        
    }
}