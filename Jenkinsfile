library 'reference-pipeline'
library 'jms-sharedlibrary'

properties([[$class: 'JiraProjectProperty'], gitLabConnection('GitLab'), [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false], parameters([[$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', description: '', filterLength: 1, filterable: false, name: 'Choice', randomName: 'choice-parameter-11609754981219883', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return[\'error\']'], script: [classpath: [], sandbox: true, script: 'return [\'Build_And_Deploy\',\'Build\',\'Deploy\']']]], [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT', description: '', filterLength: 1, filterable: false, name: 'Environment', randomName: 'choice-parameter-11609754983208333', referencedParameters: 'Choice', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return[\'error\']'], script: [classpath: [], sandbox: true, script: '''if (Choice.equals("Build_And_Deploy") || Choice.equals("Deploy")) {
return [\'Dev\',\'QA\', \'Prod\']
}else {
return [\'NotApplicable\']
}''']]], [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT', description: '', filterLength: 1, filterable: false, name: 'Version', randomName: 'choice-parameter-11609754985181760', referencedParameters: 'Choice', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return[\'error\']'], script: [classpath: [], sandbox: true, script: '''if (Choice.equals("Deploy")){
	def repo = "release"
	def groupId = "eai3537372.com.fedex.jms"
	def artifactId = "eventhub_raw_data_transfer"
	
	def list = new ArrayList<String>()
	def xml = "https://nexus.prod.cloud.fedex.com:8443/nexus/service/rest/v1/search?repository=$repo&format=maven2&maven.groupId=$groupId&maven.artifactId=$artifactId".toURL().text
	def root = new groovy.json.JsonSlurper().parseText(xml).items.version	
	def modifiedArtifact = new ArrayList<String>()
    for(def item :  root){
        modifiedArtifact.add(item)
    }
    return modifiedArtifact.reverse()
}else {
return [\'NotApplicable\']
}''']]], [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT', description: '', filterLength: 1, filterable: false, name: 'ArtifactType', randomName: 'choice-parameter-11609754987170868', referencedParameters: 'Choice', script: [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: true, script: 'return[\'error\']'], script: [classpath: [], sandbox: true, script: '''if (Choice.equals("Build_And_Deploy")) {
return [\'Snapshot\',\'Release\']
}else if (Choice.equals("Build")) {
return [\'Snapshot\',\'Release\']
}else if (Choice.equals("Deploy")) {
return [\'Release\']
}else {
return [\'NotApplicable\']
}''']]]])])


jms(
eai:'3537372',
application:'eventhub_raw_data_transfer',
buildDir:'target',
fortify:'3537372_harmonization',
nexusIq:'3537372-harmonization',
appModule:'azurefunction/eventhub_raw_data_transfer',
recipients:'shubham.verma.osv@fedex.com',
DevFunctionApp:'dev_af_hrm_poc',
DevReg:'rg-pkg-dly'
)