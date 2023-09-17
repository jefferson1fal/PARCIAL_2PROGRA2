package edu.pizza.especialidades;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

import java.util.ArrayList;
import java.util.List;

public class PizzaYolaArmo extends Pizza {
    private List<Topping> ingredientesExtras;

    public PizzaYolaArmo(String nombre) {
        super(nombre);
        ingredientesExtras = new ArrayList<>();
    }

    // Agregar un ingrediente extra a la pizza
    public void agregarToppingExtra(Topping ingrediente) {
        ingredientesExtras.add(ingrediente);
    }

    // Obtener la lista de ingredientes extras de la pizza
    public List<Topping> getIngredientesExtras() {
        return ingredientesExtras;
    }

    // Calcular el precio total de la pizza con los ingredientes extras
    @Override
    public double getPrecio() {
        double precioTotal = super.getPrecio(); // Precio base de la pizza
        for (Topping ingrediente : ingredientesExtras) {
            precioTotal += ingrediente.getPrecio(); // Sumar el precio de cada ingrediente extra
        }
        return precioTotal;
    }
}
