node('docker-node') {
  def buildNumber = currentBuild.number
  def name_img = "${env.JOB_NAME}"
  def image
  def version = "${buildNumber}"
  def pubregistry = "registry.devops.7f8254f4188647b4be19.eastus.aksapp.io/"
  stage('Checkout Repository') {
    deleteDir()
    checkout scm
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv('sonar-poc') {
      sh "/usr/share/maven/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=consumer-event-billing-1 -Dsonar.login=sqp_243f254fafb201963fc0bab3e3288312b5bc3330"
    }
  }
  stage("Quality gate") {
    waitForQualityGate abortPipeline: true
  }                     
  stage('Build Image'){
      image = docker.build("$name_img")
    }
  stage('Push Image'){
    docker.withRegistry('http://20.231.125.187:8182', 'nexus') {
      image.push("$version")
      image.push("latest")
    }  
  }
  stage('Delete Image'){
    def imgrm
    imgrm = sh(script: "docker images $name_img:latest -q", returnStdout: true).trim()
    println imgrm
    sh("docker rmi -f $imgrm")
  }
  stage('Deploy Image'){
    def fullname = "${name_img}:${version}"
    println fullname
    withCredentials([file(credentialsId: 'kubeconfig', variable: 'SECRET_FILE')]) {
      sh 'KUBECONFIG=$SECRET_FILE kubectl apply -f ./kubernetes/deployment.yaml'
      sh 'KUBECONFIG=$SECRET_FILE kubectl set image deployment/'+name_img+' '+name_img+'='+pubregistry+fullname
    }
  }

}
