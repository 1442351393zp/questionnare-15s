package com.infoac.cas.util;

import java.util.ArrayList;
import java.util.List;

import com.infoac.cas.dto.OrganVO;
import com.infoac.cas.dto.PermissionVO;
import com.infoac.cas.dto.TemplateVO;

/*
 * 递归实现树数据的展示
 */
public class TreeBuliderTemplate {
	/**
     * 使用递归方法建树
     * @param treeNodes
     * @return
     */
    public static List <TemplateVO> buildByRecursive(List<TemplateVO> templateVO) {
        List<TemplateVO> treesper = new ArrayList<TemplateVO>();//全部数据
        for (TemplateVO treeNode : templateVO) {
            if ("0".equals(treeNode.getTypepid())) {
            treesper.add(findChildren(treeNode,templateVO));
        }
    }
        return treesper;
    }
 
    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static TemplateVO findChildren(TemplateVO treeNode,List<TemplateVO> treeNodes) {
        for (TemplateVO it : treeNodes) {
            if(treeNode.getTypeid().equals(it.getTypepid())) {
            if (treeNode.getChildren()== null) {
                treeNode.setChildren(new ArrayList<TemplateVO>());
            }
            //是否还有子节点，如果有的话继续往下遍历，如果没有则直接返回
            treeNode.getChildren().add(findChildren(it,treeNodes));
 
         }
      }
        return treeNode;
    }

}
