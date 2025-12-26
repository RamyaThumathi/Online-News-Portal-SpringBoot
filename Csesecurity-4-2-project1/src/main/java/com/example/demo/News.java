package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="news")
@Data
public class News {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getNewstag1() {
		return newstag1;
	}

	public void setNewstag1(String newstag1) {
		this.newstag1 = newstag1;
	}

	public String getNewstag2() {
		return newstag2;
	}

	public void setNewstag2(String newstag2) {
		this.newstag2 = newstag2;
	}

	public String getDateHeld() {
		return dateHeld;
	}

	public void setDateHeld(String dateHeld) {
		this.dateHeld = dateHeld;
	}

	public String getTimeHeld() {
		return timeHeld;
	}

	public void setTimeHeld(String timeHeld) {
		this.timeHeld = timeHeld;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNewsDetails() {
		return newsDetails;
	}

	public void setNewsDetails(String newsDetails) {
		this.newsDetails = newsDetails;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Reporter getReporter() {
		return reporter;
	}

	public void setReporter(Reporter reporter) {
		this.reporter = reporter;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;
    private String newstag1;
    private String newstag2;
    private String dateHeld;
    private String timeHeld;
    private String place;

    @Column(columnDefinition = "TEXT")
    private String newsDetails;

    private String newsType;

    private String image1;
    private String image2;
    private String image3;

    private String status = "Pending";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Reporter reporter;
}
