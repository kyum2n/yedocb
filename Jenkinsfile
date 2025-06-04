pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/kyum2n/yedocb.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build -x test --no-daemon'
            }
        }

        stage('Test') {
            when {
                expression { return false } // 테스트 단계 무조건 skip
            }
            steps {
                sh './gradlew test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'yedoc-docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        docker login -u $DOCKER_USER -p $DOCKER_PASS
                        docker build -t kyumni/yedoc-backend .
                        docker push kyumni/yedoc-backend
                    '''
                }
            }
        }
    }
}
