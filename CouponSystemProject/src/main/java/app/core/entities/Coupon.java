package app.core.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "company_id")
    private Company company;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer amount;
    private Double price;
    private String image;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "coupon_customer",
    		joinColumns = @JoinColumn(name = "coupon_id"),
    		inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;

}
