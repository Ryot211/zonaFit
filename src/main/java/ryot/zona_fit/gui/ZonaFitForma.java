package ryot.zona_fit.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ryot.zona_fit.servicio.ClienteServicio;
import ryot.zona_fit.servicio.IClienteServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    IClienteServicio clienteServicio;
    private DefaultTableModel tableModelClientes;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();

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

    }
}
