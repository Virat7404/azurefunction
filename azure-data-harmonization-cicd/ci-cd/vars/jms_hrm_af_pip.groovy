def call(Map config){

pipeline {
    agent any
    
	environment {
		EAI = "${config.eai}"
		APPLICATION= "${config.application}"
		BRANCH_NAME = "${env.GIT_BRANCH}"
		SONAR_NAME= "$EAI"+':'+"$APPLICATION"
		VERSION = '0.1.0'
		GROUP_ID = 'eai'+"$EAI"+'.com.fedex.jms'
		ARTIFACT_EXTENTION = "jar"
		FORTIFY_NAME = "${config.fortify}"
		NEXUSIQ_NAME = "${config.nexusIq}"
		BUILD_DIR = "${config.buildDir}"
		RECIPIENTS = "${config.recipients}"
		RELEASE_NEXUS_REPO = 'release'
 		NEXUS_CREDENTIALS = "$EAI"+'_nexus'
 		APPLICATION_DIR = "${WORKSPACE}"+'/'+"${config.appModule}"
 		
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
        stage('Build') {
			steps {
				deleteDir()
				checkout scm
				script{
					sh 'cd $APPLICATION_DIR && mvn clean install -s settings.xml'
				}
			}
        }

	
	stage('Artifact Upload') {
			steps {		
				script{
				       pom = readMavenPom(file: APPLICATION_DIR+'/'+'pom.xml')
        VERSION = pom.getVersion()
        print VERSION
               nexusStaging (
artifactId:APPLICATION,
file:APPLICATION_DIR+'/'+BUILD_DIR+'/'+APPLICATION+'-'+VERSION+'.'+ARTIFACT_EXTENTION,
type:ARTIFACT_EXTENTION,
credentialsId:NEXUS_CREDENTIALS,
groupId:GROUP_ID,
repository:RELEASE_NEXUS_REPO,
version:VERSION
)
					}
				}
				
			}



	stage('Dev Deployment') {
		steps {
			script{
			    //VERSION="1.0"
				downloadNexusArtifact repo:RELEASE_NEXUS_REPO,groupId:GROUP_ID,artifactId:APPLICATION,extension:ARTIFACT_EXTENTION,version:VERSION,release:'true'	
				}
			print "Azure Fuction App event hub deploy Deployment"
/*azureFNDeploy credenttialId:Dev_CRED_ID,fuctionAppName:DEV_FUNC_NAME,resourcGroup:DEV_REG_NAME,filePath:'workspace/'
				notifyDeploymentStatus application:APPLICATION,space:params.Environment,status:'Completed',recipients:RECIPIENTS
*/			
		}
		}

	}
	post{
	    success{
	        notifyPipelineStatus application:APPLICATION,status:'success',recipients:RECIPIENTS
	        print "pipeline success"
		}
		failure{
			notifyPipelineStatus application:APPLICATION,status:'failure',recipients:RECIPIENTS
			print "Failure"
		}
		unstable{
		    script{
					notifyPipelineStatus application:APPLICATION,status:'unstable',recipients:RECIPIENTS
				}
		}
		always {
		    cleanWs()
		}

	}
}
}
