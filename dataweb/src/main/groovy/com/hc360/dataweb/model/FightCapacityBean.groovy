package com.hc360.dataweb.model

/**
 * Created by home on 2017/2/6.
 * 战斗力的po
 */
class FightCapacityBean {

    String name;
    List<FightCapacityOneBean> fightInfo;

    FightCapacityBean(String name) {
        this.name = name
    }
}
