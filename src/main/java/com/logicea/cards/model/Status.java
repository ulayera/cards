package com.logicea.cards.model;

public enum Status {
  TO_DO,
  IN_PROGRESS,
  DONE;

  // null-safe, case insensitive valueOf
  public static Status fromString(String str) {
    if (str == null) {
      return null;
    }
    return Status.valueOf(str.toUpperCase());
  }
}
