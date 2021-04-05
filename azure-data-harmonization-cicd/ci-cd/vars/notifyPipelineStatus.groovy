#!/usr/bin/env groovy

/**
* Send notifications based on Pipeline Status
*/
def call(Map config){

  def application = config.application
  def status = config.status
  def recipients = config.recipients
  def subject = "Jenkins ${application} Pipeline"
  def details


	if(status.equalsIgnoreCase("unstable")){
		details = """<p>Jenkins ${application} Appliction Pipeline Completed with Status <font color="Orange"><b>${status}</b></font> due to <font color="Orange"><b>Test Failures/Errors</b></font></p><p>Check console output at &QUOT;<a href='${env.BUILD_URL}console.'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""
	}
	if(status.equalsIgnoreCase("success")){
		details = """<p>Jenkins ${application} Appliction Pipeline Completed with Status <font color="green"><b>${status}</b></font></p><p>Check console output at &QUOT;<a href='${env.BUILD_URL}console.'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""
	}
	if(status.equalsIgnoreCase("failure")){
		details = """<p>Jenkins ${application} Appliction Pipeline Completed with Status <font color="red"><b>${status}</b></font></p><p>Check console output at &QUOT;<a href='${env.BUILD_URL}console.'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""
	}
  emailext (
      to: recipients,
      subject: subject,
      body: details
    )
}

