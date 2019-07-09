pipeline {
	agent {
		lable 'docker'
	}
	
	environment {
		IMAGE_NAME = 'Test'	
	}
	
    stages {
        stage('Unit Tests') {
            steps {
				sh """		
					cd spring-oauth2-employee-service-master
					mvn clean
					mvn test
				"""
            }            
        }

        stage('Analyze Code') {
            steps {
                // SonarQube
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
            }
        }
    }
}
