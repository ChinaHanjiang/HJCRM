package com.chinahanjiang.crm.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.chinahanjiang.crm.security.support.MyUserDetails;

/**
 * 用户 2016-8-15
 * 
 * @author tree
 *
 */
@Entity
@Table(name = "user")
public class User implements MyUserDetails, java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String cardName; // 登陆名

	private String userName;// 中文用户名

	private String userPassword;

	private String duty;

	private String mobilephone;

	private String email;

	private int sex;

	private int isDelete; /* 0-删除,1-没删除 */

	private String remarks;

	private Timestamp createTime;

	private Timestamp updateTime;

	private Department department;

	private Set<UserRoles> userRoles = new HashSet<UserRoles>(0);

	private Boolean enabled; /* false-禁用，true-启用 */

	private Boolean issys;// 是否超级用户

	private String subSystem;// 该用户所负责的子系统

	// 实现了UserDetails之后的相关变量
	private String password;
	private String username;
	private Set<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;

	public User() {

		this.isDelete = 1;
		this.enabled = true;
	}

	public User(int id, String cardName, String userName, String userPassword,
			String duty, String mobilephone, String email, int sex,
			int isDelete, String remarks, Timestamp createTime,
			Timestamp updateTime, Department department,
			Set<UserRoles> userRoles, Boolean enabled, Boolean issys,
			String subSystem, boolean accountNonExpired,
	        boolean credentialsNonExpired, boolean accountNonLocked, Set<GrantedAuthority> authorities) {
		
		if (((cardName == null) || "".equals(cardName))
				|| (userPassword == null)) {
			throw new IllegalArgumentException(
					"Cannot pass null or empty values to constructor");
		}

		this.id = id;
		this.cardName = cardName;
		this.userName = userName;
		this.userPassword = userPassword;
		this.duty = duty;
		this.mobilephone = mobilephone;
		this.email = email;
		this.sex = sex;
		this.isDelete = isDelete;
		this.remarks = remarks;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.department = department;
		this.userRoles = userRoles;
		this.enabled = enabled;
		this.issys = issys;
		this.subSystem = subSystem;
		this.authorities = authorities;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.username = cardName;
		this.password = userPassword;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "u_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "u_cardName")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@Column(name = "u_username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "u_userpassword")
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String password) {
		this.userPassword = password;
	}

	@Column(name = "u_duty")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "u_mobilephone")
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name = "u_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "u_sex")
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column(name = "u_isDelete")
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "u_remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "u_createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "u_updateTime")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "u_did", referencedColumnName = "d_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@OneToMany(targetEntity = UserRoles.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	@Fetch(FetchMode.SUBSELECT)
	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	@Column(name = "u_enabled")
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "u_issys")
	public Boolean getIssys() {
		return issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}

	@Column(name = "u_subsystem")
	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public boolean equals(Object rhs) {
		if (!(rhs instanceof User) || (rhs == null)) {
			return false;
		}

		User user = (User) rhs;

		// 具有的权限。
		if (!authorities.equals(user.authorities)) {
			return false;
		}

		// 通过Spring Security构建一个用户时，用户名和密码不能为空。
		return (this.getPassword().equals(user.getPassword())
				&& this.getUsername().equals(user.getUsername())
				&& (this.isAccountNonExpired() == user.isAccountNonExpired())
				&& (this.isAccountNonLocked() == user.isAccountNonLocked())
				&& (this.isCredentialsNonExpired() == user
						.isCredentialsNonExpired()) && (this.isEnabled() == user
				.isEnabled()));
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = (Set<GrantedAuthority>) authorities;
	}

	@Transient
	public String getPassword() {
		return password;
	}

	@Transient
	public String getUsername() {
		return username;
	}

	public int hashCode() {
		int code = 9792;

		// 若该用户不是登录人员，则可以允许没有authorities。
		if (null != getUsername() && null != getAuthorities()) {
			for (GrantedAuthority authority : getAuthorities()) {

				code = code * (authority.hashCode() % 7);
			}
		}

		if (this.getPassword() != null) {
			code = code * (this.getPassword().hashCode() % 7);
		}

		if (this.getUsername() != null) {
			code = code * (this.getUsername().hashCode() % 7);
		}

		if (this.isAccountNonExpired()) {
			code = code * -2;
		}

		if (this.isAccountNonLocked()) {
			code = code * -3;
		}

		if (this.isCredentialsNonExpired()) {
			code = code * -5;
		}

		if (this.isEnabled()) {
			code = code * -7;
		}

		return code;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(
			Collection<GrantedAuthority> authorities) {
		Assert.notNull(authorities,
				"Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority,
					"GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements
			Comparator<GrantedAuthority>, Serializable {
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before
			// adding it to the set.
			// If the authority is null, it is a custom authority and should
			// precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("CardName: ").append(this.cardName).append("; ");
		sb.append("Duty: ").append(this.duty).append("; ");
		sb.append("Remarks: ").append(this.remarks).append("; ");
		sb.append("UserSubSystem: ").append(this.subSystem).append("; ");
		sb.append("UserIsSys: ").append(this.issys).append("; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired)
				.append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired)
				.append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked)
				.append("; ");

		if (null != authorities && !authorities.isEmpty()) {
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (GrantedAuthority auth : authorities) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	

}
