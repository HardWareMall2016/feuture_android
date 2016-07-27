package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class TalentMarketQueryResponseBean extends BaseResponseBean {
    /**
     * code : 0
     * message : 成功
     * serverTime : 1463556888463
     * data : {"salarCriteria":[{"id":221,"name":"0000-4000"},{"id":222,"name":"4000-8000"},{"id":223,"name":"8000-12000"},{"id":224,"name":"12000-16000"},{"id":225,"name":"16000-0000"}],"comCriteria":[{"id":1,"name":"苹果ccc"},{"id":2,"name":"苹果ccc"},{"id":3,"name":"苹果bb"},{"id":4,"name":"苹果ccc"},{"id":5,"name":"苹果ccc"},{"id":6,"name":"苹果ccc"},{"id":7,"name":"苹果bbb"},{"id":8,"name":"苹果ccc"}],"postCriteria":[{"id":1,"name":"企业法人"},{"id":2,"name":"管理员"},{"id":3,"name":"仓库部门经理"},{"id":4,"name":"仓库部门职工"},{"id":5,"name":"原料部门经理"},{"id":6,"name":"原料部门职工"},{"id":7,"name":"二级配方管理员"},{"id":8,"name":"生产部门经理"},{"id":9,"name":"产品配方管理"},{"id":10,"name":"生产部门职工"},{"id":11,"name":"基建部门经理"},{"id":12,"name":"基建职工"},{"id":13,"name":"物流部门经理"},{"id":14,"name":"物流部门职工"}]}
     */


    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 221
         * name : 0000-4000
         */

        private List<SalarCriteriaEntity> salarCriteria;
        /**
         * id : 1
         * name : 苹果ccc
         */

        private List<ComCriteriaEntity> comCriteria;
        /**
         * id : 1
         * name : 企业法人
         */

        private List<PostCriteriaEntity> postCriteria;

        public void setSalarCriteria(List<SalarCriteriaEntity> salarCriteria) {
            this.salarCriteria = salarCriteria;
        }

        public void setComCriteria(List<ComCriteriaEntity> comCriteria) {
            this.comCriteria = comCriteria;
        }

        public void setPostCriteria(List<PostCriteriaEntity> postCriteria) {
            this.postCriteria = postCriteria;
        }

        public List<SalarCriteriaEntity> getSalarCriteria() {
            return salarCriteria;
        }

        public List<ComCriteriaEntity> getComCriteria() {
            return comCriteria;
        }

        public List<PostCriteriaEntity> getPostCriteria() {
            return postCriteria;
        }

        public static class SalarCriteriaEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class ComCriteriaEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class PostCriteriaEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
