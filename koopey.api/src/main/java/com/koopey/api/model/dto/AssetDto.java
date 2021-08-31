package com.koopey.api.model.dto;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssetDto extends BaseDto {

  private String currency;
  private String dimensionUnit;
  private int distance;
  private int quantity;
  private int width;
  private int height;
  private int length;
  private int value;
  private int weight;
  private String weightUnit;
  private String manufacturer;
  private String manufacturer_serial;
  private long manufactureDate;
  private long timeZone;
  private boolean available;
  private AdvertDto advert;
  private Set<ImageDto> images;
  private Set<ReviewDto> reviews;
  private UserDto buyer;
  private UserDto seller;
  private Set<TagDto> tags;
  private Set<LocationDto> locations;

}
