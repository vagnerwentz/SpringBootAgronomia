pipeline {
    agent any

    environment {
        IMAGE_NAME = "devops-springboot-api"
        CONTAINER_NAME = "springboot-app"
    }

    stages {
        stage('Tooling versions') {
            steps {
                sh '''
                    docker --version
                    docker compose --version
                '''
            }
        }
        
        stage('Clonar repositório') {
            steps {
                git branch: 'main', url: 'https://github.com/vagnerwentz/SpringBootAgronomia.git'
            }
        }

        

        stage('Construir JAR com Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                sh 'docker build --platform=linux/amd64 -t ${IMAGE_NAME}:latest .'
            }
        }

        stage('Remover container antigo') {
            steps {
                sh '''
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                '''
            }
        }

        stage('Executar Novo Container') {
            steps {
                sh 'docker run -d --name ${CONTAINER_NAME} -p 8081:8081 ${IMAGE_NAME}:latest'
            }
        }
    }
}
