package util;

public enum SaleTypeEnum {
    Giam_10(10,"Giảm 10%"),
    Giam_20(20,"Giảm 20%"),
    Giam_30(30,"Giảm 30%");

    private final int value;
    private final String description;

    SaleTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
    public static SaleTypeEnum getById(int value) {
        for (SaleTypeEnum e : values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;
    }

    public static SaleTypeEnum getByDescription(String Description) {
        for (SaleTypeEnum e : values()) {
            if (e.getDescription().equals(Description)) {
                return e;
            }
        }
        return null;
    }
}