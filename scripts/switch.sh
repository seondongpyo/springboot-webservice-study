#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 port : $IDLE_PORT"
    echo "> port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
    # ->  하나의 문장을 만들어 파이프라인(|)으로 넘겨주기 위해 echo를 사용함
    #     엔진엑스가 변경할 프록시 주소를 생성함
    #     쌍따옴표("")를 사용해야 하며, 사용하지 않으면 $service_url을 그대로 인식하지 못하고 변수를 찾게 됨

    # | sudo tee /etc/nginx/conf.d/service-url.inc : 앞에서 넘겨준 문장을 service-url.inc에 덮어쓺

    echo "> 엔진엑스 Reload"
    sudo service nginx reload
    # ->  엔진엑스의 설정을 다시 불러옮 (!= restart)
    #     restart는 잠시 끊기는 현상이 있지만, reload는 끊김 없이 다시 불러옮
    #     다만, 중요한 설정들은 반영되지 않으므로 restart를 사용해야 함
    #     여기선 외부의 설정 파일인 service-url을 다시 불러오는 거라 reload로 가능함
}