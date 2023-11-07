pipeline {
    agent any
   
    stages {
        stage('GIT') {
            steps {
                checkout scm
            }
        }

        stage('Cleaning the project') {
            steps{
                sh 'mvn clean'
            }
        }
      
        stage ('Artifact construction') {
            steps{
                sh 'mvn package -Dmaven.test=true -P test-coverage'
            }
        }
      
        stage('SonarQube Analysis') {
             steps {
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
                   }
             }
    }
