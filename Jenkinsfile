pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

       stage('Build Backend') {
    steps {
        dir('newdemo') {
            sh 'chmod +x mvnw'
            sh './mvnw clean package -DskipTests'
        }
    }
}

       stage('Build Frontend') {
    steps {
        dir('ems frontend/ems') {
            sh 'pwd'
            sh 'ls -l'
            sh 'npm install'
            sh 'npm run build'
        }
    }
}

        stage('Docker Compose Build') {
            steps {
                dir('employee-devops') {
                    sh 'docker compose build'
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'

                    sh 'docker tag ems-backend:latest aditya6853/ems-backend:latest'
                    sh 'docker tag ems-frontend:latest aditya6853/ems-frontend:latest'

                    sh 'docker push aditya6853/ems-backend:latest'
                    sh 'docker push aditya6853/ems-frontend:latest'

                    sh 'docker logout'
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                dir('employee-devops') {
                    sh 'docker compose up -d'
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful!'
        }

        failure {
            echo 'Build Failed!'
        }
    }
}
