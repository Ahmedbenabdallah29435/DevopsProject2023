pipeline {
    agent any

    environment {
        SONAR_MDP = 'sonar'
        DOCKER_IMAGE_NAME = 'ahmedbenabdallah-5sae4-g3-ski'
        DOCKER_IMAGE_TAG = "v${BUILD_NUMBER}" // Using Jenkins BUILD_NUMBER as the tag
        SONAR_PROJECT_KEY = 'tn.esprit.spring:gestion-station-ski'
        SONAR_AUTH_TOKEN = 'sqb_fcdb9c4beaafd57b61a1f629562b1c67ce914a59'
        SONAR_URL = 'http://192.168.2.132:9000'
        NEXUS_URL = 'http://192.168.2.132:8081/#browse/browse'
        DOCKERHUB_URL = 'https://hub.docker.com/repository/docker/docker23440/ahmedbenabdallah-5sae4-g3-ski'
    }

    stages {
        stage('GIT') {
            steps {
                checkout scm
            }
        }

        stage('Cleaning the project') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Artifact construction') {
            steps {
                sh 'mvn install'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
                junit '**/target/surefire-reports/*.xml' // Ensure this matches your test report path
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

        stage('Building Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG -f Dockerfile ./'
            }
        }

        stage('Dockerhub') {
            steps {
                sh "docker login -u docker23440 -p Docker23440"
                sh "docker tag $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG docker23440/ahmedbenabdallah-5sae4-g3-ski:$DOCKER_IMAGE_TAG"
                sh "docker push docker23440/ahmedbenabdallah-5sae4-g3-ski:$DOCKER_IMAGE_TAG"
            }
        }

        stage('Run Spring and MySQL Containers') {
            steps {
                sh "docker login -u docker23440 -p Docker23440"
                sh 'docker compose up -d'
                echo 'Run Spring && MySQL Containers'
            }
        }
    }

    post {
        success {
            echo "Build succeeded. Preparing to send email..."
            script {
                // Sending the email without trying to access non-permitted methods
                emailext body: """
                    <html>
                        <body>
                            <h2>Build and Test Summary ✅✅</h2>
                            <p>Dear Team,</p>
                            <p>The Jenkins build <b>${env.JOB_NAME}</b> has successfully completed with the following details:</p>
                            <h3>Build Details:</h3>
                            <ul>
                                <li><b>Build Number:</b> ${env.BUILD_NUMBER}</li>
                                <li><b>Build Status:</b> SUCCESS</li>
                                <li><b>SonarQube Analysis:</b> Completed</li>
                                <li><b>Docker Image:</b> ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}</li>
                                <li><b>Deployment:</b> Successfully deployed to Nexus and Dockerhub</li>
                            </ul>
                            <h3>Additional Information:</h3>
                            <ul>
                                <li><b>SonarQube Link:</b> <a href="${SONAR_URL}/dashboard?id=${SONAR_PROJECT_KEY}">SonarQube Dashboard</a></li>
                                <li><b>Nexus Repository:</b> <a href="${NEXUS_URL}">Nexus Repository Link</a></li>
                                <li><b>Docker Image:</b> Available at DockerHub <a href="${DOCKERHUB_URL}">here</a></li>
                            </ul>
                            <p>For more details, please check the Jenkins job at: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                            <p><i>This is an auto-generated email. Please do not reply.<br/>
                            Cordially,</i></p>
                        </body>
                    </html>
                """,
                subject: "Build Successful - ${env.JOB_NAME} [Build #${env.BUILD_NUMBER}] ✅",
                to: 'benabdallah.ahmed@esprit.tn',
                replyTo: 'benabdallah.ahmed@esprit.tn',
                mimeType: 'text/html'
            }
        }
        failure {
            script {
                emailext body: """
                    <html>
                        <body>
                            <h2>Build and Test Summary ❌❌</h2>
                            <p>Dear Team,</p>
                            <p>The Jenkins build <b>${env.JOB_NAME}</b> has failed.</p>
                            <h3>Build Details:</h3>
                            <ul>
                                <li><b>Build Number:</b> ${env.BUILD_NUMBER}</li>
                                <li><b>Build Status:</b> FAILURE</li>
                                <li><b>Failed Stage:</b> ${env.STAGE_NAME}</li>
                            </ul>
                            <h3>Error Details:</h3>
                            <ul>
                                <li><b>Error Message:</b> Please refer to the Jenkins console output for detailed logs.</li>
                                <li><b>SonarQube Analysis:</b> Failed or Incomplete</li>
                            </ul>
                            <p>For more details, please check the Jenkins job at: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                            <p><i>This is an auto-generated email. Please do not reply.<br/>
                            Cordially,</i></p>
                        </body>
                    </html>
                """,
                subject: "Build Failed - ${env.JOB_NAME} [Build #${env.BUILD_NUMBER}] ❌",
                to: 'benabdallah.ahmed@esprit.tn',
                replyTo: 'benabdallah.ahmed@esprit.tn',
                mimeType: 'text/html'
            }
        }
        always {
            echo "This will run always, regardless of the build result."
        }
    }
}
