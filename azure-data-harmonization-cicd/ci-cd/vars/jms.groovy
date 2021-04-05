def call(Map config){

pipeline {
    agent any
    
	environment {
		EAI = "${config.eai}"
		APPLICATION= "${config.application}"
		BRANCH_NAME = "${env.GIT_BRANCH}"
		SONAR_NAME= "$EAI"+':'+"$APPLICATION"
	//	SNAPSHOT_NEXUS_REPO_VERSION = "${config.projectVersion}"+"-SNAPSHOT"
	    VERSION = null
	//	RELEASE_NEXUS_REPO_VERSION = "${config.projectVersion}"
		GROUP_ID = 'eai'+"$EAI"+'.com.fedex.jms'
		ARTIFACT_EXTENTION = "jar"
		FORTIFY_NAME = "${config.fortify}"
		NEXUSIQ_NAME = "${config.nexusIq}"
		BUILD_DIR = "${config.buildDir}"
		RECIPIENTS = "${config.recipients}"
		RELEASE_NEXUS_REPO = 'release'
		SNAPSHOT_NEXUS_REPO = 'snapshot'
 		NEXUS_CREDENTIALS = "$EAI"+'_nexus'
 		APPLICATION_DIR = "${WORKSPACE}"+'/'+"${config.appModule}"
        DEV_FUNC_NAME= "eventhub-json-func"
		DEV_REG_NAME= "rg-pkg-dly"
		AZURE_CREDENTIALS = credentials('sp-jenkins') 
		//AZURE_SUBSCRIPTION_ID='dfcbad0e-d419-48fb-9781-fbc052153bbb'
        //AZURE_TENANT_ID='b945c813-dce6-41f8-8457-5a12c2fe15bf'
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
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
		steps {
		    script{
				deleteDir()
				checkout scm
		
			}
			}
		}
/*
stage('Code Quality And Coverage'){
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
			steps {
			sh 'cd $APPLICATION_DIR && mvn checkstyle:checkstyle -s settings.xml'
			}
		}
	stage('Code Quality And Coverage'){
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
			steps {
				parallel(
				'Checkstyle': {sh 'cd $APPLICATION_DIR && mvn compile checkstyle:checkstyle checkstyle:check -s settings.xml'},
				'PMD': {sh 'cd $APPLICATION_DIR && mvn compile pmd:pmd pmd:check -s settings.xml'},
				'Junits': {sh 'cd $APPLICATION_DIR && mvn test -s settings.xml'}
				)
			}
		}
	
*/	
        stage('Build') {
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }

			steps {
				script{
					sh 'cd $APPLICATION_DIR && mvn install -s settings.xml'
					//sh 'cd $APPLICATION_DIR && mvn clean package -s settings.xml'
				}
			}
        }

/*
		stage ('Sonar'){
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
		steps {
		    script{

			sonarqube projectKey: SONAR_NAME,
			projectName: SONAR_NAME,
			src: '${APPLICATION_DIR}/src/main/java',
			binaries: "${APPLICATION_DIR}/${BUILD_DIR}/classes",
			test: '${APPLICATION_DIR}/src/test'
			}
			}
		}
*/
	stage('Jar Nexus Upload') {
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
			steps {		
				script{
				    pom = readMavenPom(file: APPLICATION_DIR+'/'+'pom.xml')
				    VERSION = pom.getVersion()
				    if(params.ArtifactType.equalsIgnoreCase("Snapshot")){
			 nexusStaging (
artifactId:APPLICATION,
file:APPLICATION_DIR+'/'+BUILD_DIR+'/'+APPLICATION+'-'+VERSION+'.'+ARTIFACT_EXTENTION,
type:ARTIFACT_EXTENTION,
credentialsId:NEXUS_CREDENTIALS,
groupId:GROUP_ID,
repository:SNAPSHOT_NEXUS_REPO,
version:VERSION
)
				        
				    }else{
				    
				       pom = readMavenPom(file: APPLICATION_DIR+'/'+'pom.xml')
        VERSION = pom.getVersion()
      //  VERSION = RELEASE_NEXUS_REPO_VERSION
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
				
			}
/*
		stage ('NexusIq'){
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
		steps {
		    script{
			nexusPolicyEvaluation iqApplication: "${NEXUSIQ_NAME}", iqStage: 'build'
			}
			}
		}

        stage("stashing source ") {
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
            steps {
               stash includes: '**', name: 'source'
            }
        }

        stage("Get Fortify Scripts"){
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
            agent {label 'master'}

            steps{
                getFortifyScripts()
            }
        }

        stage("Run Fortify Analysis"){
			when{
				expression {params.Choice == "Build_And_Deploy" || params.Choice == "Build"}
            }
            steps{
                startFortifyAnalysis("${FORTIFY_NAME}")
            }
        }
 
*/

	stage('Dev Deployment') {
			when{
				expression {(params.Choice == "Build_And_Deploy" || params.Choice == "Deploy") && params.Environment == "Dev"}
            }
		steps {
			script{
			    				notifyDeploymentStatus application:APPLICATION,space:params.Environment,status:'Started',recipients:RECIPIENTS
			if(!params.Choice.equalsIgnoreCase("Build_And_Deploy"))
{
VERSION = params.Version

//VERSION = "0.0.1-SNAPSHOT"
}
if(params.ArtifactType.equalsIgnoreCase("Snapshot")){
downloadNexusArtifact repo:SNAPSHOT_NEXUS_REPO,groupId:GROUP_ID,artifactId:APPLICATION,extension:ARTIFACT_EXTENTION,version:VERSION,release: false    
}else{
downloadNexusArtifact repo:RELEASE_NEXUS_REPO,groupId:GROUP_ID,artifactId:APPLICATION,extension:ARTIFACT_EXTENTION,version:VERSION,release: true
}


				print "Azure Fuction App Dev Deployment"
				
				// withCredentials([usernamePassword(credentialsId: AZURE_CREDENTIALS, passwordVariable: 'AZURE_CLIENT_SECRET', usernameVariable: 'AZURE_CLIENT_ID')]) {
                           
    //                     }
				
		//	sh 	'cd  APPLICATION_DIR && mvn azure-functions:deploy'
			//	azureFNDeploy credenttialId:'dev_credential_id',fuctionAppName:DEV_FUNC_NAME,resourcGroup:DEV_REG_NAME,filePath:''
		//	azureFunctionAppPublish azureCredentialsId: AZURE_CREDENTIALS, resourceGroup: DEV_REG_NAME, appName: DEV_FUNC_NAME,filePath: APPLICATION_DIR
				//withCredentials([usernamePassword(credentialsId: 'azuresp', passwordVariable: 'AZURE_CLIENT_SECRET', usernameVariable: 'AZURE_CLIENT_ID')])
			//	azureFNDeploy credenttialId:Dev_CRED_ID,fuctionAppName:DEV_FUNC_NAME,resourcGroup:DEV_REG_NAME,filePath:''
				notifyDeploymentStatus application:APPLICATION,space:params.Environment,status:'Completed',recipients:RECIPIENTS
				}

		}
		}
	stage('QA Deployment') {
			when{
				expression {(params.Choice == "Build_And_Deploy" || params.Choice == "Deploy") && params.Environment == "QA" && params.Repo == "Release"}
            }
		steps {
			script{
			if(!params.Choice.equalsIgnoreCase("Build_And_Deploy"))
{
//VERSION = params.Version
VERSION = "0.0.1-SNAPSHOT"
}
		notifyDeploymentStatus application:APPLICATION,space:params.Environment,status:'Started',recipients:RECIPIENTS
				downloadNexusArtifact repo:SNAPSHOT_NEXUS_REPO,groupId:GROUP_ID,artifactId:APPLICATION,extension:ARTIFACT_EXTENTION,version:VERSION,release: false
				print "Azure Fuction App QA Deployment"
				notifyDeploymentStatus application:APPLICATION,space:params.Environment,status:'Completed',recipients:RECIPIENTS
				}
			
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
