#!/bin/bash

# 디렉토리로 이동
cd /home/ubuntu/deployment

# 기존 컨테이너 중지 및 삭제
sudo docker stop $(sudo docker ps -a -q --filter "name=ohhanahana-app") || true
sudo docker rm $(sudo docker ps -a -q --filter "name=ohhanahana-app") || true

# 최신 이미지 가져오기de
sudo docker pull $ECR_REGISTRY/$ECR_REPOSITORY:latest

# 새 컨테이너 실행
sudo docker run -d --name ohhanahana-app -p 8080:8080 $ECR_REGISTRY/$ECR_REPOSITORY:latest
