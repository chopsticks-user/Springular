package com.frost.springular.event.data;

import lombok.ToString;

@ToString
public enum EventRepeat {
  daily, weekly, monthly, yearly, custom, none
}