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
        APPLICATION_NAME = "eureka"
        SONAR_URL = "http://34.125.122.109:9000/"
        SONAR_TOKEN = credentials('sonar_creds')
        POM_VERSION = readMavenPom().getVersion()
        POM_PACKAGING = readMavenPom().getPackaging()
        DOCKER_HUB = "docker.io/ramsgcp2024"
        DOCKER_CREDS = credentials('docker_creds')
        //DOCKER_HOST_IP = 0.0.0.0
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
                    cp ${WORKSPACE}/target/i27-${env.APPLICATION_NAME}-${POM_VERSION}.${POM_PACKAGING} ./.cicd/
                    echo "Listing files in .cicd folder"
                    ls -la ./.cicd/
                    echo "********************** Building DOCKER Image ***********************"
                    docker build --force-rm --no-cache --build-arg JAR_SOURCE=i27-${env.APPLICATION_NAME}-${env.POM_VERSION}.${env.POM_PACKAGING} -t ${env.DOCKER_HUB}/${env.APPLICATION_NAME}:${GIT_COMMIT} .cicd/.
                    # docker build -t abc .
                    docker images
                    echo "********************** DOCKER Login ***********************"
                    docker login -u ${DOCKER_CREDS_USR} -p ${DOCKER_CREDS_PSW}
                    echo "********************** DOCKER Push ***********************"
                    docker push ${env.DOCKER_HUB}/${env.APPLICATION_NAME}:${GIT_COMMIT}
                                        """
                }
            }

            stage('Deploy to Dev') {
                steps {
                    echo "********************** Deploy to DEV Environment ***********************"
                    withCredentials([usernamePassword(credentialsId: 'rama_docker_vm_creds', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) 
                    {
                        // some block
                        //sh "sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${USERNAME}@${docker_server_ip} hostname -i"
                        sh "sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${USERNAME}@${docker_server_ip} docker run -d -p 5761:8761 --name ${env.APPLICATION_NAME}-dev ${env.DOCKER_HUB}/${env.APPLICATION_NAME}:${GIT_COMMIT}"   
                    }
                }
            }

        }
        //   
//                     cp ${WORKSPACE}/target/i27-${env.APPLICATION_NAME}-${env.POM_VERSION}.${env.POM_PACKAGING} ./.cicd/

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

