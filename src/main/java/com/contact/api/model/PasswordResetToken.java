package com.contact.api.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.contact.api.model.audit.DateAudit;

import lombok.Data;

@Entity
@Table(name = "passwordResettoken")
@Data
public class PasswordResetToken extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false )
    private User user;

    @Column(nullable = false)
    private Date expiryDate;
    

    public void setExpiryDate(int hours){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, hours);
        this.expiryDate =  now.getTime();
        System.err.println(now.getTime());
        System.err.println(expiryDate);
    }

    public boolean isExpired() {
    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	Date d1 = null;
		Date d2 = null;
		long diffHours=0;
		String currentTime =  formatter.format(new Date());
		String expiryTime =  formatter.format(this.getExpiryDate());		
    	try {
			d1 = formatter.parse(currentTime);
			d2 = formatter.parse(expiryTime);
			long diff = d2.getTime() - d1.getTime();
			diffHours = diff / (60 * 60 * 1000) % 24;
			System.err.print(diffHours + " hours, ");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return diffHours>72;
    }
}