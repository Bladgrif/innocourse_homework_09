package learn.innopolis.university;

public enum Endpoint {
    AUTH_LOGIN("auth/login"),
    COMPANY("company"),
    EMPLOYEE("employee");

    private final String path;

    private Endpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
