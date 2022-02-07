#!/bin/bash

echo "before lambda"

#aws ecs describe-task-definition --task-definition mist | jq '.taskDefinition'
cat appspec_template.yaml < CONTAINER_NAME="mist"

