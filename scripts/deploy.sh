#!/bin/bash

# 환경 변수 로드
source /home/ubuntu/deployment/scripts/env.sh

# 환경 변수 확인 (디버깅을 위해 추가)
echo "ECR_REGISTRY: $ECR_REGISTRY"
echo "ECR_REPOSITORY: $ECR_REPOSITORY"

# AWS ECR 로그인
aws ecr get-login-password --region $AWS_REGION | sudo docker login --username AWS --password-stdin $ECR_REGISTRY

# 디렉토리로 이동
cd /home/ubuntu/deployment

# 기존 컨테이너 중지 및 삭제
CONTAINER_NAME="ohhanahana-app"
EXISTING_CONTAINER=$(sudo docker ps -a -q --filter "name=$CONTAINER_NAME")

if [ -n "$EXISTING_CONTAINER" ]; then
  sudo docker stop $EXISTING_CONTAINER
  sudo docker rm $EXISTING_CONTAINER
fi

# 최신 이미지 가져오기
IMAGE_NAME="$ECR_REGISTRY/$ECR_REPOSITORY:latest"
echo "IMAGE_NAME: $IMAGE_NAME"
sudo docker pull $IMAGE_NAME

# 새 컨테이너 실행
sudo docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME
