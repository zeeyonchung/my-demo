#!/usr/bin/env bash

result=$(curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:8080/health)

if [[ "$result" =~ "200" ]]; then
  exit 0
else
  exit 1
fi
