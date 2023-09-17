package edu.pizza.especialidades;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class PizzaItaliana extends Pizza {
    public PizzaItaliana(String nombre) {
        super(nombre, new Topping[]{
                new Topping("Tomate", 3.25),
                new Topping("Mozzarella", 5.25),
                new Topping("Aceitunas", 3.50),
                new Topping("Albahaca", 2.75)
        });
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }}