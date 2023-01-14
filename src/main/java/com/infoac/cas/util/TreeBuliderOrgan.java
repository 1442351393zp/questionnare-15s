package com.infoac.cas.util;

import java.util.ArrayList;
import java.util.List;

import com.infoac.cas.dto.OrganVO;
import com.infoac.cas.dto.PermissionVO;

/*
 * 递归实现树数据的展示
 */
public class TreeBuliderOrgan {
	/**
     * 使用递归方法建树
     * @param
     * @return
     */
    public static List <OrganVO> buildByRecursive(List<OrganVO> organVO) {
        List<OrganVO> treesper = new ArrayList<OrganVO>();//全部数据
        for (OrganVO treeNode : organVO) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setIconSkin("");
            treesper.add(findChildren(treeNode,organVO));
        }
    }
        return treesper;
    }
 
    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static OrganVO findChildren(OrganVO treeNode,List<OrganVO> treeNodes) {
        for (OrganVO it : treeNodes) {
               it.setIconSkin("");
            if(treeNode.getId().equals(it.getPid())) {
            if (treeNode.getChildren()== null) {
                treeNode.setIconSkin("");
                treeNode.setChildren(new ArrayList<OrganVO>());
            }
            //是否还有子节点，如果有的话继续往下遍历，如果没有则直接返回
            treeNode.getChildren().add(findChildren(it,treeNodes));
 
         }
      }
        return treeNode;
    }

}
