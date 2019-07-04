pipeline {
    environment {
        //ONLY THESE are lines you can change as the person who develops this app
        ECR_CREDENTIALS_ID = 'ecr:eu-central-1:jenkins-dxl-gr-ecr'
        JAR_NAME = 'noosapi-0.0.1.jar'
        KUBERNETES_NAMESPACE_dev='dxl-dev2-gr'
        KUBERNETES_NAMESPACE_test='dxl-test-gr'
        KUBERNETES_NAMESPACE_preprod='dxl-preprod-gr'
        KUBERNETES_NAMESPACE_staging='dxl-staging-gr'
        KUBERNETES_NAMESPACE_stagingref='dxl-stagingref-gr'

        //DO NOT CHANGE anything below these lines. These will automatically change according to the branches
        //you build with, and the variables you have set above this step.
        BUILD_VERSION = VersionNumber([projectStartDate: '2018-09-10', versionNumberString: '${BUILDS_ALL_TIME}', versionPrefix: '']);
        DOCKER_IMAGE_URL_PREFIX = '805276130606.dkr.ecr.eu-central-1.amazonaws.com/dxl-gr-myvodafone'
        BUILD_ENVIRONMENT='dev'
        FINAL_IMAGE_NAME='${DOCKER_IMAGE_URL_PREFIX}:${BUILD_ENVIRONMENT}.${BUILD_VERSION}'
        DEPLOYMENT_CONFIG_FOLDER='./deployment/kubeconfig_${BUILD_ENVIRONMENT}'
        CURRENT_KUBERNETES_NAMESPACE='dxl-dev2-gr'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
    }
    agent {
        label "docker"
    }
    stages {
        stage('Java Build') {
            steps {
				script {
                    defineEnvironment()
                }
				sh """
					echo current kubernetes namespace: $CURRENT_KUBERNETES_NAMESPACE
					echo build environment: $BUILD_ENVIRONMENT
					echo final image name: $FINAL_IMAGE_NAME
					echo deployment config folder: $DEPLOYMENT_CONFIG_FOLDER
                """
                configFileProvider([configFile(fileId: '4a952bc3-b38c-4486-b317-ace7bc71f539', variable: 'MAVEN_SETTINGS')]) {
                    container('docker') {
                        withCredentials([usernamePassword(credentialsId: 'nexus-functional-gr-user', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            sh """
        cd .
        mvn clean
        mvn package -s $MAVEN_SETTINGS -DskipTests

        #mvn sonar:sonar -s $MAVEN_SETTINGS
        """
                        }
                    }
                }
            }
        }

        stage('Docker build') {
            steps {
                container('docker') {
                    withDockerRegistry([credentialsId: ECR_CREDENTIALS_ID, url: "https://${DOCKER_IMAGE_URL_PREFIX}"]) {
                        sh """
       cd .
       ##cp ${WORKSPACE}/target/${JAR_NAME} .

       docker build -t ${FINAL_IMAGE_NAME} .            
       docker push ${FINAL_IMAGE_NAME}
       """
                    }
                }
            }
        }
        stage('Deployment') {
            when {
                not {
                    anyOf {
                        branch 'build_ga'
                    }
                }
            }
            steps {
                container('kubectl') {
                    withCredentials([file(credentialsId: getKubernetesCredentialsName(), variable: 'kubeconfig')]) {
                        sh "echo $kubeconfig"
                        sh "cp $kubeconfig /tmp/kubeconfig"

                        //change the name of the image inside the deployment yaml.
                        sh "sed -i \"s|IMAGE_NAME|${FINAL_IMAGE_NAME}|\" ${DEPLOYMENT_CONFIG_FOLDER}/deployment.yaml"
                        sh "KUBECONFIG=/tmp/kubeconfig kubectl apply -f ${DEPLOYMENT_CONFIG_FOLDER} -n ${CURRENT_KUBERNETES_NAMESPACE}"
                    }
                }
            }
        }
    }
}

/**
 * Define environment variables that will be used
 * across the building process here.
 * @return
 */
def defineEnvironment() {
    def branchName = "${env.BRANCH_NAME}"
    echo 'we are building on ' + env.BRANCH_NAME + ' branch'
    echo 'ecr credentials ID: ' + ECR_CREDENTIALS_ID

    def buildVersionName = '.${BUILD_VERSION}-'

    /*
    Check known branches and make necessary adjustments to the environment variables.
    If no known branches are found, the "dev" deployment configuration is considered as
    the fallback scenario.

    So far, the following branches are considered: develop, build_test, build_staging, build_stagingref and build_preprod.
     */
    if (branchName == 'build_preprod') {
        BUILD_ENVIRONMENT='preprod'
        CURRENT_KUBERNETES_NAMESPACE='${KUBERNETES_NAMESPACE_preprod}'
        echo 'we are on the preprod build phase'
    } else if (branchName == 'build_test') {
        BUILD_ENVIRONMENT='test'
        CURRENT_KUBERNETES_NAMESPACE='${KUBERNETES_NAMESPACE_test}'
        echo 'we are on the build_test phase'
    } else if (branchName == 'build_staging') {
        BUILD_ENVIRONMENT='rc'
        CURRENT_KUBERNETES_NAMESPACE='${KUBERNETES_NAMESPACE_staging}'
        echo 'we are on the build_test phase'
    } else if (branchName == 'build_stagingref') {
        BUILD_ENVIRONMENT='rc'
        CURRENT_KUBERNETES_NAMESPACE='${KUBERNETES_NAMESPACE_stagingref}'
        echo 'we are on the stagingref phase'
    } else if (branchName == 'build_ga') {
        BUILD_ENVIRONMENT='ga'
        CURRENT_KUBERNETES_NAMESPACE='${KUBERNETES_NAMESPACE_stagingref}'
        echo 'we are on the stagingref ga phase'
        /* for the ga environment, there should not be any build version */
        buildVersionName = '-'
    } else if (branchName == 'develop') {
        BUILD_ENVIRONMENT='dev'
        CURRENT_KUBERNETES_NAMESPACE='${KUBERNETES_NAMESPACE_dev}'
        echo 'we are building on dev environment'
    } else {
        echo 'not recognized any common environments'
    }

    echo 'build environment: ' + BUILD_ENVIRONMENT

    //set the final image name to be uploaded to the cluster.
    FINAL_IMAGE_NAME='${DOCKER_IMAGE_URL_PREFIX}:' + BUILD_ENVIRONMENT + buildVersionName
    //set the deployment config folder to look for kubernetes deployments according to the master being built.
    DEPLOYMENT_CONFIG_FOLDER='./deployment/kubeconfig_' + BUILD_ENVIRONMENT
}


/**
 * Selects the kubernetes credentials name according to the target
 * namespace we need to deploy. The credentials should be uploaded in Jenkins
 * prior to running this script.
 * @return
 */
def getKubernetesCredentialsName() {
    def namespaceName = "${CURRENT_KUBERNETES_NAMESPACE}"
    echo "namespace name: " + namespaceName
    switch (namespaceName) {
        case "dxl-dev2-gr":
            return "jenkins-dxl-dev2-gr-deployer"
        case "dxl-dev-gr":
            return "jenkins-dxl-dev-gr-deployer"
        case "dxl-test-gr":
            return "jenkins-dxl-test-gr-deployer"
        case "dxl-staging-gr":
            return "jenkins-dxl-staging-gr-deployer"
        case "dxl-stagingref-gr":
            return "jenkins-dxl-stagingref-gr-deployer"
        case "dxl-stagingref2-gr":
            return "jenkins-dxl-stagingref2-gr-deployer"
        case "dxl-preprod-gr":
            return "jenkins-dxl-preprod-gr-deployer"
    }
    echo "WARNING : no credentials name found for namespace" + namespaceName
    return "dxl-gr-kubeconfig-csot"
}
