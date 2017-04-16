package com.regongzaixian.jiankong.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tony on 2017/4/16.
 */

public class InstrumentDataEntity implements Serializable {


    private List<String> Time;
    private List<Float> TempSV;
    private List<Float> TempPV;
    private List<Float> HumSV;
    private List<Float> HumPV;

    public List<String> getTime() {
        return Time;
    }

    public void setTime(List<String> Time) {
        this.Time = Time;
    }

    public List<Float> getTempSV() {
        return TempSV;
    }

    public void setTempSV(List<Float> TempSV) {
        this.TempSV = TempSV;
    }

    public List<Float> getTempPV() {
        return TempPV;
    }

    public void setTempPV(List<Float> TempPV) {
        this.TempPV = TempPV;
    }

    public List<Float> getHumSV() {
        return HumSV;
    }

    public void setHumSV(List<Float> HumSV) {
        this.HumSV = HumSV;
    }

    public List<Float> getHumPV() {
        return HumPV;
    }

    public void setHumPV(List<Float> HumPV) {
        this.HumPV = HumPV;
    }
}
