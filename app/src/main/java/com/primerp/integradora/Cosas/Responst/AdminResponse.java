package com.primerp.integradora.Cosas.Responst;

import java.lang.reflect.Type;
import java.util.List;

public class AdminResponse {

    private int totalUsers;       // Total de usuarios
    private int activeUsers;      // Usuarios activos
    private int inactiveUsers;    // Usuarios inactivos
    private List<UserByMonth> usersByMonth; // Lista de usuarios por mes
    private List<String> roles; // Lista de roles (ahora es una lista de cadenas)
    private List<String> types;

    // Getters y setters
    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getInactiveUsers() {
        return inactiveUsers;
    }

    public void setInactiveUsers(int inactiveUsers) {
        this.inactiveUsers = inactiveUsers;
    }

    public List<UserByMonth> getUsersByMonth() {
        return usersByMonth;
    }

    public void setUsersByMonth(List<UserByMonth> usersByMonth) {
        this.usersByMonth = usersByMonth;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    // Clase interna para representar la lista de usuarios por mes
    public static class UserByMonth {
        private int month; // Mes
        private int count; // Cantidad de usuarios registrados en ese mes

        // Getters y setters
        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
