package com.fuswx.spzx.model.entity.product;

import com.fuswx.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Integer productId;
	private String imageUrls;

}