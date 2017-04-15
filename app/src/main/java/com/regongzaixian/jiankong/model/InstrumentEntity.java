package com.regongzaixian.jiankong.model;

import java.io.Serializable;

/**
 * Created by tony on 2017/4/15.
 */

public class InstrumentEntity implements Serializable {


    /**
     * name : 测试辐射仪1
     * model : T-RADIATOR-1
     * serialnumber : 513658713189732158
     * temperaturerange : 10-300
     * humidityrange : 20-100
     * temperatureindicator : SAT554
     * status : null
     * meantemperature : 55
     * meanhumidity : 40
     * lang : en_US
     * template : default
     * id_ref : null
     * id : 1
     * createdAt : 2017-04-14T13:03:41.000Z
     * updatedAt : 2017-04-14T13:05:57.000Z
     * temperaturerangemin : 10
     * temperaturerangemax : 300
     * humidityrangemin : 20
     * humidityrangemax : 100
     */

    private String name;
    private String model;
    private String serialnumber;
    private String temperaturerange;
    private String humidityrange;
    private String temperatureindicator;
    private String status;
    private int meantemperature;
    private int meanhumidity;
    private String lang;
    private String template;
    private Object id_ref;
    private int id;
    private String createdAt;
    private String updatedAt;
    private String temperaturerangemin;
    private String temperaturerangemax;
    private String humidityrangemin;
    private String humidityrangemax;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getTemperaturerange() {
        return temperaturerange;
    }

    public void setTemperaturerange(String temperaturerange) {
        this.temperaturerange = temperaturerange;
    }

    public String getHumidityrange() {
        return humidityrange;
    }

    public void setHumidityrange(String humidityrange) {
        this.humidityrange = humidityrange;
    }

    public String getTemperatureindicator() {
        return temperatureindicator;
    }

    public void setTemperatureindicator(String temperatureindicator) {
        this.temperatureindicator = temperatureindicator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMeantemperature() {
        return meantemperature;
    }

    public void setMeantemperature(int meantemperature) {
        this.meantemperature = meantemperature;
    }

    public int getMeanhumidity() {
        return meanhumidity;
    }

    public void setMeanhumidity(int meanhumidity) {
        this.meanhumidity = meanhumidity;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Object getId_ref() {
        return id_ref;
    }

    public void setId_ref(Object id_ref) {
        this.id_ref = id_ref;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTemperaturerangemin() {
        return temperaturerangemin;
    }

    public void setTemperaturerangemin(String temperaturerangemin) {
        this.temperaturerangemin = temperaturerangemin;
    }

    public String getTemperaturerangemax() {
        return temperaturerangemax;
    }

    public void setTemperaturerangemax(String temperaturerangemax) {
        this.temperaturerangemax = temperaturerangemax;
    }

    public String getHumidityrangemin() {
        return humidityrangemin;
    }

    public void setHumidityrangemin(String humidityrangemin) {
        this.humidityrangemin = humidityrangemin;
    }

    public String getHumidityrangemax() {
        return humidityrangemax;
    }

    public void setHumidityrangemax(String humidityrangemax) {
        this.humidityrangemax = humidityrangemax;
    }
}
