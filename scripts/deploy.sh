#!/bin/bash

# 환경 변수 로드
source /home/ubuntu/deployment/scripts/env.sh

# ECR 로그인
aws ecr get-login-password --region $AWS_REGION | sudo docker login --username AWS --password-stdin $ECR_REGISTRY

cd /home/ubuntu/deployment

CONTAINER_NAME="ohhanahana-app"
EXISTING_CONTAINER=$(sudo docker ps -a -q --filter "name=$CONTAINER_NAME")

if [ -n "$EXISTING_CONTAINER" ]; then
  sudo docker stop $EXISTING_CONTAINER
  sudo docker rm $EXISTING_CONTAINER
fi

IMAGE_NAME="${ECR_REGISTRY}/${ECR_REPOSITORY}:latest"
sudo docker pull $IMAGE_NAME
sudo docker run -d --name $CONTAINER_NAME -e SPRING_PROFILE=main -e TZ=Asia/Seoul -p 8080:8080 $IMAGE_NAME