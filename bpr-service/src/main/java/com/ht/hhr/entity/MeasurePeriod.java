package com.ht.hhr.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Map;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 21:24
 * @description
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MeasurePeriod {

    //'早上(8:00前)', '上午(8:00~11:00)', '中午(11:00~13:00)', '下午(13:00~18:00)', '晚上(18:00后)'
    ZERO_EIGHT(1, "早上", "00:00~8:00"),
    EIGHT_ELEVEN(2, "上午", "08:00~11:00"),
    ELEVEN_THIRTEEN(3, "中午", "11:00~13:00"),
    THIRTEEN_EIGHTEEN(4, "下午", "13:00~18:00"),
    EIGHTEEN_ZERO(5, "晚上", "18:00~00:00");

    private Integer code;
    private String name;
    private String description;

    MeasurePeriod(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MeasurePeriod from(Object object) {
        if(object instanceof Map){
            return from((Map) object);
        }
        if(object instanceof String){
            return from((String) object);
        }
        return null;
    }

    public static MeasurePeriod from(Map name) {
        Integer code = (Integer) name.get("code");
        MeasurePeriod period = getPeriod(code);
        return period;
    }

    private static MeasurePeriod getPeriod(Integer code) {
        if(code == null) return null;
        MeasurePeriod[] values = values();
        for (MeasurePeriod value : values) {
            if(value.code == code){
                return value;
            }
        }
        return null;
    }

    public static MeasurePeriod from(String name) {
        MeasurePeriod[] values = values();
        for (MeasurePeriod value : values) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public static MeasurePeriod getPeriod(int code) {
        MeasurePeriod[] values = values();
        if (code != 0) {
            for (MeasurePeriod value : values) {
                if (value.getCode() == code) {
                    return value;
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
