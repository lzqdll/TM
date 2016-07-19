package com.tm.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User {
	private long id;
	private String usrName;
	private List<LeaveBalance> leaveBalances;
	private String usrPassword;
	private String displayName;
	private String email;
	private String employeeType;
	private boolean valid;
	private String distinguishedName;
	private User manager;
	private Date startWorkingTime;
	private String managerDistinguishedName;
	private Set<User> teamMembers;
	// private UserGroup uGroup;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrPassword() {
		return usrPassword;
	}

	public void setUsrPassword(String usrPassword) {
		this.usrPassword = usrPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@ManyToOne
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	@OneToMany(mappedBy = "user")
	public List<LeaveBalance> getLeaveBalances() {
		return leaveBalances;
	}

	public void setLeaveBalances(List<LeaveBalance> leaveBalances) {
		this.leaveBalances = leaveBalances;
	}

//	@OneToMany(mappedBy = "user",fetch=FetchType.LAZY)
//	public Set<ApplicationRecord> getApplicationRecord() {
//		return applicationRecord;
//	}

//	@OneToMany(mappedBy = "manager",fetch=FetchType.LAZY)
//	public Set<User> getTeamMembers() {
//		return teamMembers;
//	}

	public void setTeamMembers(Set<User> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	public String getManagerDistinguishedName() {
		return managerDistinguishedName;
	}

	public void setManagerDistinguishedName(String managerDistinguishedName) {
		this.managerDistinguishedName = managerDistinguishedName;
	}

	public Date getStartWorkingTime() {
		return startWorkingTime;
	}

	public void setStartWorkingTime(Date startWorkingTime) {
		this.startWorkingTime = startWorkingTime;
	}

}
