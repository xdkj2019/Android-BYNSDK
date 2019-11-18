package com.xidian.bynadsdk.splash;

/**
 * Created by Administrator on 2019/10/21.
 */

public class SplashParameter {
    private int height;
    private long timeMillis;
    private boolean isShowSkip;
    private int adzone_id;
    private int width;
    private String device_value;
    private String device_type;

    public int getAdzone_id() {
        return adzone_id;
    }

    public int getWidth() {
        return width;
    }

    public String getDevice_value() {
        return device_value;
    }

    public String getDevice_type() {
        return device_type;
    }

    public int getHeight() {
        return height;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public boolean isShowSkip() {
        return isShowSkip;
    }

    private SplashParameter() {
    }
    public static class Builder{
        private int height;
        private long timeMillis=3000;
        private boolean isShowSkip=true;
        private int adzone_id;
        private int width;
        private String device_value;
        private String device_type;
        public Builder() {
        }
        public SplashParameter.Builder setAdzoneId(int adzone_id) {
            this.adzone_id = adzone_id;
            return this;
        }
        public SplashParameter.Builder setDeviceValue(String device_value) {
            this.device_value = device_value;
            return this;
        }
        public SplashParameter.Builder setDeviceType(String device_type) {
            this.device_type = device_type;
            return this;
        }

        public SplashParameter.Builder setSize(int width, int height) {
            this.width=width;
            this.height = height;
            return this;
        }

        public SplashParameter.Builder setTimeMillis(long timeMillis) {
            this.timeMillis = timeMillis;
            return this;
        }

        public SplashParameter.Builder setShowSkip(boolean showSkip) {
            isShowSkip = showSkip;
            return this;
        }
        public SplashParameter build(){
            SplashParameter splashParameter = new SplashParameter();
            splashParameter.height=this.height;
            splashParameter.width=this.width;
            splashParameter.device_type=this.device_type;
            splashParameter.device_value=this.device_value;
            splashParameter.adzone_id=this.adzone_id;
            splashParameter.isShowSkip=this.isShowSkip;
            splashParameter.timeMillis = this.timeMillis;
            return splashParameter;
        }
    }


}

