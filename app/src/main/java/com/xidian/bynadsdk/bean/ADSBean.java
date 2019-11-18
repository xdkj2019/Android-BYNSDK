package com.xidian.bynadsdk.bean;

/**
 * Created by Administrator on 2019/10/29.
 */

public class ADSBean {

    /**
     * title : 标题标题标题
     * image : {"url":"http://img.haodanku.com/0_602041258520_1567594515.jpg","width":"720","height":"1080"}
     * url : https://s.click.taobao.com/Iysod1w
     * deep_link :
     * jump_type : 1
     */

    private String title;
    private ImageBean image;
    private String url;
    private String deep_link;
    private int jump_type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeep_link() {
        return deep_link;
    }

    public void setDeep_link(String deep_link) {
        this.deep_link = deep_link;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

    public static class ImageBean {
        /**
         * url : http://img.haodanku.com/0_602041258520_1567594515.jpg
         * width : 720
         * height : 1080
         */

        private String url;
        private String width;
        private String height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }
}
