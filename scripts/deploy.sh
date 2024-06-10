#!/bin/bash

# 환경변수 로드
source /home/ubuntu/deployment/scripts/env.sh

cd /home/ubuntu/deployment

# 기존에 실행 중인 컨테이너 중지
sudo docker stop $(docker ps -a -q --filter "name=ohhanahana-app") || true

# 기존에 중지된 컨테이너 삭제
sudo docker rm $(docker ps -a -q --filter "name=ohhanahana-app") || true

# 최신 Docker 이미지 가져오기
sudo docker pull $DOCKERHUB_USERNAME/$DOCKERHUB_REPO:latest

# 새로운 컨테이너 실행
docker run -d --name ohhanahana-app -p 8080:8080 -e SPRING_PROFILE=main -e TZ=Asia/Seoul $DOCKERHUB_USERNAME/$DOCKERHUB_REPO:latest