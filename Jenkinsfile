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
					#mvn clean -X
					#mvn test -X
				"""
            }            
        }

        stage('Analyze Code') {
            steps {
                // SonarQube
		    sh"""
		    	cd spring-oauth2-employee-service/spring-oauth2-employee-service-master/
		    	#mvn sonar:sonar -Dsonar.sources=. -Dsonar.projectKey=MSTemplate -Dsonar.host.url=http://172.19.106.22:9000 -Dsonar.login=5dafc777e552f22ccc548aa199054ea3131b146f
		"""
		            //sh "/home/jenkins/tools/hudson.plugins.sonar.SonarRunnerInstallation/sonarqubescanner/bin/sonar-scanner -Dsonar.host.url=http://192.168.0.14:9000 -Dsonar.projectName=meanstackapp -Dsonar.projectVersion=1.0 -Dsonar.projectKey=meanstack:app -Dsonar.sources=. -Dsonar.projectBaseDir=/home/jenkins/workspace/sonarqube_test_pipeline"

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
						#cp ${WORKSPACE}/Code/mirco-service/target/micro-service-0.0.1-RELEASE.jar .
						#docker build -t ${IMAGE_NAME} .            
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
						#docker push ${IMAGE_NAME}
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
					branch 'master'
				}
            }
            steps {
				parallel(
				  a: {
					sh "echo Automation Tests on test ENV"
				  },
				  b: {
					sh """
						echo Performance Tests on test ENV
						cd spring-oauth2-employee-service/spring-oauth2-employee-service-master/src/main/resources/performance_scripts
						jmeter -Jjmeter.save.saveservice.output_format=csv -n -t My_VodafoneUK_APIS.jmx -l My_VodafoneUK_APIS.jtl -o /var/jenkins_home/sherif_reports/HTML_Report_1
					"""
				  }
			)
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
