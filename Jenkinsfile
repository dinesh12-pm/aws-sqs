pipeline {
    agent any

    tools {
        maven 'Maven_3_9_9'
        jdk 'JDK17'
    }

    environment {
        AWS_ACCESS_KEY_ID     = credentials('aws-access-key')      // Username from aws-access-key
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-key')     // Password from aws-secret-key
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/dinesh12-pm/aws-sqs.git'
            }
        }

        stage('Build') {
            steps {
                echo '🚧 Building project with Maven...'
                script {
                    if (isUnix()) {
                        sh 'mvn clean package -DskipTests'
                    } else {
                        bat 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running tests...'
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Starting application...'
                script {
                    if (isUnix()) {
                        sh 'nohup java -jar target/aws-sqs-demo-1.0.0.jar > app.log 2>&1 &'
                    } else {
                        bat 'start /B java -jar target\\aws-sqs-demo-1.0.0.jar > app.log 2>&1'
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build & Deploy Successful!'
        }
        failure {
            echo '❌ Build Failed!'
        }
    }
}
