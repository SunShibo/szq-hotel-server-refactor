package com.szq.hotel.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ObjectUtil {

    /*
       将object对象转换为BigDecimal
    */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }
    /*
        将object对象转换为Integer
     */
    public static Integer getIntegerByObject(Object object){
        Integer in = null;

        if(object!=null){
            if(object instanceof Integer){
                in = (Integer)object;
            }else if(object instanceof String){
                in = Integer.parseInt((String)object);
            }else if(object instanceof BigDecimal){
                in = ((BigDecimal)object).intValue();
            }else if(object instanceof Long){
                in = ((Long)object).intValue();
            }
        }

        return in;
    }
}
