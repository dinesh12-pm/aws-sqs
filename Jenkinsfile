pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        AWS_ACCESS_KEY_ID = credentials('aws-access-key')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-key')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/<your-username>/aws-sqs-demo.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building project...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Starting application...'
                sh 'nohup java -jar target/aws-sqs-demo-1.0.0.jar > app.log 2>&1 &'
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
