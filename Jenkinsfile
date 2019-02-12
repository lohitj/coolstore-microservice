def yamlFile(){
def datas = readYaml text: """
templatePath: 'https://raw.githubusercontent.com/lohitj/coolstore-microservice/stable-ocp-3.9/openshift/coolstore-template.yaml'
microservice: 'web-ui'
sonarTemplate: 'https://raw.githubusercontent.com/lohitj/coolstore-microservice/stable-ocp-3.10/sonarqube-ephemeral-template.yaml'
sonarUrl: 'http://sonar-coolstore-dev-lohit.apps.na39.openshift.opentlc.com'
devproject: 'coolstore-dev-lohit'
cicdproject: 'test-lohit'
"""
}
def return1(name) 
{
    openshift.withCluster() {
    openshift.withProject(datas.devproject) {
    return openshift.selector('dc', name).exists()
    }

}
}
def checout()
{
    checkout([$class: 'GitSCM', branches: [[name: '*/stable-ocp-3.10']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: "${GIT_URL}"]]])
}

def BuildDecide(update)
{
    if(!update) {
        openshift.withCluster() {
	openshift.verbose()
        openshift.withProject(datas.devproject) {
        openshift.newApp(datas.templatePath) 
        }
    }
	BuildDecideSonar()
    }
    else  
    {
	    openshift.withCluster() {
	    openshift.withProject(datas.devproject) {
        openshift.startBuild(datas.microservice)
        }           	  
	    }
    }
}
def BuildDecideSonar()
{
    openshift.withCluster() {
	openshift.verbose()
    openshift.withProject(datas.cicdproject) {
    openshift.newApp(datas.sonarTemplate) 
        }
    }
}

pipeline 
{
	agent any
	
    environment 
    {
        GIT_URL='https://github.com/lohitj/coolstore-microservice.git'
    }
    tools{
        maven 'MAVEN_HOME'
        jdk   'JAVA_HOME'
    }

  stages 
  {
        stage ('check') 
        {
	        steps
            	{
                	BuildDecide(return1(datas.microservice))
	        }
        }
        stage ('Build') 
        {
            steps
            {
                checout()
                sh 'mvn -f cart-service/pom.xml clean verify'
            }
        }
	stage ('Sonar')
        {
            steps
            {
                sh "mvn -f cart-service/pom.xml sonar:sonar -Dsonar.host.url='http://sonar-coolstore-dev-lohit.apps.na39.openshift.opentlc.com' -X"

            }
	    
        }
	stage ('Unit test')
	  {
		  steps
		  {
			sh "mvn -f cart-service/pom.xml test"
		  }
	  }
	


	 stage('Integration-Test')
	  {

		steps
		  {
			  container("jnlp"){sh "mvn -f cart-service/pom.xml integration-test"}
		  }
	  }
			 
        stage('Jacoco')
        {
            steps
            {
                sh "mvn -f cart-service/pom.xml  clean package"
            }
        }
	stage('Findbug')
        {
            steps
            {
                sh "mvn -f cart-service/pom.xml  findbugs:findbugs"
            }
        }
        
	}
    
}
