pipeline {
    agent any

environment {
    SONAR_MDP = sonar
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

        stage('Run Mockito Tests') {
                            steps {
                                sh 'mvn test'
                            }
                        }
      
        stage('SonarQube Analysis') {
             steps {
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=${SONAR_MDP}'
                   }
             }

        stage('Deploy') {
             steps {
                    sh 'mvn deploy -DskipTests=true'
                   }
             }



    }
}
