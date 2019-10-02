package com.kc.jbt.service.entity;

import java.util.Arrays;

public enum MemberType {

    Platinum(50000, 0),
    Gold(30000, 50000),
    Silver(15000, 30000),
    None(0, 15000);

    private Integer from;
    private Integer to;

    private MemberType(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public static MemberType getMemberType(Integer salary) {
        if (salary == null || salary < None.from || salary < None.to) {
            return None;
        }

        if (salary > Platinum.from) {
            return Platinum;
        }

        return Arrays.asList(Gold, Silver)
                .stream()
                .filter(mem -> salary > mem.from && salary < mem.to)
                .findFirst()
                .orElse(None);
    }
}
