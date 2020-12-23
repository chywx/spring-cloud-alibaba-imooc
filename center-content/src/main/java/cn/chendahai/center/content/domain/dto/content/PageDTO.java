package cn.chendahai.center.content.domain.dto.content;/**
 * @author chy
 * @date 2020/12/23 0023 下午 16:25
 * Description：
 */

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/12/23 0023
 */
public class PageDTO implements Serializable {

    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private Integer startIndex;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if (pageSize > 100) {
            return 100;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartIndex() {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }

    @Override
    public String toString() {
        return "PageCriteria{" +
            "pageNo=" + pageNo +
            ", pageSize=" + pageSize +
            ", startIndex=" + startIndex +
            '}';
    }
}
