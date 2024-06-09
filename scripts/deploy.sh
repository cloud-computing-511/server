#!/bin/bash

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
IMAGE_NAME="992382726653.dkr.ecr.ap-northeast-2.amazonaws.com/ohhanahana-temp:latest"
echo "IMAGE_NAME: $IMAGE_NAME"
sudo docker pull $IMAGE_NAME

# 새 컨테이너 실행
sudo docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME
