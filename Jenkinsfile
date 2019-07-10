pipeline {
	agent {
		label 'docker'
	}
	
	environment {
		IMAGE_NAME = 'Test'	
	}
	
    stages {
        stage('Unit Tests') {
            steps {
				sh """		
					cd spring-oauth2-employee-service/spring-oauth2-employee-service-master/
					mvn clean -X
					mvn test -X
				"""
            }            
        }

        stage('Analyze Code') {
            steps {
                // SonarQube
		    mvn sonar:sonar -Dsonar.projectKey=MSTemplate -Dsonar.host.url='http://172.19.106.22:9000' -Dsonar.login=840b7d1c785880796f4fce5da4873e28d763e89b
            }
        }
		
        stage('Build Artifact') {
            when {
				anyOf {
					branch 'develop'
					branch 'release/*'

				}
            }
            steps {
                container('docker') {
					sh """
						cp ${WORKSPACE}/Code/mirco-service/target/micro-service-0.0.1-RELEASE.jar .
						docker build -t ${IMAGE_NAME} .            
					"""
                }
            }
        }
		
		stage('Save Artifact') {
            when {
				anyOf {
					branch 'develop'
					branch 'release/*'
				}
            }
            steps {
                container('docker') {
					// Push to oc streams
					sh """
						docker push ${IMAGE_NAME}
					"""
                }
            }
        }
		
		stage('Test Deployment') {
            when {
				anyOf {
					branch 'develop'
				}
            }
            steps {
                // OC commands to deploy on test ENV
		    sh "echo Test Deployment"
            }
        }
		
		stage('Automation Tests on test ENV') {
            when {
				anyOf {
					branch 'develop'
				}
            }
            steps {
                // run automation scripts on test ENV
		    sh "echo Automation Tests on test ENV"
            }
        }
		
		stage('Staging Deployment') {
            when {
				anyOf {
					branch 'release/*'
				}
            }
            steps {
                // OC commands to deploy on staging ENV
		    sh "echo Staging Deployment"
            }
        }
		
		stage('Automation Tests on staging ENV') {
            when {
				anyOf {
					branch 'release/*'
				}
            }
            steps {
                // run automation scripts on staging ENV
		    sh "echo Automation Tests on staging ENV"
            }
        }
		
		stage('Production Deployment') {
            when {
				anyOf {
					branch 'master'
				}
            }
            steps {
                // OC commands to deploy on production ENV
		    sh "echo Production Deployment"
            }
        }
    }
}
