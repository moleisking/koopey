package com.koopey.api.model.dto;

import java.util.Date;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssetDto extends BaseDto {

  public String buyerId;
  private String currency;
  private String data;
  private String dimensionUnit;
  private String firstImage;
  private String secondImage;
  public String sellerId;
  private String thirdImage;
  private String fourthImage;
  private String manufacturer;
  private String weightUnit;
  private Integer average;
  private Integer distance;
  private Integer quantity;
  private Integer height;
  private Integer length;
  private Integer positive;
  private Integer negative;
  private Integer value;
  private Integer weight;
  private Integer width;
  private Date manufactureDate;
  private String timeZone;
  private Boolean available;
  private AdvertDto advert;
  private String[] reviewIds;
  private String[] tagIds;
  private String[] locationIds;

}
