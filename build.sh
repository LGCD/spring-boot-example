#!/bin/bash
export JAVA_HOME=/usr/local/jdk1.8.0_65
export PATH=$JAVA_HOME/bin:$PATH
#
#如需使用其他版本的maven请联系scm，打开并修改下面的变量
#export MAVEN_HOME=xxxx    (如使用系统默认的不需要设置，默认maven-3.3.3)
#export PATH=$MAVEN_HOME/bin:$PATH
#

# 新项目需修改
project="spring-boot-example"

if [ $# -ne 1 ];then
    echo "Usage: $0 [test|dev|pro]"
    echo "      test - Test environment, eg. your own computer."
    echo "      dev  - Development environment, eg. 10.93.21.54"
    echo "      pro - Product environment, eg. 10.93.21.52"
    exit 0
fi

ENV=$1

mvn clean package -P${ENV} -Dmaven.test.skip=true

ret=$?
if [ $ret -ne 0 ];then
    echo "===== maven build failure ====="
    exit $ret
else
    echo -n "===== maven build successfully! ====="
fi

rm -rf output
mkdir -p output
cp control.sh output/  # 拷贝control.sh脚本 至output目录下
mv target/${project}.jar output/  #拷贝目标war包或者jar包等 至output目录下
cd output/
