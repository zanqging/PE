package com.jinzht.pro.model;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2015/10/15.
 */
public class CircleBean {


    /**
     * code : 2
     * msg :
     * data : [{"remain_likers_num":0,"remain_comment_num":0,"comment":[{"uid":16,"label_suffix":":","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/3cd6e48ae9b74f5a907868a3cf711e78.jpg","flag":false,"name":"陈生珠","content":"djsfn","id":6}],"flag":true,"name":"乔元培","content":"","datetime":"7分钟前","uid":14,"like":[{"name":"陈生珠","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/3cd6e48ae9b74f5a907868a3cf711e78.jpg","uid":16}],"photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg","is_like":false,"position":"金指投","pic":["http://www.jinzht.com:8000/media/feeling/2015/11/3e79851f2b114404b8ce150cbea8d0da.png","http://www.jinzht.com:8000/media/feeling/2015/11/abd1bd1dc3cf4d1dbd8cd059d003ac04.png"],"id":13}]
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        public DataEntity() {
            super();
            this.like = new ArrayList<>();
            this.comment = new ArrayList<>();
        }
        public DataEntity(String datetime, int id, String city, int uid, int remain_comment_num, boolean is_like, String content, String name, int remain_likers_num,
                          String photo, boolean flag, ArrayList<String> pics, String position) {
            this.datetime = datetime;
            this.id = id;
            this.addr = city;
            this.uid = uid;
            this.remain_comment_num = remain_comment_num;
            this.is_like = is_like;
            this.content = content;
            this.name = name;
            this.remain_likers_num = remain_likers_num;
            this.photo = photo;
            this.flag = flag;
            this.pic = pics;
            this.position = position;
        }
        public DataEntity(String photo, String content,int id,String name,String position,String addr, List<String> pic) {
            this.remain_likers_num = remain_likers_num;
            this.remain_comment_num = remain_comment_num;
            this.flag = flag;
            this.name = name;
            this.content = content;
            this.datetime = datetime;
            this.uid = uid;
            this.photo = photo;
            this.is_like = is_like;
            this.position = position;
            this.id = id;
            this.comment = comment;
            this.like = like;
            this.pic = pic;
            this.addr= addr;
        }

        public DataEntity(String photo,String content,int id, boolean is_like,  int remain_comment_num,String name, String datetime,
                 String addr, List<LikeEntity> like, List<CommentEntity> comment,int remain_likers_num, List<String> pic, String position, int uid,
                           boolean flag) {
            this.remain_likers_num = remain_likers_num;
            this.remain_comment_num = remain_comment_num;
            this.flag = flag;
            this.name = name;
            this.content = content;
            this.datetime = datetime;
            this.uid = uid;
            this.photo = photo;
            this.is_like = is_like;
            this.position = position;
            this.id = id;
            this.comment = comment;
            this.like = like;
            this.pic = pic;
            this.addr = addr;
            this.like = new ArrayList<>();
            this.comment = new ArrayList<>();
        }

        /**
         * remain_likers_num : 0
         * remain_comment_num : 0
         * comment : [{"uid":16,"label_suffix":":","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/3cd6e48ae9b74f5a907868a3cf711e78.jpg","flag":false,"name":"陈生珠","content":"djsfn","id":6}]
         * flag : true
         * name : 乔元培
         * content :
         * datetime : 7分钟前
         * uid : 14
         * like : [{"name":"陈生珠","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/3cd6e48ae9b74f5a907868a3cf711e78.jpg","uid":16}]
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg
         * is_like : false
         * position : 金指投
         * pic : ["http://www.jinzht.com:8000/media/feeling/2015/11/3e79851f2b114404b8ce150cbea8d0da.png","http://www.jinzht.com:8000/media/feeling/2015/11/abd1bd1dc3cf4d1dbd8cd059d003ac04.png"]
         * id : 13
         */

        private int remain_likers_num;
        private int remain_comment_num;
        private boolean flag;
        private String name;
        private String content;
        private String datetime;
        private int uid;
        private String photo;
        private boolean is_like;
        private String position;
        private int id;
        private List<CommentEntity> comment;
        private List<LikeEntity> like;
        private List<String> pic;
        private String addr;
        private ShareEntity share;

        public ShareEntity getShare() {
            return share;
        }

        public void setShare(ShareEntity share) {
            this.share = share;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setRemain_likers_num(int remain_likers_num) {
            this.remain_likers_num = remain_likers_num;
        }

        public void setRemain_comment_num(int remain_comment_num) {
            this.remain_comment_num = remain_comment_num;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setComment(List<CommentEntity> comment) {
            this.comment = comment;
        }

        public void setLike(List<LikeEntity> like) {
            this.like = like;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public int getRemain_likers_num() {
            return remain_likers_num;
        }

        public int getRemain_comment_num() {
            return remain_comment_num;
        }

        public boolean getFlag() {
            return flag;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getDatetime() {
            return datetime;
        }

        public int getUid() {
            return uid;
        }

        public String getPhoto() {
            return photo;
        }

        public boolean getIs_like() {
            return is_like;
        }

        public String getPosition() {
            return position;
        }

        public int getId() {
            return id;
        }

        public List<CommentEntity> getComment() {
            return comment;
        }

        public List<LikeEntity> getLike() {
            return like;
        }

        public List<String> getPic() {
            return pic;
        }
        public static class ShareEntity{
            private String title;
            private String img;
            private String url;
            private int id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
        public static class CommentEntity {
            public CommentEntity() {
                super();
            }

            public CommentEntity(String name,int uid, int id, String photo, boolean flag,  String content) {
                this.uid = uid;
                this.photo = photo;
                this.flag = flag;
                this.name = name;
                this.content = content;
                this.id = id;
            }

            public CommentEntity(String name,int uid, int id, String photo, boolean flag,  String content,String at_name,int at_uid) {
                this.uid = uid;
                this.photo = photo;
                this.flag = flag;
                this.name = name;
                this.content = content;
                this.id = id;
                this.at_name = at_name;
                this.at_uid = at_uid;
            }

            /**
             * uid : 16
             * label_suffix : :
             * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/3cd6e48ae9b74f5a907868a3cf711e78.jpg
             * flag : false
             * name : 陈生珠
             * content : djsfn
             * id : 6
             */

            private int uid;
            private String photo;
            private boolean flag;
            private String name;
            private String content;
            private int id;
            private String at_name;
            private int at_uid;

            public boolean isFlag() {
                return flag;
            }

            public String getAt_name() {
                return at_name;
            }

            public void setAt_name(String at_name) {
                this.at_name = at_name;
            }

            public int getAt_uid() {
                return at_uid;
            }

            public void setAt_uid(int at_uid) {
                this.at_uid = at_uid;
            }



            public void setUid(int uid) {
                this.uid = uid;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }
            public String getPhoto() {
                return photo;
            }

            public boolean getFlag() {
                return flag;
            }

            public String getName() {
                return name;
            }

            public String getContent() {
                return content;
            }

            public int getId() {
                return id;
            }
        }

        public static class LikeEntity {
            public LikeEntity() {
                super();
            }

            public LikeEntity(String name, String photo, int uid) {
                this.name = name;
                this.photo = photo;
                this.uid = uid;
            }

            /**
             * name : 陈生珠
             * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/3cd6e48ae9b74f5a907868a3cf711e78.jpg
             * uid : 16
             */

            private String name;
            private String photo;
            private int uid;

            public void setName(String name) {
                this.name = name;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public String getPhoto() {
                return photo;
            }

            public int getUid() {
                return uid;
            }
        }
    }
}
