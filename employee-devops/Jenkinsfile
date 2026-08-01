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
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('ems frontend/ems') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        stage('Docker Compose Build') {
            steps {
                dir('employee-devops') {
                    bat 'docker compose build'
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                dir('employee-devops') {
                    bat 'docker compose up -d'
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
