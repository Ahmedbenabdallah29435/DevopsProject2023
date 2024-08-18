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
        }
    post {
        success {
            echo "Preparing to send email..."
            script {
                echo "Preparing to send1 email..."
                def testResults = currentBuild.result ?: 'SUCCESS' // Default to 'SUCCESS' if result is null
                emailext body: """
                    <html>
                        <body>
                            <p>The tests are complete. Result: ${testResults}✅✅</p><br/>
                            <p>Job: ${env.JOB_NAME}<br/>
                            More Info can be found here: ${env.BUILD_URL}</p><br/>
                            <p>🔍 ✅ 🔍 ❌<br/>
                            <i>This is an auto-generated email. Please don't reply.<br/>
                            Cordially.</i></p>
                        </body>
                    </html>
                    """,
                    subject: "Tests Status - ${testResults}✅",
                    to: 'b.abdallahahmed2351323@gmail.com',
                    replyTo: 'b.abdallahahmed2351323@gmail.com',
                    mimeType: 'text/html'
            }
        }
        failure {
            script {
                def testResults = currentBuild.result ?: 'FAILURE' // Default to 'FAILURE' if result is null
                emailext body: """
                    <html>
                        <body>
                            <p>The tests are complete. Result: ${testResults}❌❌</p><br/>
                            <p>Job: ${env.JOB_NAME}<br/>
                            More Info can be found here: ${env.BUILD_URL}</p><br/>
                            <p>🔍 ✅ 🔍 ❌<br/>
                            <i>This is an auto-generated email. Please don't reply.<br/>
                            Cordially.</i></p>
                        </body>
                    </html>
                    """,
                    subject: "Tests Status - ${testResults}❌",
                    to: 'b.abdallahahmed2351323@gmail.com',
                    replyTo: 'b.abdallahahmed2351323@gmail.com',
                    mimeType: 'text/html'
            }
        }
        always {
            // Additional actions to be taken regardless of the build result
            script {
                def testResults = currentBuild.result ?: 'UNKNOWN' // Default to 'UNKNOWN' if result is null
                echo "This will run always, regardless of the build result."
                echo "testResults: ${testResults}"
            }
        }
    }

}
