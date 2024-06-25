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
                sh """
                echo "Starting sonar scan"
                echo "Executing unit tests for ${env.APPLICATION_NAME} Application"
                     mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=i27-eureka \
                    -Dsonar.host.url=http://34.125.173.244:9000 \
                    -Dsonar.login=sqa_27938702c7e2f326ed3f0f6b7d492b1ff8642791
                     """
            }
        }
    }
}

