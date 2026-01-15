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

@Component
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

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();

        guardarButton.addActionListener(e -> crearCliente());
        limpiarButton.addActionListener(e ->  limpiarFormulario());
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
        this.tableModelClientes = new DefaultTableModel(0,4);
        String[] cabeceros = {"ID", "Nombre", "Apellido", "Membresia"};
        this.tableModelClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(this.tableModelClientes);
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
        var cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        this.clienteServicio.guardarCliente(cliente);// insertamos el cliente
        limpiarFormulario();
        listarClientes();
    }
    private void limpiarFormulario (){
        nombreTexto.setText("");
        apeliidoTexto.setText("");
        membresiaTexto.setText("");
    }
    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);
    }

}
