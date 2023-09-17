package edu.pizza.especialidades;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

// Definición de la clase PizzaDesayuno que hereda de la clase Pizza
public class PizzaDesayuno extends Pizza {
    // Constructor de la clase PizzaDesayuno
    public PizzaDesayuno() {
        // Llama al constructor de la clase base Pizza con el nombre "Pizza de Desayuno"
        // y una lista de ingredientes (Topping) específicos del desayuno
        super("Pizza de Desayuno", new Topping[]{
                new Topping("Huevos revueltos", 5.50),
                new Topping("Tocino", 6.75),
                new Topping("Salchicha", 5.25),
                new Topping("Queso cheddar", 4.75)
        });
    }

    // Implementación del método getNombre() de la clase base Pizza
    @Override
    public String getNombre() {
        // Devuelve el nombre de la pizza, que es "Pizza de Desayuno"
        return super.getNombre();
    }
}

