package com.xidian.bynadsdk.bean;

/**
 * Created by Administrator on 2019/10/29.
 */

public class BYNAdSDKADSBean {

    /**
     * title : ad_name
     * image : {"url":"http://img.daff9.cn/biyingniao/images/other/1911/5dd236e31ca31.png","width":"960","height":"1600"}
     * url :
     * deeplink :
     * jump_type : 1
     * item_info : {"item_id":"","activity_id":""}
     */

    private String title;
    private ImageBean image;
    private String url;
    private String deeplink;
    private int jump_type;
    private ItemInfoBean item_info;

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

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

    public ItemInfoBean getItem_info() {
        return item_info;
    }

    public void setItem_info(ItemInfoBean item_info) {
        this.item_info = item_info;
    }

    public static class ImageBean {
        /**
         * url : http://img.daff9.cn/biyingniao/images/other/1911/5dd236e31ca31.png
         * width : 960
         * height : 1600
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

    public static class ItemInfoBean {
        /**
         * item_id :
         * activity_id :
         */

        private String item_id;
        private String activity_id;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }
    }

}
