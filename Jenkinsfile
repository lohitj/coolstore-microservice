def templatePath = 'https://raw.githubusercontent.com/subir0071/coolstore-microservice/stable-ocp-3.9/openshift/coolstore-template.yaml' 
def templateName = 'coolstore'
def return1() {
            openshift.withCluster() {
            openshift.withProject('coolstore-test-subir') {
            return openshift.selector('bc', 'web-ui').exists()
            }

}
}

def BuildDecide(update){
    if(update == 'true') {
        openshift.withCluster() {
		    openshift.verbose()
                openshift.withProject('coolstore-test-subir') {
                  openshift.newApp(templatePath) 
                }
            }
    }
    else  {
				  openshift.withCluster() {
					openshift.withProject('coolstore-test-subir') {
                      				openshift.startBuild("--from-build=web-ui")
                   			}           	  
				
    }
}
}


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
                      echo "cluster ${openshift.project()} re Using project:  ${openshift.project()}"
                    }           
            }

        }
    }
    stage ('check') {
	    steps{
        BuildDecide(return1())
	    }
    }
  }
	  
	  
      
    
    
  }  
		  
 }
