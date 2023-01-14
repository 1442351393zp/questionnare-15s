package com.infoac.cas.dto;

import java.util.List;

/**
 * 拖拽
 */
public class DragDTO {

	private String rid ;
    private List<PageDTO>  pageDTOList;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public List<PageDTO> getPageDTOList() {
        return pageDTOList;
    }

    public void setPageDTOList(List<PageDTO> pageDTOList) {
        this.pageDTOList = pageDTOList;
    }
}
