package ryot.zona_fit.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ryot.zona_fit.modelo.Cliente;
import ryot.zona_fit.servicio.ClienteServicio;
import ryot.zona_fit.servicio.IClienteServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apeliidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tableModelClientes;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();

        guardarButton.addActionListener(e -> crearCliente());
        limpiarButton.addActionListener(e ->  limpiarFormulario());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> {
            eliminarCliente();
        });
    }

    private void iniciarForma(){
        FlatDarculaLaf.setup(); // agregamos look and fell modo osucro
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);// centra la ventana
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        //this.tableModelClientes = new DefaultTableModel(0,4);
        this.tableModelClientes = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        String[] cabeceros = {"ID", "Nombre", "Apellido", "Membresia"};
        this.tableModelClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(this.tableModelClientes);
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Cargar listado de clientes
        listarClientes();

    }
    private void listarClientes(){
        this.tableModelClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente ->{
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tableModelClientes.addRow(renglonCliente);
        });
    }
    private void crearCliente(){
    if(nombreTexto.getText().equals("")){
        mostrarMensaje("Proporciona un nombre");
        nombreTexto.requestFocusInWindow();
        return;
    }
    if(membresiaTexto.getText().equals("")){
        mostrarMensaje("Proporciona un membresia");
        membresiaTexto.requestFocusInWindow();
        return;
    }
    // Recuperamos los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apeliidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());
        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);
        this.clienteServicio.guardarCliente(cliente);// insertamos el cliente / modificar
        if(this.idCliente == null){
            mostrarMensaje("Cliente creado con exito");
            limpiarFormulario();
            listarClientes();
        }else{
            mostrarMensaje("Cliente modificado con exito");
            limpiarFormulario();
            listarClientes();
        }

    }
    private void eliminarCliente(){
       var renglon = clientesTabla.getSelectedRow();
       if(renglon != -1){

           var cliente = clienteServicio.buscarClientePorId(idCliente);
           if(cliente!= null){
               this.clienteServicio.eliminarCliente(cliente);
               mostrarMensaje("Cliente eliminado con exito");
               limpiarFormulario();
               listarClientes();
           }else{
               mostrarMensaje("Cliente no encontrado");
           }
       }else{
           mostrarMensaje("Seleccione al menos un cliente");
       }
    }
    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){// -1 significa que no se selecciono ningun registro
            var id = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon,1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon,2).toString();
            this.apeliidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon,3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }
    private void limpiarFormulario (){
        nombreTexto.setText("");
        apeliidoTexto.setText("");
        membresiaTexto.setText("");
        // Limpíamos el id del cliente seleccionado
        this.idCliente = null;
        // Deseleccionamos el registro de la tabla
        this.clientesTabla.getSelectionModel().clearSelection();
    }
    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);
    }

}
