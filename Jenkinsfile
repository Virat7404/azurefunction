pipeline {
    agent any
  environment {
        AZURE_SUBSCRIPTION_ID='92170e15-44be-43ac-b67d-8fd81b05b8f9'
        AZURE_TENANT_ID='22fca6e7-362a-49c9-b252-691d5c333d98'
        RESOURCE_GROUP = 'Jen_rgp' 
        FUNC_NAME = 'rawevh'
        APPLICAION_DIR= 'odd-or-even-function-master/odd-or-even-function-master'
    }
stages{
    stage('Init') {
      steps{
       script{      
            checkout scm
       }
      }
    }

    stage('Build') {
      steps{
      script{      
        //sh 'cd $PWD/$APPLICAION_DIR && mvn clean package'
        //sh 'rm $PWD/$APPLICAION_DIR/target/azure-functions/odd-or-even-function-sample/*.jar'
          pom = readMavenPom(file:'pom.xml')
				    VERSION = pom.getVersion()
          print VERSION
	      sh 'echo $VERSION'
        //sh 'cd -'
        //sh 'mkdir deployment_files'
        //sh 'cp -r $PWD/$APPLICAION_DIR/target/azure-functions/odd-or-even-function-sample/* deployment_files'
        
      }
      }
    }

    stage('Publish') {
     steps{        
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
         // sh 'cd $PWD/target/azure-functions/func-001-fxs-daw-atlas-raw-transfer-dev && func azure functionapp publish functooldeployraw --publish-local-settings -i'
        // sh 'mkdir deployment_files'
         //sh 'cp $PWD/$APPLICAION_DIR && mkdir deployment_files'
         
           sh 'az logout'
         //cleanWs()
                      
                  
     }
      }


 }
}
