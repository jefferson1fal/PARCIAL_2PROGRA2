package edu.formularios;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;
import edu.pizza.especialidades.PizzaDesayuno;
import edu.pizza.especialidades.PizzaItaliana;
import edu.pizza.especialidades.PizzaMarinara;
import edu.pizza.especialidades.PizzaPostre;
import edu.pizza.especialidades.PizzaYolaArmo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class frmPizza {
    private JPanel jPanelPrincipal;
    private JComboBox<Topping> comboBoxToppings;
    private JTextField txtPizza;
    private JButton btnAddIngrediente;
    private JLabel lblTotal;
    private JList<Topping> lista1;
    private JButton btnPreparar;
    private JList<String> lista2;
    private JComboBox<String> comboBoxEspecialidades;
    private JButton seleccionarButton;
    private JLabel lblEspecialidad;
    private JRadioButton rdbPequeña;
    private JRadioButton rdbMediana;
    private JRadioButton rdbGrande;
    private double total;
    private DefaultListModel<Topping> modeloLista = new DefaultListModel<>();
    private DefaultListModel<String> modeloLista2 = new DefaultListModel<>();
    private String especialidadSeleccionada;
    private String tamañoSeleccionado = "Pequeña"; // Tamaño por defecto

    private List<Topping> ingredientes = new ArrayList<>();
    private Map<String, Class<? extends Pizza>> especialidades = new HashMap<>();
    private Map<String, List<String>> especialidadIngredientes = new HashMap<>();
    private Map<String, Double> especialidadPrecios = new HashMap<>();
    private Pizza pizzaEspecial;

    // Definir los precios por tamaño de las pizzas
    private double precioPequeña = 10.0;
    private double precioMediana = 15.0;
    private double precioGrande = 20.0;

    public JPanel getjPanelPrincipal() {
        return jPanelPrincipal;
    }

    public frmPizza() {
        cargarToppings();
        cargarEspecialidades();
        cargarEspecialidadIngredientes();


        comboBoxEspecialidades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                especialidadSeleccionada = (String) comboBox.getSelectedItem();

                if (especialidadSeleccionada == null) {
                    return; // No hacer nada si no se ha seleccionado nada
                }

                // Actualizar el lblEspecialidad con el nombre de la pizza seleccionada
                lblEspecialidad.setText("Especialidad: " + especialidadSeleccionada);

                // Actualizar el campo de texto txtPizza con el nombre de la pizza seleccionada
                txtPizza.setText(especialidadSeleccionada);

                // Cargar los ingredientes de la especialidad en la lista2 (JList)
                List<String> ingredientesEspecialidad = especialidadIngredientes.get(especialidadSeleccionada);
                if (ingredientesEspecialidad != null) {
                    modeloLista2.clear();
                    for (String ingrediente : ingredientesEspecialidad) {
                        modeloLista2.addElement(ingrediente);
                    }
                    lista2.setModel(modeloLista2);
                }

                // Calcular el costo total de la pizza seleccionada
                total = calcularPrecioTotal(precioPequeña); // Reiniciar el costo total al precio de una pizza pequeña
                lblTotal.setText("Total: $" + total);
            }
        });

        btnAddIngrediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (especialidadSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una especialidad primero");
                    return;
                }

                Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();
                modeloLista.addElement(ingrediente);
                lista1.setModel(modeloLista);
                total += ingrediente.getPrecio();
                lblTotal.setText("Total: $" + total);
            }
        });

        // Agregar un MouseListener al JList lista1 para detectar el doble clic y eliminar un topping
        lista1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    int selectedIndex = lista1.getSelectedIndex();
                    if (selectedIndex != -1) { // Asegurarse de que haya un elemento seleccionado
                        Topping toppingSeleccionado = modeloLista.getElementAt(selectedIndex);
                        total -= toppingSeleccionado.getPrecio();
                        lblTotal.setText("Total: $" + total);
                        modeloLista.removeElementAt(selectedIndex);
                    }
                }
            }
        });

        btnPreparar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (especialidadSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una especialidad primero");
                    return;
                }

                // Crear una nueva pizza con la especialidad seleccionada
                Pizza pizza;
                if (especialidadSeleccionada.equals("Pizza Italiana")) {
                    pizza = new PizzaItaliana("Pizza Italiana");
                } else if (especialidadSeleccionada.equals("Pizza Marinara")) {
                    pizza = new PizzaMarinara("Pizza Marinara");
                } else if (especialidadSeleccionada.equals("Pizza Postre")) {
                    pizza = new PizzaPostre();
                } else if (especialidadSeleccionada.equals("Pizza Desayuno")) {
                    pizza = new PizzaDesayuno();
                } else if (especialidadSeleccionada.equals("Pizza Yola Armo")) {
                    // Crea una PizzaYolaArmo sin ingredientes iniciales
                    pizza = new PizzaYolaArmo("Pizza Yola Armo");
                } else {
                    JOptionPane.showMessageDialog(null, "Especialidad no válida");
                    return;
                }

                // Agregar los ingredientes seleccionados
                for (int i = 0; i < modeloLista.getSize(); i++) {
                    Topping ingrediente = modeloLista.getElementAt(i);
                    pizza.addTopping(ingrediente);
                }

                // Calcular el precio total de la pizza
                double precioTamaño = calcularPrecioTamaño(tamañoSeleccionado);
                double precioTotal = calcularPrecioTotal(precioTamaño);
                lblTotal.setText("Total: $" + precioTotal);

                // Verificar si hay "toppings extras" seleccionados en lista1
                if (lista1.getModel().getSize() > 0) {
                    modeloLista2.addElement("Toppings extras:");
                    for (int i = 0; i < lista1.getModel().getSize(); i++) {
                        Topping toppingExtra = (Topping) lista1.getModel().getElementAt(i);
                        modeloLista2.addElement(" - " + toppingExtra.getNombre());
                    }
                }

                // Agregar "Preparando la pizza" y "La pizza está lista" a la lista2
                modeloLista2.addElement("Preparando la pizza....\n........\n.......");
                modeloLista2.addElement("La pizza está lista!!");
            }
        });


        rdbPequeña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (especialidadSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una especialidad primero");
                    return;
                }

                // Actualiza el precio total al precio de una pizza pequeña
                double precioTotal = calcularPrecioTotal(precioPequeña);
                lblTotal.setText("Total: $" + precioTotal);
            }
        });

        rdbMediana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (especialidadSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una especialidad primero");
                    return;
                }

                // Actualiza el precio total al precio de una pizza mediana
                double precioTotal = calcularPrecioTotal(precioMediana);
                lblTotal.setText("Total: $" + precioTotal);
            }
        });

        rdbGrande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (especialidadSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una especialidad primero");
                    return;
                }

                // Actualiza el precio total al precio de una pizza grande
                double precioTotal = calcularPrecioTotal(precioGrande);
                lblTotal.setText("Total: $" + precioTotal);
            }
        });
        txtPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void cargarToppings() {
        ingredientes.add(new Topping("Champiñones", 4.55));
        ingredientes.add(new Topping("Mozzarella", 5.25));
        ingredientes.add(new Topping("Cebolla", 6.25));
        ingredientes.add(new Topping("Tomate", 3.25));
        ingredientes.add(new Topping("Pepperoni", 7.25));
        ingredientes.add(new Topping("Bacon", 8.25));

        DefaultComboBoxModel<Topping> model = new DefaultComboBoxModel<>(ingredientes.toArray(new Topping[0]));
        comboBoxToppings.setModel(model);
    }

    private void cargarEspecialidades() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Pizza Italiana");
        model.addElement("Pizza Marinara");
        model.addElement("Pizza Postre");
        model.addElement("Pizza Desayuno");
        model.addElement("Pizza Yola Armo"); // Agregamos la nueva especialidad
        comboBoxEspecialidades.setModel(model);
    }

    private void cargarEspecialidadIngredientes() {
        List<String> ingredientesPizzaItaliana = new ArrayList<>();
        ingredientesPizzaItaliana.add("Tomate");
        ingredientesPizzaItaliana.add("Mozzarella");
        ingredientesPizzaItaliana.add("Aceitunas");
        ingredientesPizzaItaliana.add("Albahaca");
        especialidadIngredientes.put("Pizza Italiana", ingredientesPizzaItaliana);

        List<String> ingredientesPizzaMarinara = new ArrayList<>();
        ingredientesPizzaMarinara.add("Ajo");
        ingredientesPizzaMarinara.add("Aceite de oliva");
        ingredientesPizzaMarinara.add("Orégano");
        ingredientesPizzaMarinara.add("Salsa de tomate");
        especialidadIngredientes.put("Pizza Marinara", ingredientesPizzaMarinara);

        List<String> ingredientesPizzaPostre = new ArrayList<>();
        ingredientesPizzaPostre.add("Chocolate");
        ingredientesPizzaPostre.add("Fresas");
        ingredientesPizzaPostre.add("Crema batida");
        ingredientesPizzaPostre.add("Nueces");
        especialidadIngredientes.put("Pizza Postre", ingredientesPizzaPostre);

        List<String> ingredientesPizzaDesayuno = new ArrayList<>();
        ingredientesPizzaDesayuno.add("Huevos");
        ingredientesPizzaDesayuno.add("Tocino");
        ingredientesPizzaDesayuno.add("Queso cheddar");
        ingredientesPizzaDesayuno.add("Cebolla");
        especialidadIngredientes.put("Pizza Desayuno", ingredientesPizzaDesayuno);

        // Agrega más ingredientes para otras especialidades aquí
    }



    // Función para calcular el precio total en función del precio del tamaño
    private double calcularPrecioTotal(double precioTamaño) {
        // Calcula el precio total sumando el precio del tamaño y otros ingredientes si los hay
        double precioTotal = precioTamaño;

        // Agregar el precio de los ingredientes seleccionados
        for (int i = 0; i < modeloLista.getSize(); i++) {
            Topping ingrediente = modeloLista.getElementAt(i);
            precioTotal += ingrediente.getPrecio();
        }

        return precioTotal;
    }

    // Función para calcular el precio del tamaño de pizza seleccionado
    private double calcularPrecioTamaño(String tamaño) {
        // Define los precios de los tamaños de pizza
        Map<String, Double> tamañoPrecios = new HashMap<>();
        tamañoPrecios.put("Pequeña", 10.0);  // Precio de una pizza pequeña
        tamañoPrecios.put("Mediana", 15.0); // Precio de una pizza mediana
        tamañoPrecios.put("Grande", 20.0);  // Precio de una pizza grande

        // Obtiene el precio del tamaño seleccionado
        return tamañoPrecios.getOrDefault(tamaño, 0.0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmPizza");
        frame.setContentPane(new frmPizza().jPanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
