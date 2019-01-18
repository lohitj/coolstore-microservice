def templatePath = 'https://raw.githubusercontent.com/subir0071/coolstore-microservice/1.2.x/openshift/coolstore-template.yaml' 
def templateName = 'coolstore'

pipeline {
  agent {
    node {
      label 'maven' 
    }
  }
  options {
    timeout(time: 20, unit: 'MINUTES') 
  }
  stages {
    stage('preamble') {
        steps {
            script {
                openshift.withCluster() {
                	openshift.verbose()
                    openshift.withProject() {
                      echo "cluster ${openshift.project()} Using project:  ${openshift.project()}"
                    }
                }
                }
            script {
				     openshift.withCluster() {
				     openshift.newProject( 'my-new-project-2' )
				     echo "created new project"
                    openshift.withProject('subir-coolstore-dev') {
                      echo "cluster ${openshift.project()} Using project:  ${openshift.project()}"
                    }           
            }

        }
    }
  }
}
}
