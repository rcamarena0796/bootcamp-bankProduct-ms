package com.everis.bootcamp.bankproductms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "COMISSION")
@EqualsAndHashCode(callSuper = false)
public class Comission {
    private double comission;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;
}