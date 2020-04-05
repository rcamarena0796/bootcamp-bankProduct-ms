package com.everis.bootcamp.bankproductms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankMaxTransDTO {
    private HashMap<String, Integer> productMaxTrans;
}