def call(Map config){

pipeline {
    agent any
    
	environment {
		
		APPLICATION= "${config.application}"
		BRANCH_NAME = "${env.GIT_BRANCH}"
	        VERSION = null
		BUILD_DIR = "${config.buildDir}"
		RELEASE_NEXUS_REPO = 'release'
		SNAPSHOT_NEXUS_REPO = 'snapshot'
 		APPLICATION_DIR = "${WORKSPACE}"+'/'+"${config.appModule}"
                DEV_FUNC_NAME= "eventhub-json-func"
		DEV_REG_NAME= "rg-pkg-dly"
		AZURE_SUBSCRIPTION_ID='92170e15-44be-43ac-b67d-8fd81b05b8f9'
                AZURE_TENANT_ID='22fca6e7-362a-49c9-b252-691d5c333d98'
                RESOURCE_GROUP = 'Jen_rgp' 
                 FUNC_NAME = 'rawevh'
	}
	tools{
		maven 'Maven 3.3.9'
		jdk 'JAVA_8'
	}
	options {
            disableConcurrentBuilds()
            buildDiscarder(logRotator(numToKeepStr: env.BRANCH_NAME ==~ /master/ ? '10' : '5'))
          }

    stages {
        		stage ('Checkout'){
		steps {
		    script{
				deleteDir()
				checkout scm
		
			}
			}
		}
	
        stage('Build') {
	
			steps {
				script{
					//sh 'cd $APPLICATION_DIR && mvn install -s settings.xml'
					sh 'mvn clean package'
				}
			}
        }



	stage('Dev Deployment') {
		
		steps {
			 withCredentials([usernamePassword(credentialsId: 'credfunhttpuspp', passwordVariable: 'AZURE_CLIENT_SECRET', usernameVariable: 'AZURE_CLIENT_ID')]) {
                           // sh 'az login --allow-no-subscriptions --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
                            //sh 'func init'
                           // sh 'az --version'
                          //sh 'func functionapp publish ensfn3 --publish-settings-only'
                   sh '''
             az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID
             az account set -s $AZURE_SUBSCRIPTION_ID
                      '''
                        }
           //sh 'cd $PWD/target/azure-functions/func-001-fxs-daw-atlas-raw-transfer-dev && zip -r ../../../archive.zip ./* && cd -'
          // sh "az functionapp deployment source config-zip -g $RESOURCE_GROUP -n $FUNC_NAME --src archive.zip"
          sh 'cd $PWD/target/azure-functions/func-001-fxs-daw-atlas-raw-transfer-dev && func azure functionapp publish functooldeployraw --publish-local-settings -i'
           sh 'az logout'
		}
	
}
}
