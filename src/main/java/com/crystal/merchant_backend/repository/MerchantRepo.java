package com.crystal.merchant_backend.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crystal.merchant_backend.entity.Merchant;

@Repository
public class MerchantRepo {
    
    @Autowired
    JdbcTemplate template;

    private final String LIST_ALL_MERCHANTS = "SELECT * FROM merchants";
    private final String LIST_MERCHANT_BY_ID = "SELECT * FROM merchants WHERE merchant_id=?";

    public List<Merchant> listAllMerchants(){
        return template.query(LIST_ALL_MERCHANTS, BeanPropertyRowMapper.newInstance(Merchant.class));
    }

    public Merchant listMerchantById(String id){
        return template.queryForObject(LIST_MERCHANT_BY_ID, BeanPropertyRowMapper.newInstance(Merchant.class), id);
    }
    
}
