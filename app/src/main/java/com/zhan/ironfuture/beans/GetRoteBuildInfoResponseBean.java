package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2016/5/23.
 */
public class GetRoteBuildInfoResponseBean extends BaseResponseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int freeSum;
        private int lineStateSum;
        private int sitefreeSum;

        private List<ComListEntity> comList;
        private List<LinePriceListEntity> linePriceList;

        public int getFreeSum() {
            return freeSum;
        }

        public void setFreeSum(int freeSum) {
            this.freeSum = freeSum;
        }

        public int getLineStateSum() {
            return lineStateSum;
        }

        public void setLineStateSum(int lineStateSum) {
            this.lineStateSum = lineStateSum;
        }

        public int getSitefreeSum() {
            return sitefreeSum;
        }

        public void setSitefreeSum(int sitefreeSum) {
            this.sitefreeSum = sitefreeSum;
        }

        public List<ComListEntity> getComList() {
            return comList;
        }

        public void setComList(List<ComListEntity> comList) {
            this.comList = comList;
        }

        public List<LinePriceListEntity> getLinePriceList() {
            return linePriceList;
        }

        public void setLinePriceList(List<LinePriceListEntity> linePriceList) {
            this.linePriceList = linePriceList;
        }

        public static class ComListEntity {
            private int groupId;
            private String groupName;

            private List<ComlistEntity> comlist;

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public List<ComlistEntity> getComlist() {
                return comlist;
            }

            public void setComlist(List<ComlistEntity> comlist) {
                this.comlist = comlist;
            }

            public static class ComlistEntity {
                private int id;
                private String name;
                private String comLogo;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getComLogo() {
                    return comLogo;
                }

                public void setComLogo(String comLogo) {
                    this.comLogo = comLogo;
                }
            }
        }

        public static class LinePriceListEntity {
            private String rodeGrade;
            private int lineSum;
            private int linePrice;

            public String getRodeGrade() {
                return rodeGrade;
            }

            public void setRodeGrade(String rodeGrade) {
                this.rodeGrade = rodeGrade;
            }

            public int getLineSum() {
                return lineSum;
            }

            public void setLineSum(int lineSum) {
                this.lineSum = lineSum;
            }

            public int getLinePrice() {
                return linePrice;
            }

            public void setLinePrice(int linePrice) {
                this.linePrice = linePrice;
            }
        }
    }
}
