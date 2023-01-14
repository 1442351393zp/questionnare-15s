package com.infoac.cas.util;

import java.util.ArrayList;
import java.util.List;

import com.infoac.cas.dto.PermissionVO;

/*
 * 递归实现树数据的展示
 */
public class TreeBulider {
	/**
     * 使用递归方法建树
     * @param treeNodes
     * @return
     */
    public static List <PermissionVO> buildByRecursive(List<PermissionVO> permissionVO) {
        List<PermissionVO> treesper = new ArrayList<PermissionVO>();//全部数据
        for (PermissionVO treeNode : permissionVO) {
            if ("0".equals(treeNode.getPid())) {
            treesper.add(findChildren(treeNode,permissionVO));
        }
    }
        return treesper;
    }
 
    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static PermissionVO findChildren(PermissionVO treeNode,List<PermissionVO> treeNodes) {
        for (PermissionVO it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
            if (treeNode.getChildren()== null) {
                treeNode.setChildren(new ArrayList<PermissionVO>());
            }
            //是否还有子节点，如果有的话继续往下遍历，如果没有则直接返回
            treeNode.getChildren().add(findChildren(it,treeNodes));
 
         }
      }
        return treeNode;
    }

}
