#!/bin/bash
DEPLOY_DIR=$(dirname $(pwd))
CONF_DIR="$DEPLOY_DIR/conf"
LIB_DIR="$DEPLOY_DIR/lib"
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
echo $LIB_DIR
echo $LIB_JARS
JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
nohup java $JAVA_MEM_OPTS -classpath $CONF_DIR:$LIB_JARS:$LIB_DIR com.juma.tgm.main.CustomerInfoTransformer > ./out.log 2>&1 &