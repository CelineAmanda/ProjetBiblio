package com.casjedcem.dto;

import java.util.Date;

public class DocumentDTO {

	private long id;
	private String ISBN;
	private String titre;
	private String Auteur;
	private String description;
	private String imageName;
	private Date dateCreate;
	private int quantitéInit;
	private int quantitéDispo;
	private Long categoryID;
	private boolean available = true;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return Auteur;
	}
	public void setAuteur(String auteur) {
		Auteur = auteur;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Date getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
	public int getQuantitéInit() {
		return quantitéInit;
	}
	public void setQuantitéInit(int quantitéInit) {
		this.quantitéInit = quantitéInit;
	}
	public int getQuantitéDispo() {
		return quantitéDispo;
	}
	public void setQuantitéDispo(int quantitéDispo) {
		this.quantitéDispo = quantitéDispo;
	}
	public Long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public DocumentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DocumentDTO(String iSBN, String titre, String auteur, String description, String imageName, Date dateCreate,
			int quantitéInit, int quantitéDispo, Long categoryID, boolean available) {
		super();
		ISBN = iSBN;
		this.titre = titre;
		Auteur = auteur;
		this.description = description;
		this.imageName = imageName;
		this.dateCreate = dateCreate;
		this.quantitéInit = quantitéInit;
		this.quantitéDispo = quantitéDispo;
		this.categoryID = categoryID;
		this.available = available;
	}
    
	
}
