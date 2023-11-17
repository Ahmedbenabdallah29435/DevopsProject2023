pipeline {
    agent any

environment {
    SONAR_MDP = 'sonar'
    DOCKER_IMAGE_NAME = 'ahmedBenAbdallah-5Sae4-g3-ski'
    DOCKER_IMAGE_TAG = "v${BUILD_NUMBER}" // Using Jenkins BUILD_NUMBER as the tag
}
    stages {
        stage('GIT') {
            steps {
                checkout scm
            }
        }

        stage('Cleaning the project') {
            steps{
                sh 'mvn clean compile'
            }
        }
      
        stage ('Artifact construction') {
            steps{
                sh 'mvn install -Dmaven.test.skip=true'
            }
        }

        stage('Run Unit Tests') {
            steps {
            sh 'mvn test'
            }
        }
      
        stage('SonarQube Analysis') {
             steps {
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=${SONAR_MDP}'
                   }
             }

        stage('Deploy to Nexus') {
             steps {
                    sh 'mvn deploy -DskipTests=true'
                   }
             }

        stage('building docker image') {
             steps {
                     sh 'docker build -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG -f Dockerfile ./'
                   }
             }

        stage('dockerhub') {
             steps {

                    sh "docker login -u docker23440 -p docker23440"
                    sh "docker tag $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG docker23440/ahmedBenAbdallah-5Sae4-g3-ski:$DOCKER_IMAGE_TAG"
                    sh "docker push  docker23440/ahmedBenAbdallah-5Sae4-g3-ski:$DOCKER_IMAGE_TAG"
                   }
             }

    }
}
