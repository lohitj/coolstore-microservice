
    
def yamlFile()
{
    echo 'start'
    echo "${JOB_BASE_NAME}"
	echo "${JOB_NAME}"
	echo "${workspace}"
	echo "${env.BUILD_URL}"
	echo "${workspace}"
	def path = "${workspace}" + "@script/propertyFile.yml"
	echo path
	def datas = readYaml file: path
	println datas
    env.microservice = datas.microservice
    env.devproject = datas.devproject
    env.cicdproject = datas.cicdproject
	env.prodproject = datas.prodproject
    env.templatePath = datas.templatePath
    env.sonarTemplate = datas.sonarTemplate
    env.templateName = datas.templateName
	env.sonar = datas.sonar
	env.git = datas.git
	echo "${git}"
}
def return1(name,project) 
{
    echo name
    openshift.withCluster() {
    openshift.withProject(project) {
    return openshift.selector('dc',name).exists()
    }

}
}
def checout()
{
    checkout([$class: 'GitSCM', branches: [[name: '*/stable-ocp-3.10']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: "https://github.com/lohitj/coolstore-microservice.git"]]])
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
	BuildDecideSonar()
    }
    else  
    {
	    openshift.withCluster() {
	    openshift.withProject("${devproject}") {
        openshift.startBuild("${microservice}")
        }           	  
	    }
		
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
      image: 'cloudbees/jnlp-slave-with-java-build-tools',
	  alwaysPullImage: true,
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
       
	   yamlFile()
	   checout()
	   yamlFile()
	   script{
		   openshift.withCluster() {
		openshift.verbose()
    openshift.withProject() {
    openshift.newApp("redhat-openjdk18-openshift:1.1~https://github.com/sourabhgupta385/spring-boot-mongodb-example","--strategy=source") 
	     def builds = openshift.selector("bc", "spring-boot-mongodb-example").related('builds')
                  timeout(5) { 
                    builds.untilEach(1) {
                      return (it.object().status.phase == "Complete")
		    }}
        }
    }
	   }
	 
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
       BuildDecide(return1("${microservice}","${devproject}"))
   }
   stage('Jacoco')
    {
        sh "mvn -f cart-service/pom.xml  clean package"
    }

		node('selenium'){stage('Integration-Test')
		{
			container('jnlp'){
			    echo'integration-test'
				checout()
			sh"mvn -f cart-service/pom.xml integration-test"
			}
		}}

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
		  /*stage('Deploy to Production approval')
		  {
             input "Deploy to prod?"
		}
		stage("Promote to Production")
		{
			BuildDecide(return1("${microservice}","${prodproject}"))
		}*/
  
	} 
}
