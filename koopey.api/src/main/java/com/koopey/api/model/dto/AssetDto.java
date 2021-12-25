package com.koopey.api.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssetDto extends BaseDto {

  private AdvertDto advert;
  private String[] reviewIds;
  private String[] tagIds;
  private String locationId;
  private String buyerId;
  private String currency;
  private String data;
  private String dimensionUnit;
  private String firstImage;
  private String secondImage;
  private String sellerId;
  private String timeZone;
  private String thirdImage;
  private String fourthImage;
  private String manufacturer;
  private String weightUnit;
  public BigDecimal altitude;
  public BigDecimal latitude;
  public BigDecimal longitude;
  private Integer average;
  private Integer distance;
  private Integer quantity;
  private Integer height;
  private Integer length;
  private Integer negative;
  private Integer positive;
  private Integer value;
  private Integer weight;
  private Integer width;
  private Date manufactureDate;
  private Boolean available;
}
