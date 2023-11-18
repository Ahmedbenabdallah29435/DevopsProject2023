pipeline {
    agent any

environment {
    SONAR_MDP = 'sonar'
    DOCKER_IMAGE_NAME = 'ahmedbenabdallah-5sae4-g3-ski'
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

                    sh "docker login -u docker23440 -p Docker23440"
                    sh "docker tag $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG docker23440/ahmedbenabdallah-5sae4-g3-ski:$DOCKER_IMAGE_TAG"
                    sh "docker push  docker23440/ahmedbenabdallah-5sae4-g3-ski:$DOCKER_IMAGE_TAG"
                   }
             }

        stage('Run Spring and MySQL Containers') {
             steps {
                    sh "docker login -u docker23440 -p Docker23440"
                    sh 'docker compose up -d'
                    echo 'Run Spring && MySQL Containers'
                   }
             }

        stage('Notify mail to my email') {
            steps {
                script {
                    def testResults = currentBuild.result ?: 'SUCCESS' // Default to 'SUCCESS' if result is null

                    if (currentBuild.result == 'FAILURE') {
                        // Override testResults if the build result is FAILURE
                        testResults = 'FAILURE'
                    }

                    emailext subject: "Tests Status - ${testResults}",
                            body: """
                                <html>
                                    <body>
                                        <p>The tests are complete. Result: ${testResults}<br/>
                                        Job: ${env.JOB_NAME}<br/>
                                        More Info can be found here: ${env.BUILD_URL}</p>
                                        <p><i>This is an auto-generated email. Please don't reply.<br/>
                                        Cordially.</i></p>
                                    </body>
                                </html>
                            """,
                            to: 'benabdallah.ahmed@esprit.tn',
                            replyTo: 'benabdallah.ahmed@esprit.tn',
                            contentType: 'text/html'
                }
            }
        }

    }
}

