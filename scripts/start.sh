#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/app/my-demo
JAR_NAME=$(ls -tr $REPOSITORY/*jar | tail -n 1)

echo ">>> JAR_NAME: $JAR_NAME"

chmod +x $JAR_NAME

echo ">>> $JAR_NAME 실행"

nohup java -jar \
      -Dspring.profiles.active=$MY_DEMO_ACTIVE_PROFILE \
      -Dspring.config.additional-location=/home/ec2-user/app/application-prod-db.properties \
      $JAR_NAME > $REPOSITORY/nohub.out 2>&1 &
