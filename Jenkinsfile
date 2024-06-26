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
        SONAR_URL = "http://34.125.122.109:9000/"
        SONAR_TOKEN = credentials('sonar_creds')
        POM_VERSION = readMavenPom().getVersion()
        POM_PACKAGING = readMavenPom().getPackaging()
      //  DOCKER_HUB = docker.io/i27k8s10
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
        stage('Sonar') {
            steps {
                echo "Starting sonar scans with Quality Gates"
                //before we go to next step we need to install SonarQube plugin
                // next goto manage Jenkins > System > Add sonarqube > give URL and token for SonarQube 
                withSonarQubeEnv('SonarQube') { //we given 'SonarQube' name at the time of Add sonarqube
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=i27-eureka \
                        -Dsonar.host.url=${env.SONAR_URL} \
                        -Dsonar.login=${SONAR_TOKEN}
                        """
                }   
              //  timeout (time: 2, unit: 'MINUTES') { //SECONDS, MINUTES, HOURS, DAYS
                //        script {
                  //          waitForQualityGate abortPipeline: true
                    //    }
                }
            }
            stage('Docker Build & Push') {
                steps {
                    echo "Starting Docker build and stage"
                    sh """
                    ls -la
                    pwd
                    cp ${WORKSPACE}/target/i27-${env.APPLICATION_NAME}-${env.POM_VERSION}.${env.POM_PACKAGING} ./.cicd/
                    echo "Listing files in .cicd folder"
                    ls -la ./.cicd/
                    echo "********************** Building DOCKER Image ***********************"
                    docker images
                    """
                }
            }
        }
// docker build --build-args JAR_SOURCE = i27-${env.APPLICATION_NAME}-${env.POM_VERSION}.${env.POM_PACKAGING} -t ${env.DOCKER_HUB}/${env.APPLICATION_NAME}:${GIT_COMMIT} ./.cicd
       /*
        stage('Docker Format') {
            steps {
                //i27-Eureka-0.0.1-SNAPSHOT.jar
                echo " The Current format is: i27-${env.APPLICATION_NAME}-${env.POM_VERSION}.${env.POM_PACKAGING}"
                // Expected format
                echo "Expected format is: i27-${env.APPLICATION_NAME}-${currentBuild.number}-${BRANCH_NAME}.${env.POM_PACKAGING}" 
            }

        }
        */
    }

