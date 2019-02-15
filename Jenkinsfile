
    
def yamlFile()
{
    echo 'start'
    def datas = readYaml file: '/var/lib/jenkins/jobs/ddd/workspace/propertyFile.yml'
    env.microservice = datas.microservice
    env.devproject = datas.devproject
    env.cicdproject = datas.cicdproject
    env.templatePath = datas.templatePath
    env.sonarTemplate = datas.sonarTemplate
    env.templateName = datas.templateName
	env.sonar = datas.sonar
}
def return1(name) 
{
    echo name
    openshift.withCluster() {
    openshift.withProject("${devproject}") {
    return openshift.selector('dc',"${microservice}").exists()
    }

}
}
def checout()
{
    checkout([$class: 'GitSCM', branches: [[name: '*/stable-ocp-3.10']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/lohitj/coolstore-microservice.git']]])
}

def BuildDecide(update)
{
    if(!update) {
        openshift.withCluster() {
	openshift.verbose()
        openshift.withProject("${devproject}") {
        openshift.newApp("${templatePath}") 
        }
    }
	
    }
    else  
    {
	    openshift.withCluster() {
	    openshift.withProject("${devproject}") {
        openshift.startBuild("${microservice}")
        }           	  
	    }
		/*BuildDecideSonar()*/
    }
}
def BuildDecideSonar()
{
    openshift.withCluster() {
	openshift.verbose()
    openshift.withProject("${cicdproject}") {
    openshift.newApp("${sonarTemplate}") 
        }
    }
}
podTemplate(cloud:'openshift',label: 'selenium', 
  containers: [
    containerTemplate(
      name: 'jnlp',
      image: 'cloudbees/java-build-tools',
      args: '${computer.jnlpmac} ${computer.name}'
    )])
{
node 
{
   
   
   def MAVEN_HOME = tool "MAVEN_HOME"
   def JAVA_HOME = tool "JAVA_HOME"
   env.PATH="${env.PATH}:${MAVEN_HOME}/bin:${JAVA_HOME}/bin"
    stage('Build')
   {
       checout()
	   yamlFile()
	   
       sh 'mvn -f cart-service/pom.xml clean compile'
   }
  
   stage('test')
   {
        sh 'mvn -f cart-service/pom.xml test'
   }
   stage ('Sonar')
   {
            
      sh "mvn -f cart-service/pom.xml sonar:sonar -Dsonar.host.url='http://sonar-test-lohit.apps.na39.openshift.opentlc.com' -X"
   }
   stage('check')
   {
       BuildDecide(return1("${microservice}"))
   }
   stage('Jacoco')
    {
        sh "mvn -f cart-service/pom.xml  clean package"
    }

		node('selenium')
	{
		stage('Integration-Test')
		{
			container('jnlp')
			{
			    echo'integration-test'
			 //   sh"mvn -f cart-service/pom.xml integration-test"
			}
		}
	}

			stage('Findbug')
				{
            
					sh "mvn -f cart-service/pom.xml  findbugs:findbugs"
				}
			stage("Tagging Image for Production")
				{	
					script {
						openshift.withCluster() {
						openshift.withProject("${devproject}") {
						openshift.tag("${templateName}:latest", "${templateName}-staging:latest") 
					}
				}
			}
		}
  
	}
}
