package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/26.
 */
public class ProductCreatLineRequestBean extends BaseRequestBean{
    /**
     * comId : 1
     * prolineType : PROLINE_FINAL
     * prolineName :
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int comId;
        private String prolineType;
        private String prolineName;

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setProlineType(String prolineType) {
            this.prolineType = prolineType;
        }

        public void setProlineName(String prolineName) {
            this.prolineName = prolineName;
        }

        public int getComId() {
            return comId;
        }

        public String getProlineType() {
            return prolineType;
        }

        public String getProlineName() {
            return prolineName;
        }
    }
}
