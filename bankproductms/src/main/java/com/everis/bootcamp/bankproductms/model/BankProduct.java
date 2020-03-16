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
	private String bankName;
	private String idProdType;
	private double total;
	@NotBlank(message = "'clientNumDoc' is required")
	private String clientNumDoc;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifyDate;

}
