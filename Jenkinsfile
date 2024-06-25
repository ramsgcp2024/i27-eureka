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
        POM_VERSION = readMavenPom().getVersion()
        POM_PACKAGING = readMavenPom().getPackaging()
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
        stage('Docker Format') {
            steps {
                //i27-eureka-0.0.1-SNAPSHOT.jar
                //install pipeline utility before executing this.  It will ask for approval then approve as a admin member
            //    echo "The Current format is: i27-${env.APPLICATION_NAME}-${env.POM_VERSION}.${env.POM_PACKAGING}" 

                    //expected : eureka-buildnumber-branchname.jar
             //   echo "Expected format is: ${env.APPLICATION_NAME}-${currentBuild.number}-${BRANCH_NAME}.${env.POM_PACKAGING}"
            }
        }

            
        
    }
}