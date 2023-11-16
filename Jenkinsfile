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
                sh 'mvn install -Dmaven.test.skip=true'
            }
        }
      
        stage('SonarQube Analysis') {
             steps {
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
                   }
             }

        stage('Run Mockito Tests') {
                    steps {
                        sh 'mvn test'
                    }
                }
    }
}
