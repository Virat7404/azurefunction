pipeline {
    agent any
  environment {
        AZURE_SUBSCRIPTION_ID='92170e15-44be-43ac-b67d-8fd81b05b8f9'
        AZURE_TENANT_ID='22fca6e7-362a-49c9-b252-691d5c333d98'
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
        sh 'mvn clean package'
      }
      }
    }

    stage('Publish') {
     steps{        
             withCredentials([usernamePassword(credentialsId: 'credfunhttpuspp', passwordVariable: 'AZURE_CLIENT_SECRET', usernameVariable: 'AZURE_CLIENT_ID')]) {
                           // sh 'az login --allow-no-subscriptions --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
                            //sh 'func init'
                            sh 'az --version'
                          sh 'func functionapp publish ensfn3 --publish-settings-only'
                        }
           
                      
                  
     }
      }
 }
}
