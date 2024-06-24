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
        SONAR_URL = "http://34.125.60.190:9000"
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
       /*
        stage ('Unit Tests') {
            steps {
                echo "Executing unit test for ${env.APPLICATION_NAME} Application"
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    jacoco execPattern: 'target/jacoco.xml'
                }
            }
        }
        */

        stage ('Sonar') {
            steps {
                sh """
                echo "Starting sonar scan"
                mvn clean sonar:sonar \
                    -Dsonar.projectKey=i27-eureka \
                    -Dsonar.host.url= ${env.SONAR_URL} \
                    -Dsonar.login=${SONAR_TOKEN}
                """
            }
        }

            
        
    }
}