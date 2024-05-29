package util;

public enum GenderEnum {

        Nam(1, "Nam"),
        Nu(0, "Nu");

        private final int value;
        private final String description;

    GenderEnum(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        public static util.GenderEnum getById(int value) {
            for (util.GenderEnum e : values()) {
                if (e.value == value) {
                    return e;
                }
            }
            return Nam;
        }

        public static util.GenderEnum getByName(String name) {
            for (util.GenderEnum e : values()) {
                if (e.name().equalsIgnoreCase(name)) {
                    return e;
                }
            }
            return Nam;
        }
    }


