#!/bin/bash

. /etc/profile


if [ "${l_mode}" = "test" ];then
    cp /etc/hosts /etc/hosts.temp
    sed -i "s/.*$(hostname)/$local_ip $(hostname)/g" /etc/hosts.temp
	echo "$local_ip $(hostname)" >> /etc/hosts.temp
	echo "10.101.0.116 dp.jumaps.com" >> /etc/hosts.temp
	echo "10.101.0.116 bps.jumaps.com" >> /etc/hosts.temp
	echo "10.101.0.116 dvs.jumaps.com" >> /etc/hosts.temp
	echo "10.101.0.2 truckMatch.jumaps.com" >> /etc/hosts.temp
    cat /etc/hosts.temp > /etc/hosts
fi

cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

###### copy mode config ######

unalias cp
[ -z "${l_mode}" ] && l_mode=dev
[ -d "${CONF_DIR}/${l_mode}" ] && cp -rf ${CONF_DIR}/${l_mode}/* ${CONF_DIR} && cat ${CONF_DIR}/host.txt >> /etc/hosts

###### end ######

bash ${BIN_DIR}/start.sh

#touch /tmp/1.file
#tail -f /tmp/1.file

while true
do
    ps -ef| grep java |grep -v grep
    if [ $? -eq 0 ];then
        sleep 5
        continue
    fi
    break
done




