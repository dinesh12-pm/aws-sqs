pipeline {
    agent any

    // 👇 Auto-install tools section
    tools {
        maven 'Maven_3_9_9'   // Name must match "Manage Jenkins → Tools → Maven installations"
        jdk 'JDK17'           // Optional, add if Java auto-install is set up in Jenkins
    }

    environment {
        AWS_REGION = 'ap-south-1'
        AWS_ACCESS_KEY_ID = credentials('aws-access-key')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-key')
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
