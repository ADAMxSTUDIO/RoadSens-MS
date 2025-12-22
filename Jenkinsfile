pipeline {
  agent any

  environment {
    // Name of SonarQube server configured in Jenkins (Manage Jenkins -> Configure System)
    SONARQUBE_ENV = 'SonarQube'
    // Optional: Maven tool name configured in Jenkins (Manage Jenkins -> Global Tool Configuration)
    MAVEN_TOOL    = 'Maven3'
  }

  tools {
    maven "${MAVEN_TOOL}"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Unit Tests') {
      steps {
        sh 'mvn -B -f ./be/ingestionVideo/pom.xml clean verify'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv(SONARQUBE_ENV) {
          sh '''
            mvn -B -f ./be/ingestionVideo/ sonar:sonar \
              -Dsonar.projectKey=ingestion-platform \
              -Dsonar.projectName="Ingestion Platform" \
              -Dsonar.host.url=$SONAR_HOST_URL \
              -Dsonar.login=$SONAR_AUTH_TOKEN
          '''
        }
      }
    }

    // Optional: wait for quality gate (needs webhook from Sonar to Jenkins)
    stage('Quality Gate') {
      when {
        expression { return env.CHANGE_ID == null } // skip on PRs if you want
      }
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }

    stage('Docker Compose Up') {
      steps {
        sh '''
          docker compose -f ./be/docker-compose.yaml pull
          docker compose -f ./be/docker-compose.yaml up -d --build
        '''
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
      junit '**/target/surefire-reports/*.xml'
    }
  }
}
