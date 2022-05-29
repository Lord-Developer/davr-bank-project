package uz.davr.model;

public enum ERole {
    ROLE_USER,
    ROLE_TEACHER,
    ROLE_ADMIN;

    private String value;

    public static ERole valueOfRole(String role) {
        for (ERole e : values()) {
            if (e.name().equals(role)) {
                return e;
            }
        }
        return null;
    }

}
