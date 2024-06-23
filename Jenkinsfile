// This Jenkinsfile is for Eureka microservice

pipeline {
    agent {
        label 'k8s-slave'
    }
    stages {
        stage('Build') {
            steps {
                echo "Building the Eurika Application"
                //mvn command
                sh 'mvn clean package -DskipTests=true'
            }
        }
    }
}