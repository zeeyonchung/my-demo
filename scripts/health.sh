#!/usr/bin/env bash

for RETRY_COUNT in {1..15}
do
  RESULT=$(curl --silent --output /dev/null --write-out "%{http_code}" http://127.0.0.1:8080/health)

  if [[ "$RESULT" -eq "200" ]]
  then
    echo "App is healthy"
    exit 0
  else
    echo "App is not healthy or unknown: ${RESULT}"
  fi

  echo "Retry checking health..."
  sleep 10
done

echo "App is not healthy..."
exit 1
