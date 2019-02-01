def return1(name) 
{
    openshift.withCluster() {
    openshift.withProject('coolstore-dev-lohit') {
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
        openshift.withProject('coolstore-dev-lohit') {
        openshift.newApp("${templatePath}") 
        }
    }
	BuildDecideSonar()
    }
    else  
    {
	    openshift.withCluster() {
	    openshift.withProject('coolstore-dev-lohit') {
        openshift.startBuild("web-ui")
        }           	  
	    }
    }
}
def BuildDecideSonar()
{
    openshift.withCluster() {
	openshift.verbose()
    openshift.withProject('lohit-test') {
    openshift.newApp("${sonarQube}") 
        }
    }
}

pipeline 
{
	agent {
	   kubernetes(cloud: 'openshift', 
	containerTemplate(alwaysPullImage: true, args: '', command: '', envVars: [], image: 'cloudbees/java-build-tools',
name: 'jnlp', workingDir: '/reports')], volumes: [persistentVolumeClaim(claimName: 'lohit-test', mountPath: '/reports', readOnly: false)]){}
	}
	
    environment 
    {
        GIT_URL='https://github.com/lohitj/coolstore-microservice.git'
	    templatePath = 'https://raw.githubusercontent.com/lohitj/coolstore-microservice/stable-ocp-3.9/openshift/coolstore-template.yaml'
        sonarQube = 'https://raw.githubusercontent.com/lohitj/coolstore-microservice/stable-ocp-3.10/sonarqube-ephemeral-template.yaml'
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
                	BuildDecide(return1('web-ui'))
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
