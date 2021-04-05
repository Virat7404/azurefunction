#!/usr/bin/env groovy

/**
* Send notifications based on build status string
*/
def call(Map config){
  // build status of null means successful
  def application = config.application
  def status = config.status
  def level = config.space
  def recipients = config.recipients

  def subject = "Jenkins ${application} : ${level} Deployment"
  def details = """<p><b><font color="green">${application} ${level} Deployment ${status}</font></b></p>"""
  
  emailext (
      to: recipients,
      subject: subject,
      body: details
    )
}

