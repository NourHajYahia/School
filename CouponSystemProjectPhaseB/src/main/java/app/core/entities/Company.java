package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String password;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Coupon> coupons;

	public Company() {
	}

	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void addCoupon(Coupon coupon) {
		if (this.coupons == null)
			this.coupons = new ArrayList<Coupon>();
		coupon.setCompany(this);
		this.coupons.add(coupon);
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	public Company(Integer id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

}
