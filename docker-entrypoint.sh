#!/bin/bash

set -e
cd ${MIST_HOME}

MASTER_ID=$(cat /proc/1/cgroup | grep "/docker/" | head -n 1 | awk -F "/" '{print $NF}')
#MASTER_ID=$(cat /proc/11/cgroup | awk 1 ORS='_')
#MASTER_ID+="BREAK"
#MASTER_ID+=$(cat /proc/12/cgroup | awk 1 ORS='_')
#MASTER_ID+="BREAK"
#MASTER_ID+=$(cat /proc/13/cgroup | awk 1 ORS='_')

#if [ -z "$MASTER_ID" ]; then
#  MASTER_ID=$(cat /proc/11/cgroup | grep "/ecs/" | head -n 1 | awk -F "/" '{print $NF}')
#fi

export MIST_OPTS="$MIST_OPTS -Dmist.workers.docker.auto-master-network.container-id=$MASTER_ID"
exec ./bin/mist-master start --debug true $@
