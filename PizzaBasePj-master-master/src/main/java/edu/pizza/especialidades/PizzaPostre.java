package edu.pizza.especialidades;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

public class PizzaPostre extends Pizza {
    public PizzaPostre() {
        super("Pizza de Postre", new Topping[]{
                new Topping("Chocolate", 4.25),
                new Topping("Fresas", 3.50),
                new Topping("Nuez", 2.75),
                new Topping("Crema batida", 3.25)
        });
    }
    @Override
    public String getNombre() {
        return super.getNombre();
    }
}
