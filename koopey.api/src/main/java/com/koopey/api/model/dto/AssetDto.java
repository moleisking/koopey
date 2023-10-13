package com.koopey.api.model.dto;

import com.koopey.api.model.dto.base.BaseDto;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AssetDto extends BaseDto {


  private Set<TagDto> tags;
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
  private Long manufactureDate;
  private Boolean available;
}
