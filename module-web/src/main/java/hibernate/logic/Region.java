package hibernate.logic;

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="REGION")
public class Region {

	@Column(name="PARENT_ID", nullable=false)
	private int parentID;
	@Id
    	@GeneratedValue(generator="increment")
    	@GenericGenerator(name="increment", strategy = "increment")
    	@Column(name="ID", nullable=false)
	private int id;
	@Column(name="NAME", nullable=false)
	private String name;
	@Column(name="POPULATION", nullable=true)
	private Integer population;
	@Column(name="SQUARE", nullable=true)
	private Integer square;

	public Region() {

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

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getPopulation() {
		return population;
	}

	public void setSquare(int square) {
		this.square = square;
	}

	public int getSquare() {
		return square;
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
		Region other = (Region) obj;
		if (id != other.id)
			return false;
		return true;
	}
}