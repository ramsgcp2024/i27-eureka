def printName(name, age) {
    return {
        echo "Your name is ${name}"
        echo "Your age is ${age}"
    }
}

pipeline {
    agent any
    stages {
        stage('First Stage') {
            steps {
                script {
                    printName('Rama','45').call()
                }
            }
        }
        stage('Second Stage') {
            steps {
                script {
                    printName('Krishna','55').call()
                }
            }
        }
    }
}