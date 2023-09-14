package com.crystal.merchant_backend.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crystal.merchant_backend.dto.CreateMerchantRequest;
import com.crystal.merchant_backend.entity.Merchant;

@Repository
public class MerchantRepo {
    
    @Autowired
    JdbcTemplate template;

    private final String LIST_ALL_MERCHANTS = "SELECT * FROM merchants";
    private final String LIST_MERCHANT_BY_ID = "SELECT * FROM merchants WHERE merchant_id=?";
    private final String CREATE_MERCHANT = "INSERT INTO merchants(merchant_name, category, image_url, address_line1, address_line2, address_line3, merchantDesc) VALUES (?,?,?,?,?,?,?)";

    public List<Merchant> listAllMerchants(){
        return template.query(LIST_ALL_MERCHANTS, BeanPropertyRowMapper.newInstance(Merchant.class));
    }

    public Merchant listMerchantById(String id){
        return template.queryForObject(LIST_MERCHANT_BY_ID, BeanPropertyRowMapper.newInstance(Merchant.class), id);
    }

    public Boolean insertMerchant(CreateMerchantRequest merchant){
        Integer result = template.update(CREATE_MERCHANT, merchant.getMerchantName(), merchant.getCategory(), merchant.getImageUrl(),merchant.getAddressLine1(),merchant.getAddressLine2(),merchant.getAddressLine3(), merchant.getMerchantDesc());
        return result > 0 ? true : false;
    }
    
}
