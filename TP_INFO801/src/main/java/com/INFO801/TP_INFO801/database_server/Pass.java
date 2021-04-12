package com.INFO801.TP_INFO801.database_server;

public class Pass {
    public String id;
    public String firstName;
    public String lastName;

    public Pass(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
