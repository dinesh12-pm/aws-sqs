pipeline {
    agent any

    tools {
        maven 'Maven_3_9_9'   // Must match Jenkins → Manage Jenkins → Global Tool Configuration
        jdk 'JDK17'           // Optional but recommended
    }

    environment {
        AWS_REGION = 'ap-south-1'   // Keep region only
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📥 Checking out repository...'
                git branch: 'main', url: 'https://github.com/dinesh12-pm/aws-sqs.git'
            }
        }

        stage('Build') {
            steps {
                echo '⚙️ Building project with Maven...'
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
                echo '🚀 Deploying application...'

                // 👇 Securely inject AWS credentials
                withCredentials([
                    usernamePassword(
                        credentialsId: 'aws-access-key',
                        usernameVariable: 'AWS_ACCESS_KEY_ID',
                        passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                    )
                ]) {
                    script {
                        if (isUnix()) {
                            sh '''
                                echo "🔑 Exporting AWS credentials..."
                                export AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
                                export AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
                                export AWS_REGION=ap-south-1

                                echo "🚀 Starting Java app..."
                                nohup java -jar target/aws-sqs-demo-1.0.0.jar > app.log 2>&1 &

                                echo "✅ Application started successfully!"
                            '''
                        } else {
                            bat '''
                                set AWS_ACCESS_KEY_ID=%AWS_ACCESS_KEY_ID%
                                set AWS_SECRET_ACCESS_KEY=%AWS_SECRET_ACCESS_KEY%
                                set AWS_REGION=ap-south-1

                                echo Starting Java app...
                                start /B java -jar target\\aws-sqs-demo-1.0.0.jar > app.log 2>&1
                                echo Application started successfully!
                            '''
                        }
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
        always {
            echo '📦 Pipeline finished.'
        }
    }
}
