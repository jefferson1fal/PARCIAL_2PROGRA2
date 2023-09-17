package edu.pizza.especialidades;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class PizzaMarinara extends Pizza {
    public PizzaMarinara(String pizzaMarinara) {
        super("Pizza Marinara", new Topping[]{
                new Topping("Ajo", 2.50),
                new Topping("Aceite de oliva", 3.75),
                new Topping("Orégano", 2.25),
                new Topping("Salsa de tomate", 4.25)
        });
    }
    @Override
    public String getNombre() {
        return super.getNombre();
    }
}
