pipeline {
    agent any

    environment {
        BACKEND_IMAGE = "aditya6853/ems-backend:latest"
        FRONTEND_IMAGE = "aditya6853/ems-frontend:latest"
    }

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
                    sh 'npm install'
                    sh 'CI=false npm run build'
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

                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                    docker push $BACKEND_IMAGE
                    docker push $FRONTEND_IMAGE

                    docker logout
                    '''
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                dir('employee-devops') {
                    sh '''
                    docker compose down
                    docker compose up -d
                    '''
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Deployment Successful!'
        }

        failure {
            echo '❌ Build Failed!'
        }

        always {
            cleanWs()
        }
    }
}
