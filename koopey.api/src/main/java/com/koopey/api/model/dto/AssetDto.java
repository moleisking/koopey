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
  private Set<TransactionDto> transactions;
  private String[] transactionIds;
  private String[] tagIds;
  private String locationId;
  private String buyerId;
  private String currency;
  private String data;
  private String measure;
  private String firstImage;
  private String secondImage;
  private String sellerId;
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
  private Long manufacture;
  private Boolean available;
}
