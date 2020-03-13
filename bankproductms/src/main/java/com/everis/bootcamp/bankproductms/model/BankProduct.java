package com.everis.bootcamp.bankproductms.model;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "BANK_PRODUCT")
@EqualsAndHashCode(callSuper = false)
public class BankProduct {
	@Id
	private String id;
	private String numAccount;
	private String nameBank;
	private double total;
	private String clientId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDAte;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifyDate;

}
