package com.infoac.cas.service.impl;

import com.infoac.cas.dao.UserDao;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.UserVO;
import com.infoac.cas.service.UserService;
import com.infoac.cas.util.DateUtil;
import com.infoac.cas.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserDao userDao;

	@Override
	public List<UserDTO> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public List<SelectUserDTO> getSelectUser(String reelId) {
		return userDao.getSelectUser(reelId);
	}

	@Override
	@Transactional
	public void saveSelectUser(String reelId, String userId) {
		String date = DateUtil.getCurrentDateStr();
		String[] userIds = userId.split(",");
		/*List<SelectUserDTO> list = new ArrayList<>();
		SelectUserDTO vo = null;
		for (int i = 0; i < userIds.length; i++) {
			vo = new SelectUserDTO();
			vo.setId(StringUtil.get32UUID());
			vo.setReelId(reelId);
			vo.setUserId(userIds[i]);
			list.add(vo);
		}*/
		userDao.delSelectUser(reelId);
		for (int i = 0; i < userIds.length; i++) {
		    String username = userDao.getUserName(userIds[i]);
			userDao.saveSelectUser2(StringUtil.get32UUID(),reelId,userIds[i],username);
		}
		//userDao.saveSelectUser(list);
		
	}
	
	@Override
	public List<UserDTO> finduserlist(UserVO uservo) {
		return userDao.finduserlist(uservo);
	}
	
	@Override
	public Long findUserCount(UserVO uservo) {
		return userDao.findUserCount(uservo);
	}

	@Override
	public List<UserDTO> findUser(String username, String password) {
		return userDao.findUser(username,password);
	}
	@Override
	public void userAdd(UserDTO user) {
		UserDTO users = new UserDTO();
		users.setId(StringUtil.get32UUID());
		users.setUsername(user.getUsername());
		users.setPassword(user.getPassword());
		users.setNickname(user.getNickname());
		users.setPhone(user.getPhone());
		users.setSex(user.getSex());
		users.setEmail(user.getEmail());
		users.setDescribe(user.getDescribe());
		userDao.insertuser(users);
	}
	@Override
	public void userupdate(UserDTO user) {
		userDao.updateuser(user);
	}
	
	@Override
	public void userupdelete(List<String> list) {
		 userDao.deleteusers(list);
	}

	@Override
	public void userdelete(String id) {
		// TODO Auto-generated method stub
		userDao.deleteuser(id);
		
	}

    @Override
    public void userdeleteall() {
        userDao.userdeleteall();
    }

    @Override
	public UserDTO userFind(String userid) {
		return userDao.userFind(userid);
	}

	@Override
	public void deleteUserRole(String userid) {
		// TODO Auto-generated method stub
		userDao.deleteUserRole(userid);
	}

    @Override
    public void deleteUserRoleall() {
        userDao.deleteUserRoleall();
    }

    @Override
	public void saveroleUser(UserRoleKey urk) {
		// TODO Auto-generated method stub
		 userDao.saveroleUser(urk);
	}

	@Override
	public void addUser(UserDTO user) {
		userDao.insertuser(user);
	}

    @Override
    public List<OrganVO> listAllDepartment(String parentId,String reelId,String isLimit) {
        List<OrganVO> departmentList = this.listSubDepartmentByParentId(parentId);
        for (OrganVO depar : departmentList) {
            List<OrganVO> all=null;
            if(StringUtils.isBlank(depar.getOrganid())) {
                //部门
                depar.setIconSkin("fu");
                List<OrganVO> users = userDao.findBydeptid(depar.getId());
                if("0".equals(isLimit)){
                    List<SelectUserDTO> selectUser = userDao.getSelectUser(reelId);
                    for (OrganVO user : users) {
                        //选中状态
                        for (SelectUserDTO selectUserDTO : selectUser) {
                            String userId = selectUserDTO.getUserId();
                            if(userId.equals(user.getId())){
                                user.setChecked("true");
                            }
                        }
                    }
                }
                all= this.listAllDepartment(depar.getId(),reelId,isLimit);
                all.addAll(users);
            }
            depar.setChildren(all);
        }
        return departmentList;
    }

    public List<OrganVO> listSubDepartmentByParentId(String pid){
        return userDao.findForList(pid);
    }

    @Override
    public List<SelectDTO> getSelectUserName(String reelId) {
        return userDao.getSelectUserName(reelId);
    }

	@Override
	public UserDTO userMessage(String username) {
		return userDao.userMessage(username);
	}

	@Override
	public void addUserList(UserDTO userDTOS) {
		userDao.addUserList(userDTOS);
	}

	@Override
	public void saveroleUserList(UserRoleKey urkList) {
		userDao.saveroleUserList(urkList);
	}


	@Override
    public void updateuserismanage(UserDTO user) {
        userDao.updateuserismanage(user);
    }


    @Override
    public UserDTO findUserByuid(String userid) {
        return userDao.findUserByuid(userid);
    }
}
