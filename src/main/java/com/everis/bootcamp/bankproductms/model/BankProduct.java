package com.everis.bootcamp.bankproductms.model;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "BANK_PRODUCT")
@EqualsAndHashCode(callSuper = false)
public class BankProduct {
	@Id
	private String id;
	@NotBlank(message = "'numAccount' is required")
	private String numAccount;
	@NotBlank(message = "'bankId' is required")
	private String bankId;
	@NotBlank(message = "'idProdType' is required")
	private String idProdType;
	private double total;
	private double minFin;
	private int maxTransactions;
	private int currentTransNumber;
	@NotBlank(message = "'clientNumDoc' is required")
	private String clientNumDoc;
	private Set<String> holders;
	private Set<String> authorized;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifyDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lastTransactionDate;

}
