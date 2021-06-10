#!/bin/bash

set -e
cd ${MIST_HOME}

MASTER_ID=$(cat /proc/1/cgroup | grep "/docker/" | head -n 1 | awk -F "/" '{print $NF}')
export MIST_OPTS="$MIST_OPTS -Dmist.workers.docker.auto-master-network.container-id=$MASTER_ID"
run ls -la ./bin/
run ls -la
exec ./bin/mist-master start --debug true $@
