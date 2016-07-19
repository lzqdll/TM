package com.tm.dao;

import java.util.List;
import com.tm.entity.User;

public interface UserDAO {
	public User get(long id);

	public User getByName(String name);

	public void saveorupdate(User User);

	public void delete(User User);

	public int getTotalRows();

	public List<User> findALL();

	public List<User> findbyUsernameAndPassword(User user);
	public List<User> findUserbyManager(User manager,String searchSQL);
}
