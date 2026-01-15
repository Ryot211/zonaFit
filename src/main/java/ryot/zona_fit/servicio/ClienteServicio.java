package ryot.zona_fit.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ryot.zona_fit.modelo.Cliente;
import ryot.zona_fit.repositorio.ClienteRepo;

import java.util.List;

@Service
public class ClienteServicio implements IClienteServicio{

    @Autowired
    private ClienteRepo clienteRepo;
    @Override
    public List<Cliente> listarClientes() {
       return clienteRepo.findAll();

    }
    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
       return clienteRepo.findById(idCliente).orElse(null);
    }
    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }
    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepo.delete(cliente);
    }
}
