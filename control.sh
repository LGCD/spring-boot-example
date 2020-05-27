#!/bin/bash

# 新项目需修改
APP_NAME="hubble"

JAVA_HOME=/usr/local/jdk1.8.0_65
APP_DIR=`pwd`
LOG_DIR="/home/xiaoju/logs/${APP_NAME}"
JAVA_MEM="-server -Xmx4g -Xms4g -Xmn2g -Xss256k"
JAVA_OPTS="${JAVA_MEM} -XX:SurvivorRatio=4 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=31 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -Xloggc:${LOG_DIR}/gc.log"
G1_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:GCPauseIntervalMillis=200 -XX:MaxTenuringThreshold=15   -XX:ParallelGCThreads=40"
JAVA_OPTS="${JAVA_OPTS} ${G1_OPTS}"


java=${JAVA_HOME}/bin/java
pidDir=${APP_DIR}/pids
logDir=${APP_DIR}/logs
pidFile=${APP_DIR}/pids/pid

function getPid() {
    if [ -f ${pidFile} ]
    then
        cat ${pidFile}
    fi
}

function checkPid() {
    pid=$(getPid)
    if [ "x_" != "x_${pid}" ]
    then
        running=$(ps -p ${pid}|grep -v "PID TTY" |wc -l)
        return ${running}
    fi
    return 0
}

function start() {
    mkdir -p ${pidDir} &>/dev/null
    mkdir -p ${logDir} &>/dev/null
    checkPid
    if [ $? -ne 0 ]
    then
        local pid=$(getPid)
        echo "${APP_NAME} is started, pid=${pid}"
        exit 0
    fi
    nohup ${java} -jar $JAVA_OPTS ${APP_DIR}/${APP_NAME}.jar >> ${logDir}/${APP_NAME}.out 2>${logDir}/${APP_NAME}.error &
    pid=$!
    echo ${pid} > ${pidFile}
    checkPid
    if [ $? -eq 0 ]
    then
        echo "${APP_NAME} start failed, please check"
        exit 1
    fi
    echo "${APP_NAME} start ok, pid=${pid}"
    exit 0
}

function stop() {
    for (( i = 0; i < 60; i++ )); do
        checkPid
        if [ $? -eq 0 ]; then
            echo "${APP_NAME} is stoped"
            exit 0
        fi
        local pid=$(getPid)
        if [ ${pid} == "" ]; then
            echo "${APP_NAME} is stoped, can't find pid on ${pidfile}"
            exit 0
        fi
        kill ${pid} &>/dev/null
        checkPid
        if [ $? -eq 0 ]; then
            echo "${APP_NAME} stop ok!"
            exit 0
        fi
        sleep 1
    done
    echo "stop timeout(60s)"
    exit 1
}

#检查服务状态
function status() {
    checkPid
    local running=$?
    if [ ${running} -ne 0 ]; then
        local pid=$(getPid)
        echo "${APP_NAME} is started, pid=${pid}"
    else
        echo "${APP_NAME} is stoped"
    fi
    exit 0
}

operate=$1
case $operate in
    "start" )
        start
        ;;
    "stop" )
        stop
        ;;
    "status" )
        status
        ;;
    "restart" )
        stop
        start
        ;;
    * )
        echo "unknow operate"
        exit 1
        ;;
esac
