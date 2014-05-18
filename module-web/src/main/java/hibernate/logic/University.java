package hibernate.logic;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

@javax.persistence.Entity
@Table(name="UNIVERSITY")
@Bean(path="java:global.project.module-ejb-1.0.UniversityBean!beans.uni.UniversityHome")
public class University implements hibernate.logic.Entity {

	@Column(name="PARENT_ID", nullable=false)
	private int parentID;
	@Id
    	@GeneratedValue(generator="increment")
    	@GenericGenerator(name="increment", strategy = "increment")
    	@Column(name="ID", nullable=false)
	private int id;
	@Column(name="NAME", nullable=false)
	private String name;
	@Column(name="DEPTS_COUNT", nullable=true)
	private Integer departamentsCount;	
	@Column(name="WWW", nullable=true)
	private String www;

	public University() {

	}
	
	public void setParentID(int id) {
		parentID = id;
	} 
	
	public int getParentID() {
		return parentID;
	}

	public void setID(int id) {
		this.id = id;
	}	

	public int getID() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDepartamentsCount(int count) {
		departamentsCount = count;
	}

	public int getDepartamentsCount() {
		return departamentsCount;
	}

	public void setWWW(String www) {
		this.www = www;
	}

	public String getWWW() {
		return www;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		University other = (University) obj;
		if (id != other.id)
			return false;
		return true;
	}
}