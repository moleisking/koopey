package com.koopey.api.model.dto;

import java.util.Date;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssetDto extends BaseDto {

  private String currency;
  private String dimensionUnit;
  private String manufacturer;
  private String manufacturer_serial;
  private String weightUnit;
  private Float average;
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
  private boolean available;
  private AdvertDto advert;
  private Set<ImageDto> images;
  private Set<ReviewDto> reviews;
  private UserDto buyer;
  private UserDto seller;
  private Set<TagDto> tags;
  private Set<LocationDto> locations;

}
