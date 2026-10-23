pipeline {
    agent any
    triggers {
        pollSCM('H/2 * * * *')
    }
    options {
        timestamps()
        disableConcurrentBuilds()
    }
    environment {
        DOCKERHUB_REPO = 'varun3214/hotel-menu-service'
        K8S_NAMESPACE = 'hotel-demo'
        K8S_DEPLOYMENT = 'hotel-menu-service'
        CONTAINER_NAME = 'hotel-menu-service'
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    env.IMAGE_TAG =
                        "2.0.0"
                    env.IMAGE =
                        "${env.DOCKERHUB_REPO}:${env.IMAGE_TAG}"
                    echo "Building image: ${env.IMAGE}"
                }
            }
        }

        stage('Maven Build and Test') {
            steps {
                sh 'mvn -B clean verify'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t "$IMAGE" .'
            }
        }
        stage('Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKERHUB_USER',
                        passwordVariable: 'DOCKERHUB_TOKEN'
                    )
                ]) {
                    sh '''
                        set -eu
                        echo "$DOCKERHUB_TOKEN" |
                            docker login \
                              --username "$DOCKERHUB_USER" \
                              --password-stdin
                        docker push "$IMAGE"
                    '''
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    set -eu
                    kubectl -n "$K8S_NAMESPACE" \
                      set image \
                      "deployment/$K8S_DEPLOYMENT" \
                      "$CONTAINER_NAME=$IMAGE"
                    kubectl -n "$K8S_NAMESPACE" \
                      rollout status \
                      "deployment/$K8S_DEPLOYMENT" \
                      --timeout=180s
                    kubectl -n "$K8S_NAMESPACE" \
                      get deployments,pods,services
                '''
            }
        }
    }
      post {
        success {
            echo "Deployment completed. Image: ${env.IMAGE}"
        }
        always {
            sh 'docker logout >/dev/null 2>&1 || true'
        }
    }
}
