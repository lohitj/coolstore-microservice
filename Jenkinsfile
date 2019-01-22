def templatePath = 'https://raw.githubusercontent.com/lohitj/coolstore-microservice/stable-ocp-3.9/openshift/coolstore-template.yaml' 
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
                    openshift.withProject('ms-coolstore') {
                      echo "cluster ${openshift.project()} Using project:  ${openshift.project()}"
                    }           
            }

        }
    }
  }
	  
	  
      stage('create New') {
      when {
          expression {
				expression {
          openshift.withCluster() {
            openshift.withProject('ms-coolstore') {
            return !openshift.selector('bc', 'web-ui').exists()
            }
          }
        }
          }

      }
      steps {
        script {
            openshift.withCluster() {
		    openshift.verbose()
                openshift.withProject('ms-coolstore') {
                  openshift.newApp(templatePath) 
                }
            }
        }
      }
    }
    
    
    stage('Recreate existing') {
      when {
          expression {
				expression {
          openshift.withCluster() {
            openshift.withProject('ms-coolstore') {
            return openshift.selector('bc', 'web-ui').exists()
            }
          }
        }
          }

      }
      
      steps {
			  script{
				  openshift.withCluster() {
					openshift.withProject('lohit-test') {
                      				openshift.startBuild("--from-build=web-ui")
                   			 }           	  
				  }
			  }
		  }	
		  
		  
    }
    
}
}
