#!/usr/bin/env bash

CURRENT_PID=$(pgrep -fl demo | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo ">>> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi
