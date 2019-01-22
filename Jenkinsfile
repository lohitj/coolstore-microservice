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
                    openshift.withProject('subir-coolstore-test') {
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
            openshift.withProject('subir-coolstore-test') {
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
                openshift.withProject('subir-coolstore-test') {
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
            openshift.withProject('subir-coolstore-test') {
            return openshift.selector('bc', 'web-ui').exists()
            }
          }
        }
          }

      }
      
      steps {
			  script{
				  openshift.withCluster() {
					openshift.withProject('subir-coolstore-test') {
                      				openshift.startBuild("--from-build=web-ui")
                   			 }           	  
				  }
			  }
		  }	
		  
		  
    }
    
}
}def templatePath = 'https://raw.githubusercontent.com/subir0071/coolstore-microservice/stable-ocp-3.9/openshift/coolstore-template.yaml' 
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
                    openshift.withProject('subir-coolstore-test') {
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
            openshift.withProject('subir-coolstore-test') {
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
                openshift.withProject('subir-coolstore-test') {
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
            openshift.withProject('subir-coolstore-test') {
            return openshift.selector('bc', 'web-ui').exists()
            }
          }
        }
          }

      }
      
      steps {
			  script{
				  openshift.withCluster() {
					openshift.withProject('subir-coolstore-test') {
                      				openshift.startBuild("--from-build=web-ui")
                   			 }           	  
				  }
			  }
		  }	
		  
		  
    }
    
}
}
