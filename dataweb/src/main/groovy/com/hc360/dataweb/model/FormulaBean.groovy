package com.hc360.dataweb.model

/**
 * Created by home on 2018/11/26.
 */
class FormulaBean {
    String name;
    String num;
//    int flag
    FormulaBean(){
        super();
    }

    FormulaBean(String name, String num ) {
        this.name = name
        this.num = num

    }

    @Override
    public String toString() {
        return "P4pBean{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
